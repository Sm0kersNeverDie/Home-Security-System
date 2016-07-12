/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Character;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class Enemy {
    private int atk, def, hp, max;
    public Enemy(String enemy) {
        if (enemy.equalsIgnoreCase("gangster")) {
            this.max = 30;
            this.atk = 20;
            this.def = 10;
        }
        else if (enemy.equalsIgnoreCase("bulky")) {
            this.max = 20;
            this.atk = 10;
            this.def = 5;
        }
        else {
            this.max = 12;
            this.atk = 6;
            this.def = 3;
        }
        this.hp = this.max;
    }
    public int getHP() {
        return this.hp;
    }
    public int getMax() {
        return this.max;
    }
    public int getAtk() {
        return this.atk;
    }
    public boolean attacked(int yourAtk) {
        this.hp -= (yourAtk - this.def);
        if (!(this.hp > 0)) {
            System.out.println("The foe fainted!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (this.hp > 0);
    }
}
