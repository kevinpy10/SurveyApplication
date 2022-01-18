package com.example.prototypese;

public class Survey {
    private int userId;
    private int survey_id;
    private String survey_category;
    private String survey_title;
    private String survey_author;
    private String survey_desc;
    private String created_at;
    private long survey_reward;
    private int survey_participant;
    private int survey_max_participant;
    private String survey_url;

    public Survey(int userId, int survey_id, String survey_category, String survey_title, String survey_author, String survey_desc, String created_at, long survey_reward, int survey_participant, int survey_max_participant, String survey_url) {
        this.userId = userId;
        this.survey_id = survey_id;
        this.survey_category = survey_category;
        this.survey_title = survey_title;
        this.survey_author = survey_author;
        this.survey_desc = survey_desc;
        this.created_at = created_at;
        this.survey_reward = survey_reward;
        this.survey_participant = survey_participant;
        this.survey_max_participant = survey_max_participant;
        this.survey_url = survey_url;
    }

    public Survey() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getSurvey_category() {
        return survey_category;
    }

    public void setSurvey_category(String survey_category) {
        this.survey_category = survey_category;
    }

    public String getSurvey_title() {
        return survey_title;
    }

    public void setSurvey_title(String survey_title) {
        this.survey_title = survey_title;
    }

    public String getSurvey_author() {
        return survey_author;
    }

    public void setSurvey_author(String survey_author) {
        this.survey_author = survey_author;
    }

    public String getSurvey_desc() {
        return survey_desc;
    }

    public void setSurvey_desc(String survey_desc) {
        this.survey_desc = survey_desc;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getSurvey_reward() {
        return survey_reward;
    }

    public void setSurvey_reward(long survey_reward) {
        this.survey_reward = survey_reward;
    }

    public int getSurvey_participant() {
        return survey_participant;
    }

    public void setSurvey_participant(int survey_participant) {
        this.survey_participant = survey_participant;
    }

    public int getSurvey_max_participant() {
        return survey_max_participant;
    }

    public void setSurvey_max_participant(int survey_max_participant) {
        this.survey_max_participant = survey_max_participant;
    }

    public String getSurvey_url() {
        return survey_url;
    }

    public void setSurvey_url(String survey_url) {
        this.survey_url = survey_url;
    }
}
