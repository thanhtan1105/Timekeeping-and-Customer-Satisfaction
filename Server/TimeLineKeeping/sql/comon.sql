
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


/** ROLE*/
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('admin','/admin/**;/account/**;/department/**','redirect:/admin/departments/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('manager','/manager/**','redirect:/manager/check_in/');
INSERT INTO `mydb`.`role` (name,allow_page,redirect ) VALUES ('employee','/employee/**','redirect:/employee/attendance/');