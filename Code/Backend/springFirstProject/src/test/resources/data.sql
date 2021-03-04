INSERT INTO `quiz_description` (`quiz_name`, `quiz_description`) VALUES ('Capital Cities', 'Select the Capital city of these counties');
INSERT INTO `quiz_description` (`quiz_name`, `quiz_description`) VALUES ('Continents', 'Select the continent that these countries are in');

--QUIZID 1
INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('Capital of England','London;Bristol;Manchester;Hull','London','1');
INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('Capital of New Zealand','Wellington;Christchurch;Dunedin;Auckland','Wellington','1');
INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('Capital of Cuba','Camaguey;Guantanamo;Havana;Holguin','Havana','1');
INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('Capital of Uruguay','Montevideo;Melo;Buenos Aires;Maldonado','Montevideo','1');

--QUIZID 2
--INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('What continent is Austrailia in?','Europe;Oceania;Africa;Antarctica','Oceania','2');
--INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('What continent is Lesotho in?','Europe;Oceania;Africa;Antarctica','Africa','2');
--INSERT INTO `quiz_question` (`question`,`answers`,`correct`,`quiz_description_quiz_id`) VALUES ('What continent is Andorra in?','Europe;Oceania;Africa;Antarctica','Europe','2');