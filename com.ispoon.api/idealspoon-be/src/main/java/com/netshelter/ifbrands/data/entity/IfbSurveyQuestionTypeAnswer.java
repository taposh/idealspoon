package com.netshelter.ifbrands.data.entity;
// Generated Apr 12, 2013 12:26:23 PM by Hibernate Tools 3.2.4.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IfbSurveyQuestionTypeAnswer generated by hbm2java
 */
@Entity
@Table(name="ifb_survey_question_type_answer"
)
public class IfbSurveyQuestionTypeAnswer  implements java.io.Serializable {


     private Integer surveyQuestionTypeAnswerId;
     private IfbSurveyAnswer ifbSurveyAnswer;
     private IfbSurveyQuestionType ifbSurveyQuestionType;
     private Date createTime;

    public IfbSurveyQuestionTypeAnswer() {
    }

    public IfbSurveyQuestionTypeAnswer(IfbSurveyAnswer ifbSurveyAnswer, IfbSurveyQuestionType ifbSurveyQuestionType, Date createTime) {
       this.ifbSurveyAnswer = ifbSurveyAnswer;
       this.ifbSurveyQuestionType = ifbSurveyQuestionType;
       this.createTime = createTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="survey_question_type_answer_id", unique=true, nullable=false)
    public Integer getSurveyQuestionTypeAnswerId() {
        return this.surveyQuestionTypeAnswerId;
    }
    
    public void setSurveyQuestionTypeAnswerId(Integer surveyQuestionTypeAnswerId) {
        this.surveyQuestionTypeAnswerId = surveyQuestionTypeAnswerId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="answer_id", nullable=false)
    public IfbSurveyAnswer getIfbSurveyAnswer() {
        return this.ifbSurveyAnswer;
    }
    
    public void setIfbSurveyAnswer(IfbSurveyAnswer ifbSurveyAnswer) {
        this.ifbSurveyAnswer = ifbSurveyAnswer;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="question_type_id", nullable=false)
    public IfbSurveyQuestionType getIfbSurveyQuestionType() {
        return this.ifbSurveyQuestionType;
    }
    
    public void setIfbSurveyQuestionType(IfbSurveyQuestionType ifbSurveyQuestionType) {
        this.ifbSurveyQuestionType = ifbSurveyQuestionType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", nullable=false, length=19)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }




}

