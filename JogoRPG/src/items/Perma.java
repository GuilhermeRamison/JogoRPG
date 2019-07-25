/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author guilherme
 */
public class Perma extends Item {
    private int attackBonus;
    private int hpBonus;
    private int stealLifeBonus;
    private int defenseBonus;
    private int limitBagWeigthBonus;
    private int durability;
    
    public Perma(String name, String type, String rarity, int quantity, int weigth, int price, int healBonus, int attackBonus, int hpBonus, int stealLifeBonus, int defenseBonus, int limitBagWeigthBonus) {
        super(name, type, rarity, quantity, weigth, price, healBonus);
        this.attackBonus = attackBonus;
        this.hpBonus = hpBonus;
        this.stealLifeBonus = stealLifeBonus;
        this.defenseBonus = defenseBonus;
        this.limitBagWeigthBonus = limitBagWeigthBonus;
        this.durability = 100;
    }
    public int getAttackBonus() {
        return attackBonus;
    }
    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }
    public int getHpBonus() {
        return hpBonus;
    }
    public void setHpBonus(int hpBonus) {
        this.hpBonus = hpBonus;
    }
    public int getStealLifeBonus() {
        return stealLifeBonus;
    }
    public void setStealLifeBonus(int stealLifeBonus) {
        this.stealLifeBonus = stealLifeBonus;
    }
    public int getDefenseBonus() {
        return defenseBonus;
    }
    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }
    public int getLimitBagWeigthBonus() {
        return limitBagWeigthBonus;
    }
    public void setLimitBagWeigthBonus(int limitBagWeigthBonus) {
        this.limitBagWeigthBonus = limitBagWeigthBonus;
    }
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
}
