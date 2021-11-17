package com.cs506.cash_splitting.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Faq.TABLE_NAME)
public class Faq {
    public static final String TABLE_NAME = "faqdb";

    @Id
    @Column
    int faqid;


    @Column
    String question;

    @Column
    String answer;

    public void setFaqid(int faqid) {
        this.faqid = faqid;
    }

    public int getFaqid() {
        return faqid;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

}
