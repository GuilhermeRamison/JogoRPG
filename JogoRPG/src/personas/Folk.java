/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personas;

import java.util.HashMap;

/**
 *
 * @author guilherme
 */
public class Folk extends Persona {
    private String welcome;
    private String talk;
    
    public Folk(String name, int gold, String welcome, String talk) {
        super(name, gold);
        this.welcome = welcome;
        this.talk = talk;
    }
    public String getWelcome() {
        return welcome;
    }
    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }
    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }
    public void chat() {
        System.out.println(welcome);
        welcome = talk;
    }
}
