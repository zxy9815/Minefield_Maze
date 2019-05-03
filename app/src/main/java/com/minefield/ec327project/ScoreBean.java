package com.minefield.ec327project;

/**
 * this class stores the current username, level played and score in program
 */
public class ScoreBean {
    private String phone;
    private String level;
    private Long time;

    public ScoreBean(String user, String level, Long time) {
        this.phone = user;
        this.level = level;
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public String getLevel() {
        return level;
    }

    public Long getTime() {
        return time;
    }


}
