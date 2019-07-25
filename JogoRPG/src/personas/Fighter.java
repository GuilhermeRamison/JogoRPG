/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

import items.Item;
import items.Perma;
import java.util.HashMap;
import java.util.Random;
import static jogorpg.world_of_zuul.Game.player;

/**
 *
 * @author guilherme
 */
public abstract class  Fighter extends Persona{
    private int limitBagWeigth;
    private int bagWeigth;
    private HashMap<String, Item> inventory;
    private int maxHp;
    private int hp;
    private boolean bleeding;
    private int damage;
    private int stealLife;
    private int defense;

    public Fighter(String name, int gold, int limitBagWeigth, int maxHp, int damage) {
        super(name, gold);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.stealLife = 0;
        this.bleeding = false;
        this.limitBagWeigth = limitBagWeigth;
        defense = 0;
        inventory = new HashMap<>();
        upGoldWeigth();
        
    }
    
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getStealLife() {
        return stealLife;
    }
    public void setStealLife(int stealLife) {
        this.stealLife = stealLife;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public void incHp(Item healer) {
        if(hp < maxHp) {
            if(!"heal".equals(healer.getType())) {
                System.out.println("Você não pode se curar com esse item");
            } else {
                hp += healer.getHealBonus();
                if(healer.decItem(healer) <= 0) {
                    inventory.remove(healer.getName());
                }
                if(hp > maxHp) {
                    hp = maxHp;
                }
            }
        } else {
            System.out.println("Seu Hp já está no máximo!");
        }      
    }
    public void decHp() {
        hp--;
    }
    public boolean isBleeding() {
        if(hp < (maxHp / 2)) {
            this.bleeding = true;
            return true;
        } else {
            this.bleeding = false;
            return false;
        }
    }
    public void printStatus() {
        System.out.println("####################");
        System.out.println("# Nome: " + getName());
        System.out.println("# HP: " + getHp() + "/" + getMaxHp() +"\n"); 
        System.out.println("####################");
    }
    public int luck() {
        Random generator = new Random();
        return generator.nextInt(8);
    }
   
    public String stab(Fighter opponent) {
        int dice = luck();
        int dam;
        if(opponent != player) {
            if(player.getEquippedItems().get("weapon") != null) {
                if(player.getEquippedItems().get("weapon").getDurability() <= 15) {
                    dam = ((damage - (player.getEquippedItems().get("weapon").getAttackBonus()/2)) / 100) * (100 - opponent.getDefense());
                } else {
                    dam = (damage / 100) * (100 - opponent.getDefense());
                }
            } else {
                dam = (damage / 100) * (100 - opponent.getDefense());
            }
        } else {
            dam = (damage / 100) * (100 - opponent.getDefense());
        }
        System.out.println(">>>>" + getName() + " ataca " + opponent.getName() + "<<<<");
        if(dice == 7) {
            for(int i = 0;i < dam;i++) {
                opponent.decHp();
                opponent.decHp();
            }
            this.setHp(getHp() + getStealLife());
            System.out.println("Rolagem do dado: " + dice);
            System.out.println("Dano - " + (2 * dam));
            opponent.printStatus();
            if(opponent.getName().equals(player.getName())) {
                if(player.getEquippedItems().get("shield") != null) {
                    player.getEquippedItems().get("shield").setDurability(player.getEquippedItems().get("shield").getDurability() - 2);
                }
                 if(player.getEquippedItems().get("armor") != null) {
                    player.getEquippedItems().get("armor").setDurability(player.getEquippedItems().get("armor").getDurability() - 2);
                }
            } else {
                if(player.getEquippedItems().get("weapon") != null) {
                    player.getEquippedItems().get("weapon").setDurability(player.getEquippedItems().get("weapon").getDurability() - 5);
                }
            }
        }
        else if(dice > 0) {
            for(int i = 0;i < dam;i++) {
                opponent.decHp();
            }
            this.setHp(getHp() + getStealLife());
            System.out.println("Rolagem do dado: " + dice);
            System.out.println("Dano - " + dam);
            opponent.printStatus();
            if(opponent.getName().equals(player.getName())) {
                if(player.getEquippedItems().get("shield") != null) {
                    player.getEquippedItems().get("shield").setDurability(player.getEquippedItems().get("shield").getDurability() - 2);
                }
                 if(player.getEquippedItems().get("armor") != null) {
                    player.getEquippedItems().get("armor").setDurability(player.getEquippedItems().get("armor").getDurability() - 2);
                }
            } else {
                if(player.getEquippedItems().get("weapon") != null) {
                    player.getEquippedItems().get("weapon").setDurability(player.getEquippedItems().get("weapon").getDurability() - 5);
                }
            }
        }
        else if(dice == 0) {
            System.out.println("Rolagem do dado: " + dice);
            System.out.println("MISS");
        }
        if(opponent.getHp() <= 0) {
            System.out.println(getName() + " derrotou " + opponent.getName());
            return opponent.getName();
        }
        return "Alive";
    }
    public void upGoldWeigth() {
        int goldWeigth = 0;
        if(getGold() >= 1000) {
            goldWeigth = (getGold() / 1000);
        }
        setBagWeigth(getBagWeigth() + goldWeigth);
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
    public int getInventoryTotalItems() {
        return inventory.size();
    }
    public Item getItemInTheInventory(String item) {
        return inventory.get(item);
    }
    public String printIventory() {
        String nameItems = new String();
        nameItems = "Inventário:\t" + getGold() + " gold" +  "\tPeso de carga: " + getBagWeigth() + "/" + getLimitBagWeigth() + "\n";
        for(String key : inventory.keySet()) {
            if(key.equals("Fruta") || key.equals("Poção")) {
                nameItems += "\t" + inventory.get(key).getQuantity() + "x " + inventory.get(key).getName() + "(" +
                                    inventory.get(key).getType() +")(" + inventory.get(key).getRarity() + ")(" + 
                                    inventory.get(key).getPrice()+ " gold)" + "\n";
            } else {
                Perma p = (Perma) inventory.get(key);
                nameItems += "\t" + p.getQuantity() + "x " + p.getName() + "(" +
                                    p.getType() +")(" + p.getRarity() + ")(" + 
                                    p.getPrice()+ " gold)" + 
                                    p.getDurability() + "%\n";
            }
        }
        return nameItems;
    }
    public boolean pickItem(Item item) {
        if(item.getWeigth() + bagWeigth <= limitBagWeigth) {
            if(!inventory.containsValue(item)) {
                if(item.getQuantity() <= 0)
                    item.setQuantity(1);
                inventory.put(item.getName(), item);
            } else {
                inventory.get(item.getName()).incItem(item);
            }
            setBagWeigth(getBagWeigth() + item.getWeigth());
        } else {
            System.out.println("Limite de peso máximo atingido!");
            return false;
        }
        return true;
    }
    public Item dropItem(Item item) {
        if(inventory.get(item.getName()).getName().equals(item.getName())) {
            inventory.remove(item.getName());
            setBagWeigth(getBagWeigth() - item.getWeigth());
            return item;
        } else {
            System.out.println("Você não possui esse item!\n");
            return null;
        }
    }
    public Item dropItem(Item item, int quant) {
        if(inventory.get(item.getName()).getName().equals(item.getName())) {
            for(int i = 0;i < quant;i++) {
                item.decItem(item);
                if(item.getQuantity() <= 0) {
                    dropItem(item);
                    setBagWeigth(getBagWeigth() + item.getWeigth());
                    return null;
                }
            }
            return item;
        } else {
            System.out.println("Você não possui esse item!\n");
            return null;
        }
    }
}