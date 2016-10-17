
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

/** EMOTION CONTENT**/
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('0', '8', '99', 'Bạn nên rót cho %s ly nước.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('1', '8', '8', 'Bạn nên bình tỉnh và tôn trọng %s khi nói chuyện.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('2', '8', '8', 'Bạn nên lăng nghe những ấm uất của %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('3', '8', '8', 'Bạn nên tạo không gian cỡ mở với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('4', '8', '8', 'Bạn nên vui vẻ nói chuyện với %s.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('5', '8', '8', 'Bạn nên nói chuyện với %s bình thường.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('6', '8', '8', 'Bạn nên nói chuyện với %s nhẹ nhàng.', '0', '0');
INSERT INTO `mydb`.`emotion_content` (`emotion_first`, `emotion_second`, `emotion_third`, `message`, `status`, `vote`) VALUES ('7', '8', '8', 'Bạn nên bình tỉnh nói chuyện với %s.', '0', '0');


/** ROLE*/
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('admin','/admin/**;/account/**;/department/**','redirect:/admin/departments/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('manager','/manager/**','redirect:/manager/check_in/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('employee','/employee/**','redirect:/employee/attendance/');