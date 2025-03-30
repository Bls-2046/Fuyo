const express = require('express');
const router = express.Router();
const multer = require('multer');
const path = require('path');
const crypto = require('crypto');
const fs = require('fs');
const axios = require('axios');

// 配置multer存储
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, path.join(__dirname, '../public/uploads'));
    },
    filename: (req, file, cb) => {
        const ext = path.extname(file.originalname);
        const randomName = crypto.randomBytes(8).toString('hex');
        cb(null, `${randomName}${ext}`);
    }
});

const upload = multer({ storage });

// 上传接口
router.post('/upload', upload.single('image'), (req, res) => {
    if (!req.file) {
        return res.status(400).json({ error: 'No file uploaded' });
    }

    const fileUrl = `/uploads/${req.file.filename}`;
    res.json({
        success: true,
        url: fileUrl,
        filename: req.file.filename
    });
});

// 从URL获取图片并保存
router.post('/upload/url', async (req, res) => {
    const { imageUrl } = req.body;

    if (!imageUrl) {
        return res.status(400).json({ error: 'No image URL provided' });
    }

    try {
        const response = await axios.get(imageUrl, { responseType: 'arraybuffer' });
        const contentType = response.headers['content-type'];

        if (!contentType || !contentType.startsWith('image/')) {
            return res.status(400).json({ error: 'URL does not point to an image' });
        }

        const ext = contentType.split('/')[1] || 'jpg';
        const filename = `${crypto.randomBytes(8).toString('hex')}.${ext}`;
        const filePath = path.join(__dirname, '../public/uploads', filename);

        fs.writeFileSync(filePath, response.data);

        const fileUrl = `/uploads/${filename}`;
        res.json({
            success: true,
            url: fileUrl,
            filename: filename
        });
    } catch (error) {
        console.error('Error downloading image:', error);
        res.status(500).json({ error: 'Failed to download image from URL' });
    }
});

// 获取所有图片列表
router.get('/list', (req, res) => {
    const uploadDir = path.join(__dirname, '../public/uploads');

    fs.readdir(uploadDir, (err, files) => {
        if (err) {
            return res.status(500).json({ error: 'Failed to read upload directory' });
        }

        const images = files.map(file => {
            return {
                name: file,
                url: `/uploads/${file}`
            };
        });

        res.json(images);
    });
});

module.exports = router;