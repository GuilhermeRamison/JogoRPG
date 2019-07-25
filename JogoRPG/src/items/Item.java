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
public class Item {
    private String name;
    private String type;
    private String rarity;
    private int quantity;
    private int weigth;
    private int price;
    private int healBonus;

    public Item(String name, String type, String rarity, int quantity, int weigth, int price, int healBonus) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.quantity = quantity;
        this.weigth = weigth;
        this.price = price;
        this.healBonus = healBonus;
    }
    public String getName() {
          return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getWeigth() {
        return weigth;
    }
    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getHealBonus() {
        return healBonus;
    }
    public void setHealBonus(int healBonus) {
        this.healBonus = healBonus;
    }
    public int incItem(Item item) {
        item.quantity++;
        return quantity;
    }
    public int decItem(Item item) {
        item.quantity--;
        return quantity;
    } 
}
