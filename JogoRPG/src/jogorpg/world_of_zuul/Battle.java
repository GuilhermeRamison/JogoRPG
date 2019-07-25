/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogorpg.world_of_zuul;

import items.Item;
import items.Perma;
import static jogorpg.world_of_zuul.Game.currentRoom;
import static jogorpg.world_of_zuul.Game.player;
import personas.Boss;
import personas.Hero;
import personas.Villain;

/**
 *
 * @author guilherme
 */
public class Battle {
    private Room back;
    private Villain enemy1, enemy2;
    private boolean inBattle;
    private boolean quit;
    private boolean validAction;
    private boolean runAway;
    private Parser parserBattle;

    public Battle(Villain enemy1, Villain enemy2, Room back) {
        //Se Battle herdar a classe Game, acontece um bug que leva o personagem a sala inicial sendo que a batalha está rolando 
        this.enemy1 = enemy1;
        this.enemy2 = enemy2;
        this.back = back;
        parserBattle = new Parser();
        inBattle = true;
        validAction = false;
        runAway = false;
        quit = false;
    }
    public Villain getEnemy1() {
        return enemy1;
    }
    public void setEnemy1(Villain enemy1) {
        this.enemy1 = enemy1;
    }
    public Villain getEnemy2() {
        return enemy2;
    }
    public void setEnemy2(Villain enemy2) {
        this.enemy2 = enemy2;
    }
    
    private void enemyTurn(Villain enemy) {
        if(enemy != null) {   
            if("Villain".equals(enemy.getClass().getSimpleName())) {
                enemy.stab(player);
            }
            else if("Boss".equals(enemy.getClass().getSimpleName())) {
                if(enemy.getItemInTheInventory("Potion") != null) {
                    if(enemy.isBleeding() && (enemy.getItemInTheInventory("Potion").getQuantity() > 0)) {
                        enemy.incHp(enemy.getItemInTheInventory("Potion"));
                    } else {
                        enemy.stab(player);
                    }
                }
            }
            else if("FinalBoss".equals(enemy.getClass().getSimpleName())) {
                if(enemy.getItemInTheInventory("Potion") != null) {
                    if(enemy.isBleeding() && (enemy.getItemInTheInventory("Potion").getQuantity() > 0)) {
                        enemy.incHp(enemy.getItemInTheInventory("Potion"));
                    } else {
                        enemy.stab(player);
                    }
                }
            }
        }
    }
    public boolean battleSimulator() {
        int turn = 1;
        System.out.println("#####  BATALHA  #####");
        while(inBattle == true) {
            System.out.println("###### Turno " + turn + " #####");
            player.printStatus();
            if(enemy1.getHp() > 0) {
                enemy1.printStatus();
            }
            if(enemy2 != null) {
                if(enemy2.getHp() > 0)
                    enemy2.printStatus();
            }
            //Player turn
            System.out.println("Seu turno: ");
            validAction = false;
            while(!validAction) {
                Command command = parserBattle.getCommand();
                processCommand(command);
                if(quit == true) {
                    inBattle = false;
                    return true; 
                }
                if(runAway == true) {
                    return false;
                }
            }
            try {Thread.sleep(1000);} catch (InterruptedException ex) {} // Wait 1 second
            //Enemy1 turn
            if(enemy1.getHp() > 0)
                enemyTurn(enemy1);
            try {Thread.sleep(1000);} catch (InterruptedException ex) {} // Wait 1 second
            //Enemy2 turn
            if(enemy2 != null)
                if(enemy2.getHp() > 0)
                    enemyTurn(enemy2);
            if(player.getHp() < 0) {
                System.out.println(">##### GAME OVER #####<");
                return true;
            }
            if(enemy1.getHp() <= 0 && enemy2 == null) {
                System.out.println("##### BATALHA ACABOU #####");
                    inBattle = false;
                    System.out.println(currentRoom.printItemsInTheRoom());
                    return false;
            }
            if(enemy1 != null && enemy2 != null) {
                if(enemy1.getHp() <= 0 && enemy2.getHp() <=0) {
                    System.out.println("##### BATALHA ACABOU #####");
                    System.out.println(currentRoom.printItemsInTheRoom());
                    inBattle = false;
                    return false;
                }
            }
            turn++;
        }
        return false;
    }
    private void resetVillains() {
        if(enemy1 != null) {
            enemy1.setHp(enemy1.getMaxHp());
        }
        if(enemy2 != null) {
            enemy2.setHp(enemy2.getMaxHp());
        }
    }
    private boolean processCommand(Command command) {

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
        }
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            quit = true;
        } 
        else if(commandWord == CommandWord.ATTACK) {
            attack(command);
            return true;
        }
        else if(commandWord == CommandWord.HEAL) {
            heal(command);
            return true;
        }
        else if(commandWord == CommandWord.TALK) {
            System.out.println("Você nã pode falar agora!");
        }
        else if(commandWord == CommandWord.PICK) {
            System.out.println("Você não pode pegar nenhum item agora!");
        }
        else if(commandWord == CommandWord.DROP) {
            System.out.println("Você não pode dropar nenhum item agora!");
        }
        else if(commandWord == CommandWord.EQUIP) {
            equip(command);
        }
        else if(commandWord == CommandWord.INVENTORY) {
            showInventory(command);
        }
        // else command not recognised.
        return false;
    }
    private void attack(Command command) {
        String resul;
        if(!currentRoom.haveBattle()) {
            System.out.println("Você não pode atacar agora!");
            return;
        }
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know who are the target...
            System.out.println("Atacar quem?");
            return;
        }
        String target = command.getSecondWord();
        Villain enemy = currentRoom.getVillainInTheRoom(target);
        if (enemy == null) {
            System.out.println("Inimigo não encontrado!");
        }
        else {
            resul = player.stab(enemy);
            if(resul.equals(enemy.getName())) {
                player.setGold(player.getGold() + enemy.getGold());
                player.upGoldWeigth();
                currentRoom.removeVillain(enemy);
            }
            if(resul.equals("Talorak")) {
                System.out.println("Você conseguiu! ");
                System.out.println("Depois disso você devolveu o colar ao barão, pegou sua recompensa");
                System.out.println("e saiu para novas aventuras...");
                quit = true;
            }
            validAction = true;
        }
    }
    private void heal(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Curar com o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item healer = player.getItemInTheInventory(itemName);
        if(healer == null) {
            System.out.println("Item não encontrado!");
            return;
        } 
        player.incHp(healer);
        validAction = true;
    }
    private void equip(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Equipar o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item item = player.getItemInTheInventory(itemName);
        if(item == null) {
            System.out.println("Você não possui esse item!");
        }else {
            player.equipItem((Perma) item);
        }
    }
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ir aonde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Esse caminho não existe!");
        }
        else if(nextRoom.equals(back)) {
            runAway = true;
            resetVillains();
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        } else {
            System.out.println("Você só pode fujir para a direção anterior!");
        }
    }
    private void showInventory(Command command) {
        System.out.println(player.printIventory());
    }
    private void printHelp() 
    {
        System.out.println("Você chegou em um vilarejo que frequentemente é atacado por Kobolds");
        System.out.println("Sua missão principal é descobrir o que atrai os Kobolds");
        System.out.println();
        System.out.println("Seus comandos são:");
        parserBattle.showCommands();
    }
}
