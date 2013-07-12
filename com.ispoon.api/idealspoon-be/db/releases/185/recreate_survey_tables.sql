drop table IF EXISTS ifb_survey_question_type_answer;
drop table IF EXISTS ifb_survey_question_type;
drop table IF EXISTS ifb_survey_response;
drop table IF EXISTS ifb_survey_response_log;
drop table IF EXISTS ifb_survey_answer;


create table ifb_survey_answer (
  survey_answer_id INT NOT NULL AUTO_INCREMENT,
  display_name VARCHAR(100) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (survey_answer_id)
);


create table ifb_survey_question_type (
  survey_question_type_id INT NOT NULL AUTO_INCREMENT,
  display_name VARCHAR(100) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (survey_question_type_id)
);


create table ifb_survey_response (
  survey_response_id INT NOT NULL AUTO_INCREMENT,
  campaign_key VARCHAR(64) NOT NULL,
  ad_key VARCHAR(64) NOT NULL,
  story_id INT NOT NULL,
  survey_answer_id INT NOT NULL,
  count_value INT NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_update TIMESTAMP NOT NULL,
  PRIMARY KEY (survey_response_id),
  INDEX (campaign_key),
  INDEX (ad_key),
  INDEX (survey_answer_id),
  CONSTRAINT fk_survey_answer_id FOREIGN KEY ( survey_answer_id ) REFERENCES ifb_survey_answer ( survey_answer_id )
);


create table ifb_survey_response_log (
  survey_response_log_id INT NOT NULL AUTO_INCREMENT,
  campaign_key VARCHAR(64) NOT NULL,
  ad_key VARCHAR(64) NOT NULL,
  story_id INT NOT NULL,
  survey_answer_id INT NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  remote_ip VARCHAR(100),
  remote_user_agent VARCHAR(2048),
  PRIMARY KEY (survey_response_log_id),
  INDEX (campaign_key),
  INDEX (ad_key),
  INDEX (survey_answer_id),
  INDEX (remote_ip),
  CONSTRAINT fk_log_survey_answer_id FOREIGN KEY ( survey_answer_id ) REFERENCES ifb_survey_answer ( survey_answer_id )
);



create table ifb_survey_question_type_answer (
  survey_question_type_answer_id INT NOT NULL AUTO_INCREMENT,
  question_type_id INT NOT NULL,
  answer_id INT NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (survey_question_type_answer_id),
  CONSTRAINT fk_question_type_id FOREIGN KEY ( question_type_id ) REFERENCES ifb_survey_question_type ( survey_question_type_id ),
  CONSTRAINT fk_answer_id FOREIGN KEY ( answer_id ) REFERENCES ifb_survey_answer ( survey_answer_id )
);


INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (1, 'Yes / No', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (2, 'Yes / No / Maybe', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (3, 'A or B', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (4, 'A,B or C', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (5, 'A,B,C or D', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (6, 'A,B,C,D or E', current_timestamp());
INSERT INTO ifb_survey_question_type (survey_question_type_id, display_name, create_time) VALUES (7, 'A,B,C,D,E or F', current_timestamp());


INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (1, 'Yes', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (2, 'No', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (3, 'Maybe', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (4, 'A', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (5, 'B', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (6, 'C', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (7, 'D', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (8, 'E', current_timestamp());
INSERT INTO ifb_survey_answer (survey_answer_id, display_name, create_time) VALUES (9, 'F', current_timestamp());

INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (1, 1, 1, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (2, 1, 2, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (3, 2, 1, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (4, 2, 2, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (5, 2, 3, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (6, 3, 4, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (7, 3, 5, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (8, 4, 4, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (9, 4, 5, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (10, 4, 6, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (11, 5, 4, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (12, 5, 5, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (13, 5, 6, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (14, 5, 7, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (15, 6, 4, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (16, 6, 5, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (17, 6, 6, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (18, 6, 7, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (19, 6, 8, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (20, 7, 4, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (21, 7, 5, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (22, 7, 6, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (23, 7, 7, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (24, 7, 8, current_timestamp());
INSERT INTO ifb_survey_question_type_answer (survey_question_type_answer_id, question_type_id, answer_id, create_time) VALUES (25, 7, 9, current_timestamp());

