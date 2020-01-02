package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;

public class AnswerEntity implements Serializable {
    private int id;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
