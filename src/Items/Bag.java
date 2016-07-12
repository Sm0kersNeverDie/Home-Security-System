/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Edward
 */
public class Bag implements Serializable {
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private ArrayList<String> notes = new ArrayList<String>();
    public Bag(){
        
    };
    public void addItem(Item item) {
        boolean hasItem = false;
        for (Item temp : itemList) if (temp.getName().equals(item.getName())) {
            hasItem = true;
            temp.add(item.getQuantity());
        }
        if (!hasItem) itemList.add(item);
    }
    public void useItem(String name) {
        boolean hasItem = false;
        for (Item temp : itemList) if (temp.getName().equals(name)) {
            hasItem = true;
            if (temp.use()) itemList.remove(temp);
        }
        if (!hasItem) System.out.println("You don't have such item.");
    }
    public void discard(String name, Scanner reader) {
        boolean hasItem = false;
        for (Item temp : itemList) if (temp.getName().equals(name)) {
            hasItem = true;
            System.out.println("Do you want to throw away " + temp.getQuantity() + " " + temp.getName() + "(s)?");
            System.out.print("You cannot undo this! Type YES to continue or any other key to cancel: ");
            String cmd = reader.nextLine();
            if (cmd.trim().toLowerCase().equals("yes")) {
                itemList.remove(temp);
                System.out.println("Item successfully discarded.");
            }
            else System.out.println("You cancelled the command.");
        }
        if (!hasItem) System.out.println("You don't have such item.");
    }
    public ArrayList<Item> getItemList() {
        return this.itemList;
    }
    public boolean hasItem(String item) {
        for (Item temp : itemList) 
            if (temp.getName().equalsIgnoreCase(item)) 
                return true;
        return false;
    }
    public void inventory() {
        System.out.println("Your bag contains:");
        for (Item temp : itemList) System.out.println(temp);
        System.out.println("\nYou also have some paper to take note.");
    }
    public void checkNote() {
        if (notes.isEmpty()) System.out.println("You haven't written down anything yet.");
        else {
            System.out.println("You look at the notes you have written down.");
            int c = 1;
            for (String temp : notes) System.out.println((c++) + ". " + temp);
        }
    }
    public void addNote(String note) {
        notes.add(note);
    }
}
