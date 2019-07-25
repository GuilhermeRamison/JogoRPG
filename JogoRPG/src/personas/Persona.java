/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

/**
 *
 * @author guilherme
 */
public abstract class Persona {
    private String name;
    private int gold;
    
    
    public Persona(String name, int gold){
        this.name = name;
        this.gold = gold;
        
    }
    public String getName() {
        return name;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
}