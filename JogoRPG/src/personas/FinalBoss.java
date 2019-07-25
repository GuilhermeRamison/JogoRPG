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
public class FinalBoss extends Boss {
    
    private HashMap<String, Item> equippedItems;
    
    public FinalBoss(String name, int gold, int limitBagWeigth, int maxHp, int damage) {
        super(name, gold, limitBagWeigth, maxHp, damage);
        Item Espada = new Perma("Espada", "weapon", "commom", 1,5,100,5,0,0,0,0,0);
        Item Escudo = new Perma("Escudo", "shield", "commom", 1, 10, 100, 0, 0, 2, 0, 5,0);
        Item Malha = new Perma("Malha", "armor", "commom", 1, 15, 200, 0, 0, 7, 0,0, 3);
        Item Amethyst = new Perma("Amethyst", "ring", "rare", 1, 1, 500, 0, 4, 5, 1, 2,0);
        Item Tiamat = new Perma("Tiamat", "necklace", "legendary", 1, 1, 9999, 0, 5, 5, 5, 5,0);
        equipItem((Perma) Espada);
        equipItem((Perma) Escudo);
        equipItem((Perma) Malha);
        equipItem((Perma) Amethyst);
        equipItem((Perma) Tiamat);
        Item Potion = new Item("Potion","heal", "rare", 5, 2,10,300);
        getInventory().put("Potion", Potion);
    }
    @Override
    public void incHp(Item healer) {
        if(getHp() < getMaxHp()) {
            if(!"Heal".equals(healer.getType())) {
                System.out.println("Você não pode se curar com esse item");
            } else {
                setHp(getHp() + healer.getHealBonus() * 2);
                healer.decItem(healer);
            }
        }       
    }
    @Override
    public String stab(Fighter opponent) {
        int dice = luck();
        if(dice == 7) {
            for(int i = 0;i < getDamage();i++) {
                opponent.decHp();
                opponent.decHp();
                opponent.decHp();
                setHp(getHp() + 4);
            }
            System.out.println(dice);
            System.out.println("Dano - " + (3 * getDamage()));
            opponent.printStatus();
        }
        else if(dice > 0) {
            for(int i = 0;i < getDamage();i++) {
                opponent.decHp();
                setHp(getHp() + 2);
            }
            System.out.println(dice);
            System.out.println("Dano - " + getDamage());
            opponent.printStatus();
        }
        else if(dice == 0) {
            System.out.println(dice);
            System.out.println("MISS");
        }
        if(opponent.getHp() <= 0) {
            System.out.println(getName() + " derrotou " + opponent.getName());
            return opponent.getName();
        }
        return "Alive";
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
