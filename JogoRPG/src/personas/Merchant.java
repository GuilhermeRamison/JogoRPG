/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

import items.Item;
import items.Perma;
import java.util.HashMap;
import static jogorpg.world_of_zuul.Game.player;

/**
 *
 * @author guilherme
 */
public class Merchant extends Folk {
    private int limitBagWeigth;
    private int bagWeigth;
    private HashMap<String, Item> inventory;
    
    public Merchant(String name, int gold, int limitBagWeigth, String welcome, String talk) {
        super(name, gold, welcome, talk);
        this.limitBagWeigth = limitBagWeigth;
        inventory = new HashMap<>();
        upGoldWeigth();
    }
    public int getLimitBagWeigth() {
        return limitBagWeigth;
    }
    public void setLimitBagWeigth(int limitBagWeigth) {
        this.limitBagWeigth = limitBagWeigth;
    }
    public int getBagWeigth() {
        return bagWeigth;
    }
    public void setBagWeigth(int bagWeigth) {
        this.bagWeigth = bagWeigth;
    }
    public HashMap<String, Item> getInventory() {
        return inventory;
    }
    public void setInventory(HashMap<String, Item> inventory) {
        this.inventory = inventory;
    }
    public void pickItem(Item item) {
        if(item.getWeigth() + bagWeigth <= limitBagWeigth) {
            if(!inventory.containsValue(item)) {
                inventory.put(item.getName(), item);
            } else {
                inventory.get(item.getName()).incItem(item);
            }
        }
    }
    public Item dropItem(Item item) {
        if(inventory.get(item.getName()).getName().equals(item.getName())) {
            inventory.remove(item.getName());
            return item;
        } else {
            System.out.println("Mercador não possui esse item!\n");
            return null;
        }
    }
    public Item dropItem(Item item, int quant) {
        if(inventory.get(item.getName()).getName().equals(item.getName())) {
            for(int i = 0;i < quant;i++) {
                item.decItem(item);
                if(item.getQuantity() <= 0) {
                    dropItem(item);
                    return null;
                }
            }
            return item;
        } else {
            System.out.println("Mercador não possui esse item!\n");
            return null;
        }
    }
    public String printIventory() {
        String nameItems = new String();
        nameItems = "Loja: " + getGold() + " gold\n";
        for(String key : inventory.keySet()) {
            nameItems += "\t" + inventory.get(key).getQuantity()+ "x " + inventory.get(key).getName() + "(" +
                                inventory.get(key).getType() +")(" + inventory.get(key).getRarity() + ")(" + 
                                inventory.get(key).getPrice()+ " gold)" + "\n";
        }
        return nameItems;
    }
    public void upGoldWeigth() {
        int goldWeigth = 0;
        if(getGold() >= 1000) {
            goldWeigth = (getGold() / 1000);
        }
        setBagWeigth(getBagWeigth() + goldWeigth);
    }
    public void sellItem(Item item) {
        if(player.getGold() < item.getPrice()) {
            System.out.println("Gold insuficiente!");
            return;
        }
        if(inventory.containsValue(item)) {
            dropItem(item, 1);
            player.setGold(player.getGold() - item.getPrice());
            setGold(getGold() + item.getPrice());
            player.pickItem(item);
            player.getInventory().get(item.getName()).setQuantity(player.getInventory().get(item.getName()).getQuantity() + 1);
        } else {
            System.out.println("Erro na troca.");
        }
    }
    public void buyItem(Item item) {
        if(getGold() < item.getPrice()) {
            System.out.println("Gold insuficiente!");
            return;
        }
        if(player.getInventory().containsValue(item)) {
            player.dropItem(item, item.getQuantity());
            setGold(getGold() - item.getPrice());
            player.setGold(player.getGold() + item.getPrice());
            item.setQuantity(item.getQuantity() +1);
            pickItem(item);
        } else {
            System.out.println("Erro na troca.");
        }
    }
    public void repairItem(Perma item) {
        if(player.getGold() < 50) {
            System.out.println("Gold insuficiente!");
            return;
        }
        if(item.getDurability() < 100) {
            player.setGold(player.getGold() - 50);
            setGold(getGold() + 50);
            item.setDurability(100);
        } else {
            System.out.println("Erro ao reparar item!");
        }
    }
    @Override
    public void chat() {
        System.out.println(getWelcome());
        setWelcome(getTalk());
        System.out.println(printIventory());;
    }
}
