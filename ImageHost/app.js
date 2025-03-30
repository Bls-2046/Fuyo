var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var multer = require('multer');
var crypto = require('crypto');
var fs = require('fs');
var axios = require('axios');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var imagesRouter = require('./routes/images');

var app = express();

// 配置上传目录
const UPLOAD_DIR = path.join(__dirname, 'public/uploads');
if (!fs.existsSync(UPLOAD_DIR)) {
  fs.mkdirSync(UPLOAD_DIR, { recursive: true });
  console.log('创建上传目录:', UPLOAD_DIR);
}

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/api/images', imagesRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  console.log('404错误: 请求路径未找到 -', req.originalUrl);
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  console.error('服务器错误:', err.message);
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

// 服务器监听
var port = process.env.PORT || 3000;
var server = app.listen(port, function() {
  console.log('图床服务已启动');
  console.log('访问地址: http://localhost:' + port);
  console.log('上传目录: ' + UPLOAD_DIR);
  console.log('API端点:');
  console.log(' - 上传图片: POST http://localhost:' + port + '/api/images/upload');
  console.log(' - 图片列表: GET http://localhost:' + port + '/api/images/list');
});

// 处理未捕获的异常
process.on('uncaughtException', function(err) {
  console.error('未捕获的异常:', err);
});

module.exports = app;