/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class HighScore implements Serializable, Comparable<HighScore>{
    
    private String name;
    private int score;
    private Date date;

    public HighScore() {
    }
    
    public HighScore(String name, int score, Date date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore( int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date) {
        this.date = date;
    }

    @Override
    public int compareTo( HighScore o) {
        if (o.getScore() > getScore()) {
            return -1;
        } else if (o.getScore() < getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
