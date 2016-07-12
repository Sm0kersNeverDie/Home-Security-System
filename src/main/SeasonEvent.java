/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;

/**
 *
 * @author Edward
 */
public class SeasonEvent {
    private String name;
    private HashMap<String,SeasonEvent> nextSeason = new HashMap<String,SeasonEvent>();
    public SeasonEvent(String name) {
        this.name = name;
    }
    public String name(){
        return name;
    }
    public void addNext(String direction, SeasonEvent next) {
        nextSeason.put(direction, next);
    }
    public SeasonEvent go(String direction) {
        return nextSeason.get(direction);
    }

    @Override
    public String toString() {
        return "You are in room: " + this.name;
    }
    
}
