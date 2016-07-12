/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;
import Character.NPC;
import java.io.Serializable;
import static java.lang.Character.toUpperCase;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Event;
/**
 *
 * @author Edward
 */
public class Location implements Serializable {
    private String name;
    private char building;//name of the location, and whether it's in building A or B
        //list of accessible rooms
    private HashMap<String,Room> roomList = new HashMap<>(); 
    private ArrayList<String> roomRef = new ArrayList<>();
        //list of accessible locations
    private HashMap<String,Location> nextLocationList = new HashMap<>(); 
    private ArrayList<String> locationRef = new ArrayList<>();
    private String text; //description of location when player look around
    private Event event;
    private Location fixBug;
    private NPC npc = null;
        //Create the location
    public Location(String name, char building) {
        this.name = name;
        this.building = building;
        this.event = new Event();
    }
    public Location(String name, char building, String npc) {
        this(name, building);
        this.npc = new NPC(npc);
    }
        //Add accessible rooms, locations, display text, and events
    public boolean hasNPC(){
        return (npc != null);
    }
    public NPC npc() {
        return this.npc;
    }
    public void addRoom(Room room) {
        this.roomList.put(room.toString().toUpperCase(),room);
        this.roomRef.add(room.toString().toUpperCase());
    }
    public void addNextLocation(Location nextLocation) {
        this.nextLocationList.put(nextLocation.getName().toLowerCase(), nextLocation);
        this.locationRef.add(nextLocation.getName().toLowerCase());
    }
    public void addDisplayText(String text) {
        this.text = text;
    }
    public String getName() {
        return this.name;
    }
        //Display description
    public void text() {
        System.out.println(this.text);
        if (this.npc != null) {
            System.out.println("You also saw "+this.npc.name()+" walking around.");
        }
    }
        // Codes for ROOM LIST
    public char building(){
        return this.building;
    }
          /////////////////////////////////////
         // Codes used by yourLocation only //
        /////////////////////////////////////
    
        //Get list of accessible rooms in location
    public ArrayList<String> roomRef() {
        return this.roomRef;
    }
    public HashMap<String, Room> roomList() {
        return this.roomList;
    }
        //Get list of accessible locations
    public ArrayList<String> locationRef() {
        return this.locationRef;
    }
    public HashMap<String, Location> nextLocationList() {
        return this.nextLocationList;
    }
        //Check whether a room is in this location
    public boolean noRooms() {
        return this.roomList.isEmpty();
    }
    public boolean checkRoom(String room) {
        return (roomRef.contains(room.toUpperCase()));
    }
    public Event event() {
        return this.event;
    }
    public Room room(String roomName) {
        roomName = roomName.toUpperCase().trim();
        if (this.roomList.containsKey(roomName)) return this.roomList.get(roomName);
        else {
            System.out.println("No rooms!!!!");
            return null;
        }
    }
    public Location getLocation() {
        return this.fixBug;
    }
    public void setLocation(Location location) {
        this.fixBug = location;
    }
    @Override
    public String toString() {
        return this.name.toLowerCase() + " (" + this.building + " building)";
    }
}
