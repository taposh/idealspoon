-- Adding another criteria field to SurveyResponse entity

ALTER TABLE ifb_survey_response ADD COLUMN site VARCHAR(64) NOT NULL DEFAULT "demo";
ALTER TABLE ifb_survey_response_log ADD COLUMN site VARCHAR(64) NOT NULL DEFAULT "demo";