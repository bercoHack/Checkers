package com.berco.damka_final_game;

public class Game implements Comparable{
    private long id;
    private String p1, p2;
    private String winner;
    private int steps;

    @Override
    public String toString() {
        return   p1 +
                ",\n" + p2 +
                ", winner = " + winner + '\'' +
                '\n';
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    public Game(String winner, String player1, String player2, int steps, long id) {
        this.winner = winner;
        this.p1 = player1;
        this.p2 = player2;
        this.steps = steps;
        this.id = id;
    }

    public Game(String winner, String player1, String player2, int steps) {
        this.winner = winner;
        this.p1 = player1;
        this.p2 = player2;
        this.steps = steps;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public int compareTo(Object o) {
        //the games are compared by the number of steps it took to win them
        Game g = (Game)o;
        return this.steps - g.steps;

    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
