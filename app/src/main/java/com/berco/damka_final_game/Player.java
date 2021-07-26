package com.berco.damka_final_game;

public class Player{
    private String name;
    private String Email;
    private String gender;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    @Override
    public String toString() {
        return  name  +
                ", " + Email +
                ", " + gender;
    }

    public Player(String name, String email, String gender) {
        this.name = name;
        Email = email;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o){
        //to players are equal if they have the same full name and same email address
        Player p = null;
        if(o instanceof Player)
            p = (Player) o;
        if(p.getEmail().equals(this.getEmail()) && p.getName().equals(this.getName()))
            return true;
        else
            return false;
    }
}
