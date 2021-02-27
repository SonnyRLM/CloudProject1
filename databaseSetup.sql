CREATE DATABASE IF NOT EXISTS quiz;
USE quiz;

CREATE TABLE quiz_desc(
quiz_id INT NOT NULL AUTO_INCREMENT,
quiz_name VARCHAR(50) NOT NULL,
quiz_desc VARCHAR(300) NOT NULL,
PRIMARY KEY (quiz_id));

CREATE TABLE quiz_questions(
question_id INT NOT NULL AUTO_INCREMENT,
question VARCHAR(300) NOT NULL,
answers VARCHAR(200) NOT NULL,
correct_answer VARCHAR(50) NOT NULL,
quiz_id INT NOT NULL,
PRIMARY KEY (question_id),
FOREIGN KEY (quiz_id) REFERENCES quiz_desc(quiz_id)
);

CREATE TABLE quiz_users(
user_id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(30) NOT NULL,
totalscore INT,
PRIMARY KEY(user_id)
);

CREATE TABLE quiz_scores(
score_id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
quiz_id INT NOT NULL,
quiz_score INT NOT NULL,
PRIMARY KEY (score_id),
FOREIGN KEY (user_id) REFERENCES quiz_users(user_id),
FOREIGN KEY (quiz_id) REFERENCES quiz_desc(quiz_id)
);
