package com.example.prototypese;

public class SurveyHistory {
    private int userId;
    private int survey_id;
    private String filled_at;
    private long survey_filled_reward;
    private String history_title;
    private String history_category;

    public SurveyHistory(int userId, int survey_id, String filled_at, long survey_filled_reward, String history_title, String history_category) {
        this.userId = userId;
        this.survey_id = survey_id;
        this.filled_at = filled_at;
        this.survey_filled_reward = survey_filled_reward;
        this.history_title = history_title;
        this.history_category = history_category;
    }

    public SurveyHistory() {
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

    public String getFilled_at() {
        return filled_at;
    }

    public void setFilled_at(String filled_at) {
        this.filled_at = filled_at;
    }

    public long getSurvey_filled_reward() {
        return survey_filled_reward;
    }

    public void setSurvey_filled_reward(long survey_filled_reward) {
        this.survey_filled_reward = survey_filled_reward;
    }

    public String getHistory_title() {
        return history_title;
    }

    public void setHistory_title(String history_title) {
        this.history_title = history_title;
    }

    public String getHistory_category() {
        return history_category;
    }

    public void setHistory_category(String history_category) {
        this.history_category = history_category;
    }
}
