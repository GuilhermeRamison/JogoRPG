/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

import items.Item;
import items.Perma;
import java.util.HashMap;

/**
 *
 * @author guilherme
 */
public class Boss extends Villain {
    private HashMap<String, Item> equippedItems;

    public Boss(String name, int gold, int limitBagWeigth, int maxHp, int damage) {
        super(name, gold, limitBagWeigth, maxHp, damage);
        int dice = luck();
        if(dice <= 1) {
            Perma Espada = new Perma("Espada", "weapon", "commom", 1,5,100,5,0,0,0,0,100);
            equipItem(Espada);
        }
        else if(dice == 2) {
            Perma Colar = new Perma("Colar", "necklace", "commom", 1, 2, 15, 0, 0, 2, 0, 0, 1);
            equipItem(Colar);
        }
        else if(dice == 3) {
            Perma Malha = new Perma("Malha", "armor", "commom", 1, 15, 200, 0, 0, 7, 0,0, 3);
            equipItem(Malha);
        }
         else if(dice == 4) {
            Perma Anel = new Perma("Anel", "ring", "commom", 1, 1, 10, 0, 2, 0, 0,0, 1);
            equipItem(Anel);
        }
        else if(dice == 5 || dice == 6) {
            Perma Escudo = new Perma("Escudo", "shield", "commom", 1, 10, 100, 0, 0, 2, 0,0, 5);
            equipItem(Escudo);
        } 
        else {
            Perma Espada = new Perma("Espada", "weapon", "commom", 1,5,100,5,0,0,0,0,100);
            Perma Escudo = new Perma("Escudo", "shield", "commom", 1, 10, 100, 0, 0, 2, 0,0, 5);
            equipItem(Espada);
            equipItem(Escudo);
        }
        Item Potion = new Item("Potion","heal", "rare", 5, 2,10,300);
        getInventory().put("Potion", Potion);
    }
    @Override
    public void incHp(Item healer) {
        if(getHp() < getMaxHp()) {
            if(!"Heal".equals(healer.getType())) {
                System.out.println("Você não pode se curar com esse item");
            } else {
                setHp(getHp() + healer.getHealBonus());
                healer.decItem(healer);
            }
        }       
    }
    public void equipItem(Perma item) {
        if(getInventory().containsKey(item.getName())) {
            Item newItem = getInventory().remove(item.getName());
            switch(item.getType()) {
                case "passive":
                    equippedItems.put("passive", newItem);
                    activeBuff(item);
                case "weapon":
                    equippedItems.put("weapon", newItem);
                    activeBuff(item);
                    break;
                case "necklace":
                    equippedItems.put("necklace", newItem);
                    activeBuff(item);
                    break;
                case "armor":
                    equippedItems.put("armor", newItem);
                    activeBuff(item);
                    break;
                case "ring":
                    equippedItems.put("ring", newItem);
                    activeBuff(item);
                    break;
                case "shield":
                    equippedItems.put("shield", newItem);
                    activeBuff(item);
                    break;
            }
        } else {
            System.out.println("Você não possui esse item!");
        }
    }
    public void unequipItems() {
        for(String name : equippedItems.keySet()) {
            Item item = equippedItems.remove(equippedItems.get(name));
            getInventory().put(item.getName(), item);
        }
    }
    private void activeBuff(Perma item) {
        setDamage(getDamage() + item.getAttackBonus());
        setMaxHp(getMaxHp() + item.getHpBonus());
        setStealLife(getStealLife() + item.getStealLifeBonus());
        setDefense(getDefense() + item.getDefenseBonus());
        setLimitBagWeigth(getLimitBagWeigth() + item.getLimitBagWeigthBonus());
    }
}