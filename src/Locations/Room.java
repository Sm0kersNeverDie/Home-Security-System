/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;
import Items.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class Room implements Serializable {
        // Name and description of the room
    private String name, description; 
        // Special event code of the room
    private int code;
        // List of items in the room
    private ArrayList<Item> item = new ArrayList<Item>(); 
    
        // Constructor's codes
        // Create the room, add features to the room
    public Room(String name, int code) {
        this.name = name.toUpperCase();
        this.code = code;
    }
    public Room(String name) {
        this(name, 0);
    }
    public void code(int code) {
        this.code = code;
    }
    public void addItem(Item item){
        this.item.add(item);
    }
    public void addDescription(String des) {
        this.description = des;
    }
        // Get list of item in the room
    public ArrayList<Item> getItemList() {
        return this.item;
    }
        // Remove item after taking
    public void removeItem(Item item) {
        this.item.remove(item);
    }
    public int eventCode() {
        return this.code;
    }
    public void description() {
        String vowels = "aeiou";
        System.out.println(this.description);
        if (item.size() > 0) {
            int i = 0;
            while (i < item.size()) {
                System.out.print("You look closely and also see ");
                if (item.get(i).getQuantity() > 1) System.out.print("some ");
                else if (vowels.contains(""+item.get(i).getName().toLowerCase().charAt(0))) System.out.print("an ");
                else System.out.print("a ");
                System.out.println(item.get(i++).getName().toLowerCase());
            }
        }
    }
    public boolean hasItem(String item) {
        for (Item temp : this.item) 
            if (temp.getName().equalsIgnoreCase(item)) 
                return true;
        return false;
    }
    public void removeAllItems() {
        item.clear();
    }
    @Override
    public String toString() {
        return this.name.toUpperCase();
    }
    
}
