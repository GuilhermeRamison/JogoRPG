package jogorpg.world_of_zuul;

import items.Item;
import java.util.Set;
import java.util.HashMap;
import personas.Folk;
import personas.Merchant;
import personas.Villain;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Room 
{
    private String description;
    private boolean battle;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Villain> villainsInTheRoom;
    private HashMap<String, Folk> folksInTheRoom;
    private HashMap<String, Item> itemsInTheRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        villainsInTheRoom = new HashMap<>();
        folksInTheRoom = new HashMap<>();
        itemsInTheRoom = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return description + ".\n" + printVillainsInTheRoom() + printFolksInTheRoom() +printItemsInTheRoom() + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "# Saídas:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    public boolean haveBattle() {
        if(villainsInTheRoom.isEmpty()) {
            battle = false;
        } else {
            battle = true;
        }
        return battle;
    }
    public Battle putRoomInBattle(Room back) {
        int i = 0;
        Villain[] enemys = new Villain[2];
        for(String key : villainsInTheRoom.keySet()) {
            enemys[i] = villainsInTheRoom.get(key);
            i++;
        }
        Battle fight = new Battle(enemys[0], enemys[1], back);
        return fight;
    }
    public void setVillainInTheRoom(Villain enemy) {
        String key = enemy.getName();
        villainsInTheRoom.put(key, enemy);
    }
    public Villain getVillainInTheRoom(String target) {
        return villainsInTheRoom.get(target);
    }
    public String printVillainsInTheRoom() {
        String nameVillain = new String();
        nameVillain = "# Inimigos na sala: \n";
        for(String key : villainsInTheRoom.keySet()) {
            nameVillain += "\t" +villainsInTheRoom.get(key).getName() + "\n";
        }
        return nameVillain;
    }
    public void setFolkInTheRoom(Folk farmer) {
        String key = farmer.getName();
        folksInTheRoom.put(key, farmer);
    }
    public Folk getFolkInTheRoom(String farmer) {
        return folksInTheRoom.get(farmer);
    }
    public Merchant getFolkInTheRoom(String farmer, int i) {
        return (Merchant) folksInTheRoom.get(farmer);
    }
    public String printFolksInTheRoom() {
        String nameFolk = new String();
        nameFolk = "# Pessoas na sala: \n";
        for(String key : folksInTheRoom.keySet()) {
            nameFolk += "\t" + folksInTheRoom.get(key).getName() + "\n";
        }
        return nameFolk;
    }
    public Villain removeVillain(Villain enemy) {
        int totalItems = enemy.getInventoryTotalItems();
        for(int i = 0;i < totalItems;i++) {
            
        }
        for(String key : enemy.getInventory().keySet()) {
            setItemInTheRoom(enemy.dropItem(enemy.getInventory().get(key)));
        }
        villainsInTheRoom.remove(enemy.getName());
        return enemy;
    }
    
     public void setItemInTheRoom(Item item) {
        String key = item.getName();
        if(itemsInTheRoom.containsValue(item)){
            itemsInTheRoom.get(key).incItem(item);
        } else {
            itemsInTheRoom.put(key, item);
        }
    }
    public Item getItemInTheRoom(String loot) {
        return itemsInTheRoom.get(loot);
    }
    public String printItemsInTheRoom() {
        String nameItem = new String();
        nameItem = "# Itens na sala:\n";
        for(String key : itemsInTheRoom.keySet()) {
            nameItem += "\t" + itemsInTheRoom.get(key).getName() + "(" + itemsInTheRoom.get(key).getType() + ")(" + itemsInTheRoom.get(key).getRarity() + ")" + "\n";
        }
        return nameItem;
    }
    public Item removeItem(Item item) {
        itemsInTheRoom.remove(item.getName());
        return item;
    }
}