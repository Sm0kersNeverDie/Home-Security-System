/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;

import java.io.Serializable;

/**
 *
 * @author Edward
 */
public class Item implements Serializable {
    private String name, description;
    private int quantity, atk = 0, def = 0;
    private boolean isW = false, isA = false; //check whether an item is weapon or armor
    
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public Item(String name) {
        this(name, 1);
    }
    public void add() {
        this.quantity++;
    }
    public void add(int num) {
        this.quantity += num;
    }
    public boolean use() {
        return (--this.quantity == 0);
    }
    public String getName() {
        return this.name;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void addAtk(int atk) {
        this.isW = true;
        this.atk = atk;
    }
    public void addDef(int def) {
        this.isA = true;
        this.def = def;
    }
    public boolean isWeapon() {
        return this.isW;
    }
    public boolean isArmor() {
        return this.isA;
    }
    public int getAtk() {
        return this.atk;
    }
    public int getDef() {
        return this.def;
    }
    public void addDescription(String des) {
        this.description = des;
    }
    public void showDescription() {
        System.out.println(this.description);
    }
    @Override
    public String toString() {
        return this.name + " x " + this.quantity;
    }
    
}
