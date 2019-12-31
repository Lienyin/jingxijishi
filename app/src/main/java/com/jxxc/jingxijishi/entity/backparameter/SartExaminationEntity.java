package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class SartExaminationEntity implements Serializable {

    public String topic;
    public String examinationTime;
    public String passScore;
    public String examinationId;
    public List<Question> questionList;

    public class Question{
        public String score;
        public String questionId;
        public String answerC;
        public String topic;
        public String answerB;
        public String answerD;
        public String correctAnswer;
        public String answerA;
    }
}
