package com.minefield.ec327project;

/**
 * Package    : com.example.ec327project
 * ClassName  : ScoreBean
 * Description: ${TODO}
 * Date       : 2019/4/13 14:20
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
