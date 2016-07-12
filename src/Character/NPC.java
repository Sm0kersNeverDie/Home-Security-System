/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Character;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class NPC {
    private final String name;
    private boolean story = false;
    public NPC(String name) {
        this.name = name;
    }
    public String name(){
        return this.name;
    }
    public void talk(Player p, Scanner reader) {
        System.out.println(this.name+": \"Hello "+p.name()+", you look tired. Here, have some energy drink.\"");
        System.out.println("You drank the energy drink and regained your health.");
        p.save();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.name+": \"Do you need some help?\""); String command = reader.nextLine().trim();
        while (!(command.equalsIgnoreCase("yes")||command.equalsIgnoreCase("no"))) {
            System.out.println(this.name+": \"I don't understand what you mean. Let me ask again, do you need help with your current task?\"");
            command = reader.nextLine().trim();
        }
        int w1=0, w2=0;
        if (command.equalsIgnoreCase("no")) System.out.println(this.name + ": \"Okay. But if you need something, just ask me okay?\"");
        else {
            if (!story) {
                story = true;
                System.out.println("You told "+this.name+" what happened.");
                System.out.print(this.name+": \"Oh my, that sounds like a problem.");
                if (!p.getLocation().event().hasOtherEvent()) System.out.print(" Perhaps you should go find the teachers.");
                System.out.println("\"");
            }
            if (!p.getLocation().event().sakariStart())
                System.out.println(this.name+": \"I think you should go ask Sakari. I think he's in room B113.\"");
            else if (!p.getLocation().event().sakariFinish())
                System.out.println(this.name+": \"I think I saw someone's key left in our classroom. You should go check that out.\"");
            else if (!p.getLocation().event().timoStart())
                System.out.println(this.name+": \"I think you should go see Timo. I think he's in room A0121.\"");
            else if (!p.getLocation().event().timoFinish()) {
                System.out.println(this.name+": \"Group A's classroom is room A1149.\"");
                System.out.println(this.name+": \"If you want to clean that room, I think you should go to room A1102 to get the broom.\"");
            }
            else if (!p.getLocation().event().marjoStart()) 
                System.out.println(this.name+": \"I think you should go ask Marjo-Riitta. I think she's in the library.\"");
            else if (!p.getLocation().event().marjoFinish())
                System.out.println(this.name+": \"I think you should read some books in the library.\"");
            else if (!p.getLocation().event().ullaStart())
                System.out.println(this.name+": \"I think you should go ask Ulla Paatola. I think she's in room A2117.\"");
            else if (!p.getLocation().event().ullaFinish())
                if (!p.getLuck()&&(w1++<2)) System.out.println(this.name+": \"Ulla's puzzle is a little tricky. Each time you go to a room, every season changes to the next.\nSo when you go to a room and go back to the previous, two seasons have passed.\"");
                else System.out.println(this.name+": \"You can use the command HINT instead of directions to see the solution.\"");
            else if (!p.getLocation().event().marianneStart())
                System.out.println(this.name+": \"I think you should go ask Marianne. I think she's in room A1130.\"");
            else if (!p.getLocation().event().marianneFinish())
                if (!p.getLuck()&&(w2++<2)) System.out.println(this.name+": \"Does that game sound like Ulla's game?\"");
                else System.out.println(this.name+": \"You can use the command HINT instead of directions to see the solution.\"");
            else System.out.println(this.name+": \"I think you've completed all the tasks. Now let's go help Ulla!\"");
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
