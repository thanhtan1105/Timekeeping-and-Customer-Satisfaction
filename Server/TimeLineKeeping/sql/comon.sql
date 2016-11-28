---- ROLE
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('admin','/admin/**;/account/**;/department/**','redirect:/admin/accounts/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('manager','/manager/**','redirect:/manager/check_in/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('employee','/employee/**','redirect:/employee/customer_emotion/');

---- CONFIGURATION
INSERT INTO mydb.configuration(key_query,name,value) VALUES('send.sms', 'SEND_SMS', '0');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('emotion.acceptance', 'EMOTION_ACCEPT', '0.15');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('email.company', 'EMAIL_COMPANY', 'tkcs.vn');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('password.default', 'PASSWORD_DEFAULT', 'abcd@123');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('checkin.accept.confident', 'CHECKIN_ACCEPT_CONFIDENT', '0.8');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('checkin.training.confident', 'CHECK_IN_TRAINING_CONFIDENT', '0.85');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('time.checkin.begin', 'TIME_CHECKIN_BEGIN', '6:30');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('time.checkin.end', 'TIME_CHECKIN_END', '8:30');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('emotion.advance', 'EMOTION_ADVANCE', '0');
INSERT INTO mydb.configuration(key_query,name,value) VALUES('emotion.advance.confidence', 'EMOTION_ADVANCE_CONFIDENCE', '0.5');
INSERT INTO `mydb`.`configuration` (`id`, `key_query`, `name`, `value`) VALUES ('11', 'emotion.age.a', 'EMOTION_AGE_A', '0.0251');
INSERT INTO `mydb`.`configuration` (`id`, `key_query`, `name`, `value`) VALUES ('12', 'emotion.age.b', 'EMOTION_AGE_B', '-14.096');


--- QUANTITY EMOTION
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0', 'hơi hơi', '0.15');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.05', 'một chút', '0.2');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.05', 'một ít', '0.3');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.4', 'có vẽ', '0.6');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.5', 'một phần', '0.7');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.6', 'phần nhiều', '0.8');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.65', 'rất', '0.85');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.9', 'vô cùng', '1.5');
INSERT INTO `mydb`.`quantity_emotion` (`from_value`, `name`, `to_value`) VALUES ('0.8', 'gần như', '1.2');

---- EMOTION CONTENT
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('0', '8', '99', 'Bạn nên rót cho %s ly nước.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('1', '8', '8', 'Bạn nên bình tỉnh và tôn trọng %s khi nói chuyện.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('2', '8', '8', 'Bạn nên lăng nghe những ấm uất của %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('3', '8', '8', 'Bạn nên tạo không gian cỡ mở với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('4', '8', '8', 'Bạn nên vui vẻ nói chuyện với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('5', '8', '8', 'Bạn nên nói chuyện với %s bình thường.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('6', '8', '8', 'Bạn nên nói chuyện với %s nhẹ nhàng.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('7', '8', '8', 'Bạn nên bình tỉnh nói chuyện với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('4', '8', '8', 'Bạn nên chào tạm biệt khi giao dịch kết thúc với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('4', '8', '8', 'Bạn nên giới thiệu các dịch vụ mới cho %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('4', '8', '8', 'Nếu chưa có Visa thì hãy giới thiệu cho %s.', '0', '0');




/** COORDINATE*/
/** FLOOR 1*/
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '3', '3.8', '0', '0', '4');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '6', '3.8', '0', '0', '101','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '10', '3.8', '0', '0', '103','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '14', '3.8', '0', '0', '105','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '18', '3.8', '0', '0', '107','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '22', '3.8', '0', '0', '109','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '26', '3.8', '0', '0', '111','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '30', '3.8', '0', '0', '113','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '34', '3.8', '0', '0', '115','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '38', '3.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '2', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '6', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '10', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '14', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '18', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '22', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '26', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '30', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '34', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '38', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '2', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '6', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '20', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '38', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '6', '20', '0', '0', 'Healthy','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '2', '21.2', '0', '0', 'WC','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('1', '20', '21.2', '0', '0', 'Library','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '3', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '1', '3.8', '0', '0', '3');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('1', '1', '8.8', '0', '0', '0');

/** FLOOR 2*/
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '3', '3.8', '0', '0', '4');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '6', '3.8', '0', '0', '201','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '10', '3.8', '0', '0', '203','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '14', '3.8', '0', '0', '205','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '18', '3.8', '0', '0', '207','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '23', '3.8', '0', '0', '209','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '29', '3.8', '0', '0', '211','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '34', '3.8', '0', '0', '213','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '38', '3.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '2', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '3', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '6', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '10', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '14', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '18', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '23', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '29', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '34', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '38', '8.8', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '2', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '6', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '10', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '14', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '20', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '28', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '36', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '38', '16.2', '0', '0', '0');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '6', '20', '0', '0', 'Canteen','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '2', '21.2', '0', '0', 'WC','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '10', '21.2', '0', '0', '202','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '14', '21.2', '0', '0', '204','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '20', '21.2', '0', '0', '206','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '28', '21.2', '0', '0', '208','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`, `name`,`type`) VALUES ('2', '36', '21.2', '0', '0', '210','1');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '1', '3.8', '0', '0', '3');
INSERT INTO `mydb`.`coordinate` (`floor`, `latitude`, `longitude`, `major`, `minjor`,`type`) VALUES ('2', '1', '8.8', '0', '0', '0');

/** VERTEX **/
/** FLOOR 1*/
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('1', '28');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('2', '12');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('3', '13');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('4', '14');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('5', '15');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('6', '16');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('7', '17');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('8', '18');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('9', '19');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('10', '20');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('12', '13');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('13', '14');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('14', '15');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('15', '16');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('16', '17');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('17', '18');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('18', '19');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('19', '20');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('11', '21');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('20', '24');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('21', '22');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('22', '23');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('23', '24');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('21', '26');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('22', '25');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('23', '27');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('11', '28');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('12', '28');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('29', '30');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('11', '30');

/** FLOOR 2*/
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('31', '41');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('32', '42');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('33', '43');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('34', '44');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('35', '45');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('36', '46');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('37', '47');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('38', '48');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('39', '49');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('40', '41');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('41', '42');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('42', '43');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('43', '44');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('44', '45');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('45', '46');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('46', '47');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('47', '48');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('48', '49');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('40', '50');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('49', '57');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('50', '51');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('51', '52');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('52', '53');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('53', '54');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('54', '55');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('55', '56');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('56', '57');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('51', '58');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('50', '59');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('52', '60');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('53', '61');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('54', '62');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('55', '63');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('56', '64');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('65', '66');
INSERT INTO `mydb`.`connection_point` (`vertex1_id`, `vertex2_id`) VALUES ('40', '66');


