/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

import items.Item;
import items.Perma;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author guilherme
 */
public class Hero extends Fighter{
    private HashMap<String, Perma> equippedItems;
    
    public Hero(String name, int gold, int limitBagWeigth, int maxHp, int damage) {
        super(name, gold, limitBagWeigth, maxHp, damage);
        equippedItems = new HashMap<>();
    }

    public HashMap<String, Perma> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(HashMap<String, Perma> equippedItems) {
        this.equippedItems = equippedItems;
    }
    
    public void equipItem(Perma item) {
        if(getInventory().containsKey(item.getName())) {
            Perma newItem = (Perma) getInventory().remove(item.getName());
            if(equippedItems.containsKey(item.getType())) {
                unequipItem(item);
            }
            switch(item.getType()) {
                case "passive":
                    equippedItems.put("passive", newItem);
                    setLimitBagWeigth(200);
                    break;
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
    public void unequipItem(Perma item) {
        removeBuff(item);
        getInventory().put(item.getName(), equippedItems.get(item.getType()));
        equippedItems.remove(item.getType());
        
    }
    private void activeBuff(Perma item) {
        setDamage(getDamage() + item.getAttackBonus());
        setMaxHp(getMaxHp() + item.getHpBonus());
        setStealLife(getStealLife() + item.getStealLifeBonus());
        setDefense(getDefense() + item.getDefenseBonus());
    }
    private void removeBuff(Perma item) {
        setDamage(getDamage() - item.getAttackBonus());
        setMaxHp(getMaxHp() - item.getHpBonus());
        setStealLife(getStealLife() - item.getStealLifeBonus());
        setDefense(getDefense() - item.getDefenseBonus());
    }
}
