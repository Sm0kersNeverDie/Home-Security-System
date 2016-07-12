/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import Character.Player;
import Locations.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Edward
 */
public class Command {
    private final String end1 = "quit";
    private final String end2 = "quit game";
    private final String gibberish = "I don't know what you mean...";
    private final Scanner reader = new Scanner(System.in);;
    private Player p;
    
    public Command(){
        
    }
    public void command() throws ClassNotFoundException {    
        String command;
        
        p.getEvent().gotoRoom(p, reader, p.getLocation().room("B205"));
        while (true) {
            if (p.fainted()) {
                load();
                p.resetChar();
                System.out.println("You are in: "+p.getLocation());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("Type your command: ");
            command = reader.nextLine().toLowerCase();
            if (command.equalsIgnoreCase(end1)||command.equalsIgnoreCase(end2)) break;
            else commandList(command);
        }
    }
    private void commandList(String command) {
        if (command.toLowerCase().trim().startsWith("check")||command.toLowerCase().trim().startsWith("view "))
            command = command.substring(5).trim().toLowerCase();
            // Take an item outside of room
        if (command.trim().toLowerCase().startsWith("take")) 
            System.out.println("You can't take that.");
            // Look inside a room
        else if (command.trim().toLowerCase().startsWith("look inside room")) 
            p.look(command.trim().substring(16).trim().toUpperCase());
        else if (command.trim().toLowerCase().startsWith("look inside")) 
            p.look(command.trim().substring(11).trim().toUpperCase());
            // Look around location
        else if (command.trim().toLowerCase().startsWith("look around")) 
            p.lookAround();
            // Go to a room, or another location
        else if (command.trim().toLowerCase().startsWith("go to")) {
            if (!command.trim().substring(5).trim().toLowerCase().isEmpty())
                go(command.trim().substring(5).trim().toLowerCase(),reader);
            else System.out.println("You can try the command LOCATION LIST or ROOM LIST to see where you can go.");
        }
            // List of locations and rooms
        else if (command.trim().equalsIgnoreCase("location list")) locationList(p.getLocation());
        else if (command.trim().equalsIgnoreCase("room list")) roomList(p.getLocation());
            // Check inventory
        else if (command.trim().equalsIgnoreCase("inventory"))
            p.inventory();
            // Check current location
        else if (command.trim().equalsIgnoreCase("location"))
            System.out.println("You are in: " + p.getLocation());
            // Check player's stats
        else if (command.trim().substring(0,4).equalsIgnoreCase("stat"))
            p.stats();
        else if (command.trim().contains("note")&&command.trim().contains("read")) p.getBag().checkNote();
        else if (command.trim().equalsIgnoreCase("help")) help();
        else if (command.trim().equalsIgnoreCase("play1")) p.getEvent().number1(reader);
        else if (command.trim().equalsIgnoreCase("play2")) p.getEvent().number2(reader);
        else if (command.trim().contains("talk")) {
            if (p.getLocation().hasNPC()) p.getLocation().npc().talk(p, reader);
            else System.out.println("There's nobody to talk to... Are you okay?");
        }
        else System.out.println(this.gibberish);
    }
    private void help() {
        System.out.println("Here are some of the available commands:");
        System.out.println(String.format("%-30s%-30s%-30s","- TAKE something","- LOOK AROUND","- LOOK INSIDE a room"));
        System.out.println(String.format("%-30s%-30s%-30s","- GO TO places","- view accessible LOCATION LIST","- check available ROOM LIST"));
        System.out.println(String.format("%-30s%-30s%-30s","- check INVENTORY","- check current LOCATION","- check player's STATS"));
        System.out.println(String.format("%-30s%-30s%-30s","- EXIT a room","- QUIT game","- TALK TO someone"));
        System.out.println("- READ your NOTE or a BOOK");
        System.out.println("More specific commands in specific tasks or locations will be instructed clearly in-game.");
    }
    private void locationList(Location loc) {
        System.out.println("Here is a list of accessible locations from " + loc);
        int c = 1;
        for (String temp : loc.locationRef()) System.out.println((c++) + ". " + temp);
    }
    private void roomList(Location loc) {
        if (!loc.noRooms()) {
            System.out.println("Most of the rooms in this area seem to be closed.");
            System.out.print("However, you realized room ");
            if (loc.roomRef().size() > 1) {
                for (String temp : loc.roomRef()) System.out.print(temp.toUpperCase()+", ");
                System.out.println(" all have lights inside.");
            }
            else for (String temp : loc.roomRef()) System.out.println(temp.toUpperCase()+" has lights inside.");
        }
        else System.out.println("All the rooms in this area seem to be closed.");
    }
    private void go(String place, Scanner reader) {
        boolean succeed = true;
        if (!p.move(place,reader)) {                   // This line makes player go to another location if possible
                // Go to a room
            if (place.substring(0,4).equalsIgnoreCase("room")) place = place.substring(4).trim();
            if (place.contains(".")) 
                place = place.substring(0, place.indexOf('.')) + place.substring(place.indexOf('.')+1, place.length());
            if (p.getLocation().checkRoom(place.toUpperCase())) { 
                p.getEvent().gotoRoom(p, reader, p.getLocation().room(place));
            }
            else succeed = false;
        }
        if (!(succeed||p.fainted())) System.out.println("You can't go there.\nYou can try the command LOCATION LIST or ROOM LIST to see where you can go.");
    }
    private void load() throws ClassNotFoundException {
        ObjectInputStream load = null;
        try {
            FileInputStream in = new FileInputStream("MR.rtf");
            load = new ObjectInputStream(in);
            this.p = (Player)load.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                load.close();
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        //////////////////////////////////////////////////////////////////////
       //                                                                  //
      //                            CAUTION!!!                            //
     //                                                                  //
    ///////////////////////////////////////////////////////////////////////////
        //                                                                  //
       // These lines create maps and rooms and connect them all together. //
      //                      Must handle with care!                      //
     //                                                                  //
    //////////////////////////////////////////////////////////////////////
    
    public void start() {
            // Create the locations of a map
        Location hallwayB2 = new Location("2nd floor hallway",'b');
        Location hallwayB1 = new Location("1st floor hallway",'b');
        Location lobbyB2 = new Location("2nd floor lobby",'b',"Bibek");
        Location libraryB1 = new Location("library lobby",'b');
        Location hallwayA2 = new Location("2nd floor hallway",'a');
        Location lobbyA1 = new Location("main lobby",'a',"Saugat");
        Location hallwayA1E = new Location("east hallway",'a');
        Location hallwayA1W = new Location("west hallway",'a');
        Location lobbyA0 = new Location("basement lobby",'a',"Edward");
        Location hallwayA0 = new Location("basement east hallway",'a');
            // Connects the locations
        hallwayB2.addNextLocation(lobbyB2);
        hallwayB2.addNextLocation(hallwayB1);
        hallwayB1.addNextLocation(hallwayB2);
        hallwayB1.addNextLocation(libraryB1);
        lobbyB2.addNextLocation(hallwayB2);
        lobbyB2.addNextLocation(libraryB1);
        lobbyB2.addNextLocation(hallwayA2);
        libraryB1.addNextLocation(hallwayB1);
        libraryB1.addNextLocation(lobbyB2);
        libraryB1.addNextLocation(lobbyA1);
        hallwayA2.addNextLocation(lobbyB2);
        hallwayA2.addNextLocation(lobbyA1);
        lobbyA1.addNextLocation(libraryB1);
        lobbyA1.addNextLocation(hallwayA2);
        lobbyA1.addNextLocation(lobbyA0);
        lobbyA1.addNextLocation(hallwayA1E);
        lobbyA1.addNextLocation(hallwayA1W);
        hallwayA1E.addNextLocation(lobbyA1);
        hallwayA1E.addNextLocation(hallwayA1W);
        hallwayA1W.addNextLocation(lobbyA1);
        hallwayA1W.addNextLocation(hallwayA1E);
        lobbyA0.addNextLocation(hallwayA0);
        lobbyA0.addNextLocation(lobbyA1);
        hallwayA0.addNextLocation(lobbyA0);
            // Create the room
        Room a0177 = new Room("A0177",9);
        Room b205 = new Room("B205",1);
        b205.addDescription("This is your homeroom.");
        Room a2117 = new Room("A2117",2);
        a2117.addDescription("This is Ulla Paatola's office.");
        Room b113 = new Room("B113",3);
        b113.addDescription("This is Sakari's office.");
        Room a0121 = new Room("A0121",4);
        a0121.addDescription("This is Timo's office.");
        Room a1130 = new Room("A1130",5);
        a1130.addDescription("This is Marianne's office.");
        Room a1149 = new Room("A1149",6);
        a1149.addDescription("This is group A's classroom.");
        Room a1102 = new Room("A1102",7);
        a1102.addDescription("This is the storage room.");
        Room library = new Room("library", 8);
            // Add room to location
        hallwayB2.addRoom(b205);
        hallwayA2.addRoom(a2117);
        hallwayB1.addRoom(b113);
        hallwayA0.addRoom(a0121);
        hallwayA1W.addRoom(a1130);
        hallwayA1W.addRoom(a1149);
        hallwayA1E.addRoom(a1102);
        libraryB1.addRoom(library);
            // Start game
        
            // Create the player
        System.out.print("Do you want to start a NEW GAME or LOAD an old one? "); String command = reader.nextLine().trim().toLowerCase();
        while (!(command.contains("new")||command.contains("load"))) {
            System.out.println("Invalid input.");
            System.out.print("Do you want to start a NEW GAME or LOAD an old one? "); command = reader.nextLine().trim().toLowerCase();
        }
        if (command.contains("new")) {
            p = new Player();
            p.setLocation(hallwayB2);
            // Add whatever story down here
            
        }
        else try {
            load();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
