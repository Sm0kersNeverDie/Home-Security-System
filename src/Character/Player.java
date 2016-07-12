/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Character;

import Items.*;
import Locations.Location;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Event;

/**
 *
 * @author Edward
 */
public class Player implements Serializable {
    private String name;
    private int atk, def, hp;
    private final int dAtk, dDef, maxHp;
    private final boolean luck;
    private Location yourLocation = new Location("a",'a');
    private final Bag bag;
    private boolean fainted;
    //Create the player
    public Player(){
        Scanner reader = new Scanner(System.in);
        String temp;
        System.out.print("What's your name? "); temp = reader.nextLine();
        this.name = temp;
        while (true) {
            System.out.print("Brain over brawn, TRUE or FALSE? "); 
            temp = reader.nextLine();
            if ((temp.trim().equalsIgnoreCase("true"))||temp.trim().equalsIgnoreCase("false"))
                break;
            System.out.println("I don't understand that answer...");
        }
        if (temp.trim().equalsIgnoreCase("true")) {
            this.dAtk = 6;
            this.dDef = 3;
            this.maxHp = 50;
            this.luck = true;
        }
        else {
            this.dAtk = 10;
            this.dDef = 5;
            this.maxHp = 100;
            this.luck = false;
        }
        this.bag = new Bag();
        this.fainted = false;
    }
                // Game master codes
        // Obviously used get the character name
    public String name() {
        return this.name;
    }
        // Set location of player at the beginning of game
    public void setLocation(Location loc) {
        this.yourLocation.setLocation(loc);
    }
        // Get current location of player
    public Location getLocation() {
        return this.yourLocation.getLocation();
    }
    public Event getEvent() {
        return this.yourLocation.event();
    }
        // Use when player fainted
    public void resetChar() {
        this.atk = this.dAtk;
        this.def = this.dDef;
        this.hp = this.maxHp;
        this.fainted = false;
    }
        // Use before checking stats and battles
    public void equip() {
        ArrayList<Item> thisBag = bag.getItemList();
        int tempA = 0, tempD = 0;
        for (Item temp : thisBag) {
            if (temp.isWeapon()&&(temp.getAtk() > tempA)) tempA = temp.getAtk();
            if (temp.isArmor() &&(temp.getDef() > tempD)) tempD = temp.getDef();
        }
        this.atk = this.dAtk + tempA;
        this.def = this.dDef + tempD;
    }
        // Check stats
    public boolean fainted() {
        return this.fainted;
    }
    public int getAtk(){
        return this.atk;
    }
    public int getDef(){
        return this.def;
    }
    public boolean getLuck() {
        return this.luck;
    }
    public int getHP() {
        return this.hp;
    }
    public int getMax() {
        return this.maxHp;
    }
        // Use in battles
    public boolean attacked(int enemyAtk) {
        this.hp -= (enemyAtk - this.def);
        return (this.hp > 0);
    }
    public Bag getBag() {
        return bag;
    }
        // Player codes
    
        // Move to another location
    public boolean move(String place, Scanner reader){
        if (this.yourLocation.getLocation().nextLocationList().containsKey(place)) {
            if (this.yourLocation.event().enemy(this, reader))
                this.yourLocation.setLocation(this.yourLocation.getLocation().nextLocationList().get(place));
            else this.fainted = (this.hp > 0);
            if (!this.fainted) {
                System.out.println("You are in: " + this.yourLocation.getLocation());
                return true;
            }
            else return false;
        }
        else return false;
    }
        // Take item in room
    public void take(Item item) {
        this.bag.addItem(item);
    }
        // Look inside a room
    public void look(String room){
        if (this.yourLocation.getLocation().roomList().containsKey(room)) 
            System.out.println("Seems like you can go inside this room.");
        else if (this.yourLocation.getLocation().checkRoom(room))
            System.out.println("Some strangers are working in this room. Better not come in and disturb them.");
        else System.out.println("Err... Where do you want to look again?");
    }
        // Look around the location
    public void lookAround(){
        System.out.print("You looked around the " + this.yourLocation.getLocation() + ". ");
        this.yourLocation.getLocation().text();
    }
        // Check inventory
    public void inventory(){
        this.bag.inventory();
    }
        // Check stats
    public void stats() {
        this.equip();
        System.out.println("Your name is: " + this.name);
        System.out.println("Your current health point: " + this.hp+"/"+this.maxHp);
        System.out.println("Your attack point: " + this.atk +"\nYour defend point: " + this.def);
    }
    
        // Save and Load
    public void save() {
        System.out.println("Saving...");
        ObjectOutputStream save = null;
        try {
            FileOutputStream out = new FileOutputStream("MR.rtf");
            save = new ObjectOutputStream(out);
            save.writeObject(this);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                save.close();
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Game saved.");
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
