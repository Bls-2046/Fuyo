/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80041
 Source Host           : localhost:3306
 Source Schema         : fuyo_db

 Target Server Type    : MySQL
 Target Server Version : 80041
 File Encoding         : 65001

 Date: 01/04/2025 12:59:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yiyan
-- ----------------------------
DROP TABLE IF EXISTS `yiyan`;
CREATE TABLE `yiyan`  (
  `id` bigint NOT NULL,
  `sentence` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK7f2riqsk9th3gnx5vuu3n36ey`(`sentence`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yiyan
-- ----------------------------
INSERT INTO `yiyan` VALUES (51, 'きっと大丈夫！ 絶対うまくいくよ！- Sakura');
INSERT INTO `yiyan` VALUES (70, '一声梧叶一声秋，一点芭蕉一点愁，三更归梦三更后。');
INSERT INTO `yiyan` VALUES (52, '一点星光，在心上就能反射出太阳。');
INSERT INTO `yiyan` VALUES (18, '七夕今宵看碧霄，牵牛织女渡河桥。');
INSERT INTO `yiyan` VALUES (59, '七情拆解，无非心印；六欲所指，皆由心生。');
INSERT INTO `yiyan` VALUES (35, '不同遭遇里我发现你的瞬间，有种不可言说的温柔直觉。');
INSERT INTO `yiyan` VALUES (60, '不管什么时候，能改变世界的人都是一心追逐梦想的人。');
INSERT INTO `yiyan` VALUES (58, '不要为小事遮住视线，我们还有更大的世界.');
INSERT INTO `yiyan` VALUES (46, '与君初相识，犹如故人归。');
INSERT INTO `yiyan` VALUES (22, '世界上有太多孤独的人害怕先踏出第一步。');
INSERT INTO `yiyan` VALUES (77, '两情若是久长时，又岂在朝朝暮暮。');
INSERT INTO `yiyan` VALUES (67, '乌鹊倦栖，鱼龙惊起，星斗挂垂杨。');
INSERT INTO `yiyan` VALUES (96, '书当快意读易尽，客有可人期不来。');
INSERT INTO `yiyan` VALUES (92, '云收雨过波添，楼高水冷瓜甜，绿树阴垂画檐。');
INSERT INTO `yiyan` VALUES (87, '云无心以出岫，鸟倦飞而知还。');
INSERT INTO `yiyan` VALUES (17, '人是可以改变一切的，世上的一切。');
INSERT INTO `yiyan` VALUES (55, '人最怕的是发现了自己想要的东西。');
INSERT INTO `yiyan` VALUES (11, '人类的赞歌是勇气的赞歌，人类的伟大是勇气的伟大！');
INSERT INTO `yiyan` VALUES (38, '人间四月芳菲尽，山寺桃花始盛开。');
INSERT INTO `yiyan` VALUES (27, '以有涯随无涯，殆已！已而为知者，殆而已矣！');
INSERT INTO `yiyan` VALUES (3, '但屈指西风几时来，又不道流年暗中偷换。');
INSERT INTO `yiyan` VALUES (2, '你是我羽翼下的风。');
INSERT INTO `yiyan` VALUES (29, '你说过，人最大的敌人是自己。');
INSERT INTO `yiyan` VALUES (8, '你需要找出面对明天的力量。');
INSERT INTO `yiyan` VALUES (68, '又酒趁哀弦，灯照离席。梨花榆火催寒食。');
INSERT INTO `yiyan` VALUES (49, '及时当勉励，岁月不待人。');
INSERT INTO `yiyan` VALUES (81, '垓下美人泣楚歌，定陶美人泣楚舞，真龙亦鼠虎亦鼠。');
INSERT INTO `yiyan` VALUES (9, '大丈夫序以为：夫体而国，再而家，后立己。');
INSERT INTO `yiyan` VALUES (10, '大本钟下送快递——上面摆，下面寄。');
INSERT INTO `yiyan` VALUES (54, '太想伸手摘取星星的人，常常忘记脚下的鲜花。');
INSERT INTO `yiyan` VALUES (56, '如果忘记你那么容易，那我爱你干嘛！');
INSERT INTO `yiyan` VALUES (31, '宇宙是蚂蚁的梦。');
INSERT INTO `yiyan` VALUES (90, '安得五彩虹，驾天作长桥。');
INSERT INTO `yiyan` VALUES (40, '实变函数学十遍，泛函学完心泛寒。');
INSERT INTO `yiyan` VALUES (28, '就算风吹散了冰雪，想念也会留存下来。');
INSERT INTO `yiyan` VALUES (19, '希望你今后的每一次笑，都是真心的。');
INSERT INTO `yiyan` VALUES (89, '庐山秀出南斗傍，屏风九叠云锦张。');
INSERT INTO `yiyan` VALUES (97, '当星星变成星空，梦想也就近在咫尺了。');
INSERT INTO `yiyan` VALUES (78, '忆与君别年，种桃齐蛾眉。');
INSERT INTO `yiyan` VALUES (44, '悄悄抽出小绿芽的幼苗，终将有一天会长成参天大树的。');
INSERT INTO `yiyan` VALUES (36, '悲痛的事，温柔以对；难过的事，坚强以对。');
INSERT INTO `yiyan` VALUES (100, '惟将终夜常开眼，报答平生未展眉。');
INSERT INTO `yiyan` VALUES (23, '想一个人有多想念，那又是文字失效瞬间。');
INSERT INTO `yiyan` VALUES (86, '愿得此身长报国，何须生入玉门关。');
INSERT INTO `yiyan` VALUES (43, '慢也好，步伐小也罢，是往前走就好。');
INSERT INTO `yiyan` VALUES (42, '慢慢来，谁还没有一个努力的过程。');
INSERT INTO `yiyan` VALUES (20, '成熟的人眼里满是前途，稚嫩的人眼里满是爱恨情仇。');
INSERT INTO `yiyan` VALUES (13, '我也想杀死伤痛，让那恩怨有始有终。');
INSERT INTO `yiyan` VALUES (1, '我和你，可以做朋友吗？');
INSERT INTO `yiyan` VALUES (26, '我希望兜兜转转之后那个人还是你。');
INSERT INTO `yiyan` VALUES (25, '我愿披挂长风扬鞭策马 去看去唱那四海之大。');
INSERT INTO `yiyan` VALUES (7, '我所理解的生活就是和喜欢的一切在一起。');
INSERT INTO `yiyan` VALUES (14, '我有故人抱剑去，斩尽春风未曾归。');
INSERT INTO `yiyan` VALUES (79, '我欲穿花寻路，直入白云深处，浩气展虹霓。');
INSERT INTO `yiyan` VALUES (63, '我该用什么来回应你的不义。');
INSERT INTO `yiyan` VALUES (24, '把温柔和心软留给值得的人。');
INSERT INTO `yiyan` VALUES (45, '救救孩子……');
INSERT INTO `yiyan` VALUES (66, '斜月照帘帷，忆君和梦稀。');
INSERT INTO `yiyan` VALUES (39, '无法逃避的是自我，而无法挽回的是过去。');
INSERT INTO `yiyan` VALUES (32, '早知如此绊人心，何如当初莫相识。');
INSERT INTO `yiyan` VALUES (94, '春来茗叶还争白，腊尽梅梢尽放红。');
INSERT INTO `yiyan` VALUES (93, '暖雨晴风初破冻，柳眼梅腮，已觉春心动。');
INSERT INTO `yiyan` VALUES (64, '曾经沧海难为水，除却巫山不是云。');
INSERT INTO `yiyan` VALUES (65, '最后再来做个约定吧。一定会再次与你相遇......');
INSERT INTO `yiyan` VALUES (33, '最短的捷径就是绕远路。');
INSERT INTO `yiyan` VALUES (83, '月上柳梢头，人约黄昏后。');
INSERT INTO `yiyan` VALUES (48, '月有盈亏花有开谢，想人生最苦离别。');
INSERT INTO `yiyan` VALUES (88, '望阙云遮眼，思乡雨滴心。');
INSERT INTO `yiyan` VALUES (71, '每恨蟪蛄怜婺女，几回娇妒下鸳机，今宵嘉会两依依。');
INSERT INTO `yiyan` VALUES (37, '水晶帘动微风起，满架蔷薇一院香。');
INSERT INTO `yiyan` VALUES (84, '水际轻烟，沙边微雨。荷花芳草垂杨渡。');
INSERT INTO `yiyan` VALUES (95, '江雨霏霏江草齐，六朝如梦鸟空啼。');
INSERT INTO `yiyan` VALUES (80, '海风吹不断，江月照还空。');
INSERT INTO `yiyan` VALUES (62, '温柔解救不了这个世界。');
INSERT INTO `yiyan` VALUES (82, '激气已能驱粉黛，举杯便可吞吴越。');
INSERT INTO `yiyan` VALUES (41, '爸爸不是一生下来就是爸爸，爸爸也是头一次当爸爸。');
INSERT INTO `yiyan` VALUES (15, '猫是可爱的，狼是很帅的。就是说，孤独又可爱又帅。');
INSERT INTO `yiyan` VALUES (69, '白马金鞍从武皇，旌旗十万宿长杨。');
INSERT INTO `yiyan` VALUES (101, '百分之一中的机率');
INSERT INTO `yiyan` VALUES (6, '空山新雨后，天气晚来秋。');
INSERT INTO `yiyan` VALUES (76, '纤云弄巧，飞星传恨，银汉迢迢暗度。');
INSERT INTO `yiyan` VALUES (91, '纸上得来终觉浅，绝知此事要躬行。');
INSERT INTO `yiyan` VALUES (5, '给生命以时间，而不是给时间以生命。');
INSERT INTO `yiyan` VALUES (72, '自古逢秋悲寂寥，我言秋日胜春朝。');
INSERT INTO `yiyan` VALUES (53, '若批评不自由，则赞美无意义。');
INSERT INTO `yiyan` VALUES (21, '行百里者半九十。');
INSERT INTO `yiyan` VALUES (47, '行远自迩，登高自卑。');
INSERT INTO `yiyan` VALUES (73, '西湖旧日，留连清夜，爱酒几将花误。');
INSERT INTO `yiyan` VALUES (12, '言念君子，温其如玉。');
INSERT INTO `yiyan` VALUES (57, '让我们泰然若素，与自己的时代狭路相逢。');
INSERT INTO `yiyan` VALUES (98, '越是困难，越要抬起头，地上可找不到任何希望！');
INSERT INTO `yiyan` VALUES (50, '跌跌撞撞的成长，又美又疼才是本质。');
INSERT INTO `yiyan` VALUES (85, '轻寒细雨情何限。不道春难管。');
INSERT INTO `yiyan` VALUES (34, '迎着风，拥抱彩虹！');
INSERT INTO `yiyan` VALUES (16, '近乡情更怯，不敢问来人。');
INSERT INTO `yiyan` VALUES (75, '造化钟神秀，阴阳割昏晓。');
INSERT INTO `yiyan` VALUES (61, '那些听不见音乐的人认为那些跳舞的人疯了。');
INSERT INTO `yiyan` VALUES (99, '问苍茫大地,谁主沉浮?');
INSERT INTO `yiyan` VALUES (74, '隐隐飞桥隔野烟，石矶西畔问渔船。');
INSERT INTO `yiyan` VALUES (4, '隔岸无旧情，姑苏有钟声。');
INSERT INTO `yiyan` VALUES (30, '风月折断杨柳枝，琴瑟朝露挥掷成诗。');

SET FOREIGN_KEY_CHECKS = 1;
