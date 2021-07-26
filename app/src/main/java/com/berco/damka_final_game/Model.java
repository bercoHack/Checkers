package com.berco.damka_final_game;

public class Model {
    //B equals black pon, W equals white pon
    private Pon[][] board;
    private char winner;
    public static final int SIZE = 8;
    private int steps;

    public Model() {
        board = new Pon[SIZE][SIZE];
        winner = ' ';
        steps = 0;
    }



    public void startGame() {
        //starting game means that al black pons are in one side and white in the other one
        //between them there is a 2 lines break
        winner = ' ';
        steps = 0;

        for (int i = 3; i < 5; i++) {
            for (int k = 0; k < SIZE; k++) {
                if(i%2 != k%2)
                    board[i][k] = new Pon('0', true);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k <SIZE; k++) {
                if(i%2 != k%2)
                    board[i][k] = new Pon('B', false);
            }
        }
        for (int i = 5; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                if(i%2 != k%2)
                    board[i][k] = new Pon('W', false);
            }
        }
    }

    public boolean gameOver() {
        //if there is no pons of a color the player who played it has lost
        boolean WhiteNotLost = false;
        boolean BlackNotLost = false;
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                if (i % 2 != k % 2) {
                    if (board[i][k].getColor() == 'B')
                        BlackNotLost = true;
                    if (board[i][k].getColor() == 'W')
                        WhiteNotLost = true;
                }
            }
        }
        if(WhiteNotLost && !BlackNotLost){
            winner = 'W';
            return true;
        }
        if(!WhiteNotLost && BlackNotLost){
            winner = 'B';
            return true;
        }
        return false;
    }

    public char getWinner()
    {
        return winner;
    }

    public boolean movePlayer(int nextPlace, int currPlace, char player) throws CloneNotSupportedException {
        int currRow = currPlace / SIZE;
        int currCol = currPlace % SIZE;
        Pon[][] tempBoard = new Pon[SIZE][SIZE];
        //creating a temp board in case the act wont be successful
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                if(i%2 != k%2){
                    if(board[i][k] instanceof Queen)
                        tempBoard[i][k] = (Queen) board[i][k].clone();
                    else
                        tempBoard[i][k] = (Pon) board[i][k].clone();
                }
            }
        }
        if (board[currRow][currCol].move(board, nextPlace, currPlace, player)) {
            //in case the act was successful increase the steps by 1 and return true
            steps++;
            return true;
        }
        else{
            //in case there was an issue get board to what it was before and return false
            board = tempBoard;
            return false;
        }
    }

    public int getSteps(){
        return steps;
    }

    public Pon[][] getBoard() {
        return board;
    }

}