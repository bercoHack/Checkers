package com.berco.damka_final_game;

public class Controller {
    private Model model;
    private char player;
    private String p1Details, p2Details;

    public void setP1(String p1) {
        this.p1Details = p1;
    }

    public void setP2(String p2) {
        this.p2Details = p2;
    }

    public Controller() {
        model = new Model();
        startGame();
    }

    public void startGame() {
        model.startGame();
        player = 'W';
    }


    public boolean gameOver(){
        return model.gameOver();
    }

    public String getWinnerString(){
        if(model.getWinner() == 'W')
            return p1Details;
        else
            return p2Details;
    }

    public boolean movePlayer(int currPlace, int nextPlace) throws CloneNotSupportedException {
        if(model.movePlayer(nextPlace-1,currPlace-1, player)){
            setPlayer();
            return true;
        }
        else
            return false;
    }

    public void setPlayer() {
        //used to replace the player with the opposite one after his turn
        if(player == 'B')
            player = 'W';
        else
            player = 'B';
    }

    public int getStepsWinner(){
        //the steps are counted for both players so divided by 2 and since those are integers
        // I am adding 1 in case it is not divided by 2
        if(model.getSteps()%2 == 0)
            return model.getSteps()/2;
        else
            return ((model.getSteps()/2) +1);
    }

    public String getP1() {
        return p1Details;
    }

    public String getP2() {
        return p2Details;
    }

    public char getQueen(char c) {
        if(c == 'W')
            return 'Q';
        else
            return 'K';
    }

    public Pon[][] getBoard() {
        return model.getBoard();
    }

}
