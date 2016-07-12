/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import Character.*;
import Items.*;
import Locations.*;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @author Edward
 */
public class Event implements Serializable {
    private final Random random = new Random();
    private boolean gameStart = true, clean = false;
    private boolean ullaP = false, sakari = false, marjo = false, timo = false, marianne = false, main = false;
    private boolean ulla1 = false, sakar1 = false, marj1 = false, tim1 = false, mariann1 = false, mai1 = false;
    private String masterPassword = "";
    public Event(){
        String a = "abcdefghijklmnopqrstuvwxyz";
        if (random.nextBoolean()) a = a.toUpperCase();
        int pointer = random.nextInt(a.length());
        masterPassword += a.charAt(pointer);
        a += "1234567890";
        while (masterPassword.length() < 10) {
            if (random.nextBoolean()) a = a.toUpperCase();
            else a = a.toLowerCase();
            pointer = random.nextInt(a.length());
            if (!masterPassword.contains(""+a.charAt(pointer))) 
                masterPassword += a.charAt(pointer);
        }
    }
        // Main event
    public void mainRoom() {
        if (!main) {
            System.out.println("");
        }
    }
    
        // Sakari's Event
    public boolean sakariStart() {
        return sakari;
    }
    public boolean sakariFinish(){
        return sakar1;
    }
    private void sakariRoom(Player p, Scanner reader, Room r) {
        boolean proceed = true;
        if (sakar1) {
            if (random.nextBoolean()) proceed = number1(reader);
            else proceed = number2(reader);
        }
        if (proceed) {
            System.out.println("You are in room " + r);
            r.description();
            if (!sakar1) {
                while (true) {
                    System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
                    if (command.contains("talk")&&command.contains("sakari")) {
                        sakariEvent(p,reader);
                        if (sakar1) r.addDescription("This is Sakari's room. Looks like he's gone somewhere.");
                        System.out.println("You left the room.");
                        break;
                    }
                    else if (command.contains("exit") 
                            || command.contains("leave"))
                        break;
                    else commandList(p,command,r);
                }
            }
            else {
                System.out.println("Your mom's voice echoed in your mind:\n\"My dear "+p.name()+", it's impolite to be in someone's room without their permission.\"");
                System.out.println("So you left the room.");
            }
        }
    }
    private void sakariEvent(Player p, Scanner reader) {
        String command;
        if (!hasOtherEvent()) {
            if (!sakari) {
                System.out.println("Sakari: \"Hello! How may I help you?\"");
                System.out.print("Do you want to tell him about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
                    System.out.println("I don't understand...");
                    System.out.print("Do you want to tell him about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                }
                if (command.equalsIgnoreCase("yes")) {
                    sakari = true;
                    System.out.println("Sakari: \"Oh... I understand...\"");
                    System.out.println("Sakari: \"I know 3 digits of her password, and I can tell you.\"");
                    System.out.println("Sakari: \"But first I need you to go get me something.\"");
                    System.out.println("Sakari: \"I left my key in your classroom. If you don't mind, can you go get it for me?\"");
                }
                else System.out.println("Sakari: \"Please feel free to discuss your problems with me.\"");
            }
            else {
                if (!p.getBag().hasItem("key"))
                    System.out.println("Sakari: \"Please bring me the key I left in your classroom.\"");
                else {
                    System.out.println("Sakari: \"Thank you for your hard work.\"");
                    finishMission(p,"Sakari",masterPassword.substring(7, 10));
                    sakar1 = true;
                }
            }
        }
        else {
            System.out.println("Sakari: \"Hi, "+p.name()+"! You seem to be busy.\"");
            System.out.println("Sakari: \"Well, good luck with whatever you're doing.\"");
        }
    }
        // Timo's Event
    public boolean timoStart() {
        return timo;
    }
    public boolean timoFinish() {
        return tim1;
    }
    private void timoRoom(Player p, Scanner reader, Room r) {
        boolean proceed = true;
        if (tim1) {
            if (random.nextBoolean()) proceed = number1(reader);
            else proceed = number2(reader);
        }
        if (!hasOtherEvent()) {
            if (proceed) {
                System.out.println("You are in room " + r);
                r.description();
                if (!tim1) {
                    while (true) {
                        System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
                        if (command.contains("talk")&&command.contains("timo")) {
                            timoEvent(p,reader);
                            if (tim1) r.addDescription("This is Timo's room. Looks like he's gone somewhere.");
                            System.out.println("You left the room.");
                            break;
                        }
                        else if (command.contains("exit") 
                                || command.contains("leave"))
                            break; 
                        else commandList(p,command,r);
                    }
                }
                else {
                    System.out.println("Your mom's voice echoed in your mind:\n\"My dear "+p.name()+", it's impolite to be in someone's room without their permission.\"");
                    System.out.println("So you left the room.");
                }
            }
        }
        else System.out.println("You knocked on the door but there was no reply.\nMaybe Timo is teaching.");
    }
    private void timoEvent(Player p, Scanner reader) {
        String command;
        if (!timo) {
            System.out.println("Timo: \"Hello! How may I help you?\"");
            System.out.print("Do you want to tell him about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
            while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
                System.out.println("I don't understand...");
                System.out.print("Do you want to tell him about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
            }
            if (command.equalsIgnoreCase("yes")) {
                timo = true;
                System.out.println("Timo: \"Oh... I understand...\"");
                System.out.println("Timo: \"I know 3 digits of her password, and I can tell you.\"");
                System.out.println("Timo: \"But first I need you to help me with something.\"");
                System.out.println("Timo: \"I just taught Group A, and my little experiment accidentally caused a mess in their classroom.\"");
                System.out.println("Timo: \"Help me clean that classroom first, I will tell you afterward.\"");
            }
            else System.out.println("Timo: \"Please feel free to discuss your problems with me.\"");
        }
        else {
            if (!clean)
                System.out.println("Timo: \"Help me clean Group A's classroom while I grade their homework, will you?\"");
            else {
                System.out.println("Timo: \"Thank you for your hard work.\"");
                finishMission(p,"Timo",masterPassword.substring(5, 8));
                tim1 = true;
            }
        }
    }
    private void equipmentRoom(Player p, Scanner reader, Room r) {
        Item temp = new Item("broom"); temp.addAtk(2); r.addItem(temp);
        if (random.nextInt(100)<50) {
            temp = new Item("baseball bat"); temp.addAtk(5); r.addItem(temp);
        }
        if (random.nextInt(100)<50) {
            temp = new Item("trash can lid"); temp.addDef(5); r.addItem(temp);
        }
        if (random.nextInt(100)<10) {
            temp = new Item("dart box"); temp.addAtk(10); r.addItem(temp);
        }
        if (random.nextInt(100)<10) {
            temp = new Item("steel trash can lid"); temp.addDef(10); r.addItem(temp);
        }
        if (random.nextInt(1000)<1){
            temp = new Item("taser gun"); temp.addDef(50); r.addItem(temp);
        }
        System.out.println("You are in room "+r); r.description();
        while (true) {
            System.out.print("Type your command: "); String command = reader.nextLine().trim();
            if (command.contains("exit") 
                        || command.contains("leave"))
                    break;
            else commandList(p,command,r);
        }
    }
    private void aRoom(Player p, Scanner reader, Room r){
        if (!timo&&hasOtherEvent()) System.out.println("You heard Timo's voice in the classroom.\nMaybe you shouldn't enter.");
        else {
            System.out.println("You are in room " + r); r.description();
            if (clean) System.out.println("The room looks spotless.");
            else System.out.println("The room looks like it's been visited by a tornado.");
            while (true) {
                System.out.print("Type your command: "); String command = reader.nextLine().trim();
                if (command.contains("clean")&&command.contains("class")) {
                    if (!clean) {
                        if (!p.getBag().hasItem("broom")) System.out.println("You have nothing to clean the class with.\nHow about stopping at the equipment room and get a broom?");
                        else {
                            clean = true;
                            System.out.println("After a while, you have finished cleaning the classroom.\nThe class room now looks spotless.");
                        }
                    }
                    else System.out.println("You have already done that. No need to exhaust yourself.");
                }
                else if (command.contains("exit") 
                        || command.contains("leave"))
                    break; 
                else commandList(p,command,r);
            }
        }
    }
        // Library's Event
    public boolean marjoStart() {
        return marjo;
    }
    public boolean marjoFinish() {
        return marj1;
    }
    private void read(Scanner reader) {
        System.out.println("There is a red book and a blue book. Which one do you like to read?");
        String command = reader.nextLine().trim().toLowerCase();
        if (command.contains("red")) {
            System.out.println("This book has stories about winter, Christmas and spring time.");
            System.out.println("You know that thanks to the word 'talvi' which means 'winter', and the word 'kevät' which means 'spring'");
        }
        else {
            System.out.println("This book has a lot of photos about summerand autumn.");
            System.out.println("You know that thanks to the word 'kesä' which means 'summer', and the word 'syksy' which means 'autumn'");
        }
    }
    private void library(Player p, Scanner reader, Room r) {
        System.out.println("You are in the library.");
        r.addDescription("This place is full of books and magazines.\nSomeone has left 2 books on the table in front of you.");
        r.description();
        if (!marj1) {
            System.out.println("You saw Marjo-Riitta sitting in the center of the library.");
            while (true) {
                System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
                if (command.contains("talk")&&command.contains("marjo")&&command.contains("riitta"))
                    marjoEvent(p,reader);
                else if (command.contains("read")&&command.contains("book")) read(reader);
                else if (command.contains("exit") 
                        || command.contains("leave"))
                    break; 
                else commandList(p,command,r);
            }
        }
        else {
            while (true) {
                System.out.print("Type your command: "); String command = reader.nextLine().trim();
                if (command.contains("read")&&command.contains("book")) read(reader);
                else if (command.contains("exit") 
                        || command.contains("leave"))
                    break;
                else commandList(p,command,r);
            }
        }
    }
    private void marjoEvent (Player p, Scanner reader) {
        String command;
        if (!hasOtherEvent()) {
            if (!marjo) {
                System.out.println("Marjo-Riitta: \"Hello "+p.name()+"! May I help you?\"");
                System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
                    System.out.println("I don't understand...");
                    System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                }
                if (command.equalsIgnoreCase("yes")) {
                    marjo = true;
                    System.out.println("Marjo-Riitta: \"Oh... I see...\"");
                    System.out.println("Marjo-Riitta: \"I know 3 digits of her password, and I can tell you.\"");
                    System.out.println("Marjo-Riitta: \"But only if you pass the Finnish test I'm going to give you now.\"");
                    System.out.println("Are you ready? "); command = reader.nextLine().trim();
                    if (command.contains("yes")) {
                        if (marjoQuiz(reader)) {
                            finishMission(p,"Marjo-Riitta",masterPassword.substring(2, 5));
                            marj1 = true;
                        }
                    }
                    else System.out.println("Marjo-Riitta: \"Come see me when you're ready.\"");
                }
                else System.out.println("Marjo-Riitta: \"Please feel free to discuss your problems with me.\"");
            }
            else {
                System.out.println("Marjo-Riitta: \"Now I will give you a small Finnish test.\"");
                System.out.println("Are you ready? "); command = reader.nextLine().trim();
                if (command.contains("yes")) {
                    if (marjoQuiz(reader)) {
                        finishMission(p,"Marjo-Riitta",masterPassword.substring(2, 5));
                        marj1 = true;
                    }
                    else System.out.println("Marjo-Riitta: \"Come back again when you're more prepared.\"");
                }
                else System.out.println("Marjo-Riitta: \"Come see me when you're ready.\"");
            }
        }
        else {
            System.out.println("Marjo-Riitta: \"Hi! You seem to be doing something, are you?\"");
            System.out.println("Marjo-Riitta: \"I don't think what you're looking for is in this library, but you're welcome here.\"");
        }
    }
    private boolean marjoQuiz(Scanner reader) {
        String[] result = {"kevät","kesä","syksy","talvi","kevat","kesa"}, answer = new String[4];
        int c = 0;
        System.out.println("Marjo-Riitta: \"What is 'spring' in Finnish?\""); System.out.print("Your answer: "); answer[0] = reader.nextLine().trim().toLowerCase();
        System.out.println("Marjo-Riitta: \"What is 'summer' in Finnish?\""); System.out.print("Your answer: "); answer[1] = reader.nextLine().trim().toLowerCase();
        System.out.println("Marjo-Riitta: \"What is 'autumn' in Finnish?\""); System.out.print("Your answer: "); answer[2] = reader.nextLine().trim().toLowerCase();
        System.out.println("Marjo-Riitta: \"What is 'winter' in Finnish?\""); System.out.print("Your answer: "); answer[3] = reader.nextLine().trim().toLowerCase();
        if (answer[0].equals(result[4])) {
            System.out.print("Marjo-Riitta: \"You missed the two dots in '"+result[0]);
            if (answer[1].equals(result[5])) System.out.print("' and '"+result[1]);
            else System.out.println("'.\"");
            System.out.println("Marjo-Riitta: \"But Your vocabulary impressed me, so I won't be strict on that.\"");
        }
        else if (answer[1].equals(result[5])) 
            System.out.print("Marjo-Riitta: \"You missed the two dots in '"+result[1]+"'.\"\nMarjo-Riitta: \"But Your vocabulary impressed me, so I won't be strict on that.\"");
        if (answer[0].equals(result[0])||answer[0].equals(result[4])) c++;
        if (answer[1].equals(result[1])||answer[1].equals(result[5])) c++;
        if (answer[2].equals(result[2])) c++;
        if (answer[3].equals(result[3])) c++;
        System.out.println("Marjo-Riitta: \"You got "+c+"/4 answers right.\"");
        if (c==4) {
            System.out.println("Marjo-Riitta: \"Congratulations!!! You passed the test.\"");
            return true;
        }
        else return false;
    }
        // Marianne's Event
    public boolean marianneStart() {
        return marianne;
    }
    public boolean marianneFinish(){
        return mariann1;
    }
    private void marianneRoom(Player p, Scanner reader, Room r) {
        if (marj1&&tim1&&sakar1&&ulla1) {
            boolean proceed = true;
            if (mariann1) {
                if (random.nextBoolean()) proceed= number1(reader);
                else proceed = number2(reader);
            }
            if (proceed) {
                System.out.println("You are in room " + r);
                r.description();
                if (!mariann1) {
                    while (true) {
                        System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
                        if (command.contains("talk")&&command.contains("marianne")) {
                            marianneEvent(p,reader);
                            if (mariann1) r.addDescription("This is Marianne's room. Looks like she's gone somewhere.");
                            System.out.println("You left the room.");
                            break;
                        }
                        else if (command.contains("exit") 
                                || command.contains("leave"))
                            break; 
                        else commandList(p,command,r);
                    }
                }
                else {
                    System.out.println("Your mom's voice echoed in your mind:\n\"My dear "+p.name()+", it's impolite to be in someone's room without their permission.\"");
                    System.out.println("So you left the room.");
                }
            }
        }
        else System.out.println("You knocked on the door but there was no reply.\nMaybe you should come back later..."); 
    }
    private void marianneEvent(Player p, Scanner reader) {
        String command;
        if (!marianne) {
            System.out.println("Marianne: \"Welcome to my office! How may I help you?\"");
            System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
            while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
                System.out.println("I don't understand...");
                System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
            }
            if (command.equalsIgnoreCase("yes")) {
                marianne = true;
                System.out.println("Marianne: \"Oh... I understand...\"");
                System.out.println("Marianne: \"I know 3 digits of her password, and I can tell you.\"");
                System.out.println("Marianne: \"But first I will give you a small quiz to see whether you are capable of rescuing her.\"");
                System.out.println("Are you ready? "); command = reader.nextLine().trim();
                if (command.contains("yes")) {
                    if (marianneGame(reader,p.getLuck())) {
                        finishMission(p,"Marianne",masterPassword.substring(4, 7));
                        mariann1 = true;
                    }
                }
                else System.out.println("Marianne: \"Come see me when you're ready.\"");
            }
            else System.out.println("Marianne: \"Please feel free to discuss your problems with me.\"");
        }
        else {
            System.out.println("Marianne: \"Now I will give you a small quiz to see whether you are capable of rescuing her.\"");
                System.out.println("Are you ready? "); command = reader.nextLine().trim();
                if (command.contains("yes")) {
                    if (marianneGame(reader,p.getLuck())) {
                        finishMission(p,"Marianne",masterPassword.substring(4, 7));
                        mariann1 = true;
                    }
                    else System.out.println("Marianne: \"Come see me when you're prepared.\"");
                }
                else System.out.println("Marianne: \"Come see me when you're ready.\"");
        }
    }
    private boolean marianneGame(Scanner reader, boolean difficulty) {
        String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        String[] dir = {"up","left","down","right"};
        int d = 0;
        if (difficulty) d = random.nextInt(4);
        ArrayList<SeasonEvent> map = new ArrayList<>();
        String command;
        int i, t=100;
        for (i = 0; i < 12; i++) map.add(i, new SeasonEvent(month[i]));
        SeasonEvent pt = map.get(0);
        for (i = 0; i < 12; i++)
            if (i%2 == 0) {
                map.get(i).addNext(dir[(d+2)%4], map.get((i+1)%12));
                map.get(i).addNext(dir[(d+3)%4], map.get((i+4)%12));
                map.get(i).addNext(dir[d], map.get((i+7)%12));
                map.get(i).addNext(dir[(d+1)%4], map.get((i+10)%12));
            }
            else {
                map.get(i).addNext(dir[d], map.get((i+1)%12));
                map.get(i).addNext(dir[(d+1)%4], map.get((i+4)%12));
                map.get(i).addNext(dir[(d+2)%4], map.get((i+7)%12));
                map.get(i).addNext(dir[(d+3)%4], map.get((i+10)%12));
            }
        i=0;
        System.out.println("In this game, you are put in another dimension, and your mission is to go through the rooms in a chronological order.");
        System.out.println("You have to find out the pattern or rule of the dimension to get to every location.");
        System.out.println("Be careful! If you go to the wrong room, you have to find and start over at the first room.");
        System.out.println("You have "+t+" moves to go. If you cannot solve the puzzle within "+t+" moves, you'll lose.");
        while (i < 12 && t > 0) {
            if (pt.name().equals(month[i])) i++;
            else i = 0;
            if (i == 12) break;
            System.out.println("Moves left: "+ (t--));
            System.out.println(pt); System.out.print("Where do you want to go? (up/left/down/right) ");
            command = reader.nextLine().trim();
            while (!direction(command)) {
                System.out.println("That's not a valid direction."); System.out.print("Where do you want to go? (up/left/down/right) ");
            }
            if (command.equalsIgnoreCase("hint")) 
                System.out.println("You need to continuously go "+dir[(d+2)%4]+" and "+dir[d]+" to win.");
            else pt.go(command);
        }
        if (i == 12) {
            System.out.println("Congratulation!!! You solved Marianne's Puzzle.");
            return true;
        }
        else return false;
    }
        // Ulla P's Event
    public boolean ullaStart() {
        return ullaP;
    }
    public boolean ullaFinish() {
        return !ulla1;
    }
    private void ullaRoom(Player p, Scanner reader, Room r) {
        boolean proceed = true;
        if (ulla1) {
            if (random.nextBoolean()) proceed= number1(reader);
            else proceed = number2(reader);
        }
        if (proceed) {
            System.out.println("You are in room " + r);
            r.description();
            if (!ulla1) {
                while (true) {
                    System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
                    if (command.contains("talk")&&command.contains("ulla")) {
                        ullaEvent(p,reader);
                        if (ulla1) r.addDescription("This is Ulla Paatola's room. Looks like she's gone somewhere.");
                        System.out.println("You left the room.");
                        break;
                    }
                    else if (command.equalsIgnoreCase("exit") 
                            || command.equalsIgnoreCase("leave")
                            || command.equalsIgnoreCase("leave room")
                            || command.equalsIgnoreCase("exit room"))
                        break; 
                    else commandList(p,command,r);
                }
            }
            else {
                System.out.println("Your mom's voice echoed in your mind:\n\"My dear "+p.name()+", it's impolite to be in someone's room without their permission.\"");
                System.out.println("So you left the room.");
            }
        }
    }
    private void ullaEvent(Player p, Scanner reader) {
        String command;
        if (!hasOtherEvent()) {
            if (!ullaP) {
                System.out.println("Ulla Paatola: \"Welcome to my office! How may I help you?\"");
                System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
                    System.out.println("I don't understand...");
                    System.out.print("Do you want to tell her about the situation (only YES/NO for answer)? "); command = reader.nextLine().trim();
                }
                if (command.equalsIgnoreCase("yes")) {
                    ullaP = true;
                    System.out.println("Ulla Paatola: \"Oh... I understand...\"");
                    System.out.println("Ulla Paatola: \"I know 3 digits of her password, and I can tell you.\"");
                    System.out.println("Ulla Paatola: \"But first I will give you a small quiz to see whether you are capable of rescuing her.\"");
                    System.out.println("Are you ready? "); command = reader.nextLine().trim();
                    if (command.contains("yes")) {
                        if (ullaGame(reader,p.getLuck())) {
                            finishMission(p,"Ulla Paatola",masterPassword.substring(0, 3));
                            ulla1 = true;
                        }
                    }
                    else System.out.println("Ulla Paatola: \"Come see me when you're ready.\"");
                }
                else System.out.println("Ulla Paatola: \"Please feel free to discuss your problems with me.\"");
            }
            else {
                System.out.println("Ulla Paatola: \"Now I will give you a small quiz to see whether you are capable of rescuing her.\"");
                    System.out.println("Are you ready? "); command = reader.nextLine().trim();
                    if (command.contains("yes")) {
                        if (ullaGame(reader,p.getLuck())) {
                            finishMission(p,"Ulla Paatola",masterPassword.substring(0, 3));
                            ulla1 = true;
                        }
                        else System.out.println("Ulla Paatola: \"Come see me when you're prepared.\"");
                    }
                    else System.out.println("Ulla Paatola: \"Come see me when you're ready.\"");
            }
        }
        else {
            System.out.println("Ulla Paatola: \"Hi! You seem to be on some tasks, are you?\"");
            System.out.println("Ulla Paatola: \"I don't think what you're looking for is in this office, but you're welcome to come here when you're finish with your task.\"");
        }
    }
    private boolean ullaGame(Scanner reader, boolean difficulty) {
        String command;
        String[] seasons = {"SPRING","SUMMER","AUTUMN","WINTER"}, direction = {"up","left","down","right"};
        SeasonEvent sp = new SeasonEvent(seasons[0]);
        SeasonEvent su = new SeasonEvent(seasons[1]);
        SeasonEvent au = new SeasonEvent(seasons[2]);
        SeasonEvent wi = new SeasonEvent(seasons[3]);
        SeasonEvent pt = sp;
        int c = 0, t = 50;
        int dir = 0;
        if (difficulty) dir = random.nextInt(4);
        sp.addNext(direction[dir], sp); sp.addNext(direction[(dir+1)%4], su); sp.addNext(direction[(dir+2)%4], au); sp.addNext(direction[(dir+3)%4], wi);
        su.addNext(direction[dir], wi); su.addNext(direction[(dir+1)%4], sp); su.addNext(direction[(dir+2)%4], su); su.addNext(direction[(dir+3)%4], au);
        au.addNext(direction[dir], au); au.addNext(direction[(dir+1)%4], wi); au.addNext(direction[(dir+2)%4], sp); au.addNext(direction[(dir+3)%4], su);
        wi.addNext(direction[dir], su); wi.addNext(direction[(dir+1)%4], au); wi.addNext(direction[(dir+2)%4], wi); wi.addNext(direction[(dir+3)%4], sp);
        System.out.println("In this game, you are put in another dimension, and your mission is to go through the rooms in a chronological order.");
        System.out.println("You have to find out the pattern or rule of the dimension to get to every location.");
        System.out.println("Be careful! If you go to the wrong room, you have to find and start over at the first room.");
        System.out.println("You have "+t+" moves to go. If you cannot solve the puzzle within "+t+" moves, you'll lose.");
        while (c < 4 && t > 0) {
            if (pt.name().equals(seasons[c])) c++;
            else c = 0;
            System.out.println("Moves left: "+ (t--));
            System.out.println(pt); System.out.print("Where do you want to go? (up/left/down/right) ");
            command = reader.nextLine().trim();
            while (!direction(command)) {
                System.out.println("That's not a valid direction."); System.out.print("Where do you want to go? (up/left/down/right) ");
            }
            if (!command.equalsIgnoreCase("hint")) pt.go(command);
            else System.out.println("You need to go "+direction[(dir+1)%4]+", "+direction[(dir+3)%4]+", "+direction[(dir+1)%4]+" to win.");
        }
        if (c == 4) {
            System.out.println("Congratulation!!! You solved Ulla's Puzzle.");
            return true;
        }
        else return false;
    }
        // Both games
    private boolean direction(String a) {
        return a.equalsIgnoreCase("hint")||a.equalsIgnoreCase("up")||a.equalsIgnoreCase("down")||a.equalsIgnoreCase("left")||a.equalsIgnoreCase("right");
    }
        // Check ongoing missions
    public boolean hasOtherEvent() {
        return ((ullaP&&!ulla1)||(sakari&&!sakar1)||(marjo&&!marj1)||(timo&&!tim1));
    }
    private void finishMission(Player p, String name, String password) {
        System.out.println(name+": \"As I promised, here are the 3 digits that I know.\"");
        System.out.println(name+": \"They are '"+password+"'. Not so easy if you have to guess right?\"");
        System.out.println("You wrote the password on your notes.");
        p.getBag().addNote(name+"'s password: "+password);
        System.out.println(name+": \"I must tell you, I don't know the correct position of those digits.");
        System.out.println(name+": \"Also I'm not sure whether the other teachers have any digits that I've already had.");
        System.out.println(name+": \"I have to leave now. See you later.\"");
    }
        // Triggers when player goes to another location 
    public boolean enemy(Player p, Scanner reader) {
            // Equip player with weapons and armor
        p.equip();
            // Calculate the chance of encountering the enemy
        int chance = random.nextInt(1000); 
            // If player encountered the enemy, encounter is true
            // If player can proceed to next location, proceed is true
        boolean encounter = false, proceed = true; 
            // Default values
        String command, display = "You encountered a bully on the way.";
        Enemy enemy = new Enemy("normal");
           // Generate the enemy if player encountered
        if (p.getLuck()) {
            if (chance < 1 && p.getDef() > 10 && p.getAtk() > 10) {
                enemy = new Enemy("gangster");
                display = "Uh oh... You encountered a gangster.";
                encounter = true;
            }
            else if (chance < 10) {
                enemy = new Enemy("bulky");
                display = "Uh oh... You encountered a pretty tough bully.";
                encounter = true;
            }
            else if (chance < 100) encounter = true;
        }
        else {
            if (chance < 3 && p.getDef() > 10 && p.getAtk() > 10) {
                enemy = new Enemy("gangster");
                display = "Uh oh... You encountered a gangster.";
                encounter = true;
            }
            else if (chance < 100) {
                enemy = new Enemy("bulky");
                encounter = true;
            }
            else if (chance < 350) encounter = true;
        }
            // Start battle
        if (encounter) {
            System.out.println(display);
            System.out.println("Foe's health point: " + enemy.getHP() + "/" + enemy.getMax());
            System.out.println("Your health point: " + p.getHP() + "/" + p.getMax());
            while (encounter&&proceed) {
                while (true) {
                    System.out.print("Do you want to FIGHT or RUN? "); command = reader.nextLine().trim().toLowerCase();
                    if (((command.equalsIgnoreCase("fight"))||command.equalsIgnoreCase("run"))) break;
                    System.out.println("I'm afraid that's not an option.");
                }
                if (command.equalsIgnoreCase("run")) {
                    chance = random.nextInt(100);
                    if (chance < 5) {
                        System.out.println("The enemy blocked the way.");
                        System.out.println("The foe attacked");
                        chance = random.nextInt(100);
                        if (((p.getLuck())&&(chance < 65))||((!p.getLuck())&&(chance < 80))) {
                            System.out.println("!");
                            proceed = p.attacked(enemy.getAtk());
                        }
                        else System.out.println(", but you're too fast for him to get you.");
                        System.out.println("Your health point: " + p.getHP() + "/" + p.getMax());
                        if (proceed) {
                            System.out.print("You striked back! ");
                            chance = random.nextInt(100);
                            if (((p.getLuck())&&(chance < 95))||((!p.getLuck())&&(chance < 80))) {
                                System.out.println("");
                                encounter = enemy.attacked(p.getAtk()); 
                            }
                            else System.out.println("The foe successfully dodged.");
                            System.out.println("Foe's health point: " + enemy.getHP() + "/" + enemy.getMax());
                        }
                    }
                    proceed = false;
                    System.out.println("You got back to " + p.getLocation() + ".");
                }
                else {
                    System.out.print("You attacked! ");
                    chance = random.nextInt(100);
                    if (((p.getLuck())&&(chance < 95))||((!p.getLuck())&&(chance < 80))) {
                        System.out.println("");
                        encounter = enemy.attacked(p.getAtk()); 
                    }
                    else System.out.println("The foe successfully dodged.");
                    System.out.println("Foe's health point: " + enemy.getHP() + "/" + enemy.getMax());
                    if (encounter) {
                        System.out.println("The foe attacked");
                        chance = random.nextInt(100);
                        if (((p.getLuck())&&(chance < 65))||((!p.getLuck())&&(chance < 80))) {
                            System.out.println("!");
                            proceed = p.attacked(enemy.getAtk());
                        }
                        else System.out.println(", but you're too fast for him to get you.");
                        System.out.println("Your health point: " + p.getHP() + "/" + p.getMax());
                    }
                }
            }
        }
        return proceed;
    }
        // Triggers when player goes to a room
    public void gotoRoom(Player p, Scanner reader, Room r) {
        switch (r.eventCode()) {
            case 1: 
                homeRoom(p,reader,r);
                break;
            case 2: 
                ullaRoom(p,reader,r);
                break;
            case 3:
                sakariRoom(p,reader,r);
                break;
            case 4:
                timoRoom(p,reader,r);
                break;
            case 5:
                marianneRoom(p,reader,r);
                break;
            case 6:
                if (number2(reader)) aRoom(p,reader,r);
                break;
            case 7:
                if (number1(reader)) {
                    r.removeAllItems();
                    equipmentRoom(p,reader,r);
                }
                break;
            case 8:
                library(p,reader,r);
                break;
            default:
                int pw = random.nextInt(20); 
                boolean proceed;
                switch (pw) {
                    case 0: case 1: case 2: proceed = number1(reader); break;
                    case 3: case 4: case 5: proceed = number2(reader); break;
                    case 6: proceed = password1(reader); break;
                    case 7: proceed = password2(reader); break;
                    case 8: proceed = password3(reader); break;
                    case 9: proceed = password4(reader); break;
                    default: proceed = true; break;
                }
                if (proceed) normalRoom(p,reader,r);
                break;
        }
        System.out.println("You are in: "+p.getLocation());
    }
    private void homeRoom(Player p, Scanner reader, Room r) {
        System.out.println("You are in room " + r);
        int test = 0;
        if (gameStart) {
            System.out.println("");
            gameStart = false;
        }
        else if (sakari&&(test++ == 0)) r.addItem(new Item("key"));
        else r.description();
        while (true) {
            System.out.print("Type your command: "); String command = reader.nextLine().trim().toLowerCase();
            
            if (command.contains("exit") 
                    || command.contains("leave"))
                break; 
            else commandList(p,command,r);
        }
    }
    private void normalRoom(Player p, Scanner reader, Room r) {
        Item temp = new Item("thick carton box"); temp.addDef(2);
        if (!r.hasItem(temp.getName())&&random.nextBoolean()) r.addItem(temp);
        temp = new Item("trash can lid"); temp.addDef(5);
        if (!r.hasItem(temp.getName()) && random.nextInt(10) < 3) r.addItem(temp);
        System.out.println("You are in room " + r);
        r.description();
        while (true) {
            System.out.print("Type your command: "); String command = reader.nextLine().trim();
            
            if (command.contains("exit") 
                    || command.contains("leave"))
                break; 
            else commandList(p,command,r);
        }
    }
        // List of normal commands inside a room
    private void commandList(Player p, String command, Room r) {
        if (command.startsWith("check")||command.startsWith("view "))
            command = command.substring(5).trim().toLowerCase();
        if (command.substring(0, 4).equalsIgnoreCase("take")) {
            boolean hasItem = false;
            String item = command.substring(4).trim().toLowerCase();
            for (Item temp : r.getItemList()) 
                if (temp.getName().equalsIgnoreCase(item)) {
                    p.take(temp); r.removeItem(temp);
                    hasItem = true;
                    break;
                }
            if (!hasItem) System.out.println("You can't take that.");
        }
        else if (command.equalsIgnoreCase("look around") || command.equalsIgnoreCase("look")) {
            System.out.print("You looked around room " + r + ". "); r.description();
        }
        else if (command.equalsIgnoreCase("inventory")) {
            p.inventory();
        }
        else if (command.equalsIgnoreCase("location")) {
            System.out.println("You are in room " + r + ".");
        }
        else if (command.substring(0,4).contains("stat"))
            p.stats();
        else if (command.contains("note")&&command.contains("read")) p.getBag().checkNote();
        else if (command.equalsIgnoreCase("help")) this.help();
        else System.out.println("I'm afraid I don't understand?");
    }
    public void help() {
        System.out.println("Here are some of the available commands:");
        System.out.println(String.format("%-30s%-30s%-30s","- TAKE something","- LOOK AROUND","- LOOK INSIDE a room"));
        System.out.println(String.format("%-30s%-30s%-30s","- GO TO places","- view accessible LOCATION LIST","- check available ROOM LIST"));
        System.out.println(String.format("%-30s%-30s%-30s","- check INVENTORY","- check current LOCATION","- check player's STATS"));
        System.out.println(String.format("%-30s%-30s%-30s","- EXIT a room","- QUIT game","- TALK TO someone"));
        System.out.println("- READ your NOTE or a BOOK");
        System.out.println("More specific commands in specific tasks or locations will be instructed clearly in-game.");
    }
        // Number games
    public boolean number1(Scanner reader) {
        int number = random.nextInt(1000), guessNum;
        String guess;
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this door is a 3-digit number.");
        System.out.println("You have 10 chances to guess what it is.");
        System.out.println("Each time you take a guess, the computer will tell you\nwhether your number is greater or smaller than the password.");
        for (int i = 10; i > 0; i--) {
            System.out.println("Chance(s) left: " + i);
            System.out.print("Your guess: "); guess = reader.nextLine();
            while (!(isNumber(guess)&&(guess.length() == 3))) {
                System.out.println("You did not enter a legit password!");
                System.out.print("Your guess: "); guess = reader.nextLine();
            }
            guessNum = Integer.parseInt(guess);
            if (guessNum > number) System.out.println("Your number is greater than the password.");
            else if (guessNum < number) System.out.println("Your number is smaller than the password.");
            else {
                System.out.println("You guessed the password!!!");
                return true;
            }
        }
        System.out.println("You failed to guess the password.\nIt is " + number/100%10 + "" + number/10%10 + "" + number%10);
        return false;
    }
    public boolean number2(Scanner reader) {
            // Create the password
        String digits = "1234567890";
        int pointer = random.nextInt(10);
        String password = "" + digits.charAt(pointer);
        do {
            pointer = random.nextInt(10);
            if (!password.contains(""+digits.charAt(pointer))) password += digits.charAt(pointer);
        } while (password.length() < 4);
            // User's game
        String guess;
        int correct, incorrect;
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this door is a 4-digit number, each digit appears one at most.");
        System.out.println("You have 10 chances to guess what it is.");
        System.out.println("Each time you take a guess, the computer will tell you\nhow many correct digits you have guessed in the correct position,\nand how many correct digits you have guessed in an incorrect position.");
        for (int i = 10; i > 0; i--) {
            correct = incorrect = 0;
            System.out.println("Chance(s) left: " + i);
            System.out.print("Your guess: "); guess = reader.nextLine();
            while (!(isNumber(guess)&&(guess.length() == 4)&&differentDigits(guess))) {
                System.out.println("You did not enter a legit password!");
                System.out.print("Your guess: "); guess = reader.nextLine();
            }
            if (guess.equals(password)) {
                System.out.println("You guessed the password!!!"); return true;
            }
            else for (int j = 0; j < guess.length(); j++) 
                if (guess.charAt(j) == password.charAt(j)) correct++;
                else if (password.contains(""+guess.charAt(j))) incorrect++;
            System.out.println("Number of digits in correct position: " + correct);
            System.out.println("Number of digits in incorrect position: " + incorrect);
        }
        System.out.println("You failed to guess the password.\nIt is "+password);
        return false;
    }
    private boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++)
            if ((s.charAt(i) < '0')||(s.charAt(i) > '9')) return false;
        return true;
    }
    private boolean differentDigits(String s) {
        for (int i = 0; i < s.length() - 1; i++)
            for (int j = i+1; j < s.length(); j++)
                if (s.charAt(i) == s.charAt(j)) return false;
        return true;
    }
        // Real life password
    private boolean password1(Scanner reader) {
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this room is on the green sign outside of room B205.\nIf you can't remember it, step outside and read.");
        String password;
        System.out.println("Enter the password: ");
        password = reader.nextLine().trim().toLowerCase();
        while (!password.equalsIgnoreCase("varattu")) {
            System.out.println("Incorrect!");
            System.out.println("Enter the password: ");
            password = reader.nextLine().trim().toLowerCase();
        }
        System.out.println("Correct!");
        return true;
    }
    private boolean password2(Scanner reader) {
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this room is the \"room number\" on the corridor\nwhich connects A and B building on the first floor.\nIf you have no clue, take a walk and check it.");
        String password;
        System.out.println("Enter the password: ");
        password = reader.nextLine().trim().toLowerCase();
        while (!password.equalsIgnoreCase("incorrect")) {
            System.out.println("The password is incorrect.");
            System.out.println("Enter the password: ");
            password = reader.nextLine().trim().toLowerCase();
        }
        System.out.println("Correct!");
        return true;
    }
    private boolean password3(Scanner reader) {
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this room is the smallest locker number\nin the hallway outside of room B205.\nIf you don't know, walk outside and check it.");
        String password;
        System.out.println("Enter the password: ");
        password = reader.nextLine().trim().toLowerCase();
        while (password.equalsIgnoreCase("b088")) {
            System.out.println("Incorrect!");
            System.out.println("Enter the password: ");
            password = reader.nextLine().trim().toLowerCase();
        }
        System.out.println("Correct!");
        return true;
    }
    private boolean password4(Scanner reader) {
        System.out.print("Someone has locked the door. ");
        System.out.println("The password for this room is the room number of the handicapped restroom\nin the second floor of B building.\nYou should check that out if you go \"take care of your business\".");
        String password;
        System.out.println("Enter the password: ");
        password = reader.nextLine().trim().toLowerCase();
        while (!password.equalsIgnoreCase("b217")) {
            System.out.println("Incorrect!");
            System.out.println("Enter the password: ");
            password = reader.nextLine().trim().toLowerCase();
        }
        System.out.println("Correct!");
        return true;
    }
}
