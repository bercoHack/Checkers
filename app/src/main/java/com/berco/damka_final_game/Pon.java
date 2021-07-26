package com.berco.damka_final_game;

import static com.berco.damka_final_game.Model.SIZE;

public class Pon implements Cloneable{
    private char color;
    private boolean empty;

    public Pon(char c, boolean e) {
        color = c;
        empty = e;
    }

    public void setColor(char c) {
        color = c;
    }

    public void setEmpty(boolean e) {
        empty = e;
    }

    public char getColor() {
        return color;
    }

    public boolean move(Pon[][] board, int nextPlace, int currPlace, char player) {
        int currRow = currPlace / SIZE;
        int currCol = currPlace % SIZE;
        int nextRow = nextPlace / SIZE;
        int nextCol = nextPlace % SIZE;

        //checks that the direction of moving is correct
        if (((player == 'B' && nextRow <= currRow) || (player == 'W' && nextRow >= currRow)))
            return false;

        //at first checks that new place is empty
        if (board[nextRow][nextCol].isEmpty() && board[currRow][currCol].getColor() == player) {
            //then that it can move to it and not includes eating
            if (((nextCol == currCol + 1) && (Math.abs(nextRow - currRow) == 1)) || ((nextCol == currCol - 1) && (Math.abs(nextRow - currRow) == 1))) {
                board[nextRow][nextCol].setColor(this.getColor());
                board[nextRow][nextCol].setEmpty(false);
                this.setEmpty(true);
                this.setColor('0');
                board[nextRow][nextCol].makeQueen(board, nextPlace, player);
                return true;
            }
            //then checks all possibilities of eating
            if (Math.abs(nextRow - currRow) == 2) {
                if ((nextCol == currCol - 2) && (player == 'W')) {
                    if ((board[nextRow + 1][nextCol + 1].getColor() != player) && !(board[nextRow + 1][nextCol + 1].isEmpty()) && !(board[nextRow + 1][nextCol + 1] instanceof Queen)) {
                        board[nextRow + 1][nextCol + 1].setEmpty(true);
                        board[nextRow + 1][nextCol + 1].setColor('0');
                        board[nextRow][nextCol].setColor(player);
                        board[nextRow][nextCol].setEmpty(false);
                        this.setColor('0');
                        this.setEmpty(true);
                        board[nextRow][nextCol].makeQueen(board, nextPlace, player);
                        return true;
                    }
                }
                if ((nextCol == currCol - 2) && (player == 'B')) {
                    if ((board[nextRow - 1][nextCol + 1].getColor() != player) && !(board[nextRow - 1][nextCol + 1].isEmpty()) && !(board[nextRow - 1][nextCol + 1] instanceof Queen)) {
                        board[nextRow - 1][nextCol + 1].setEmpty(true);
                        board[nextRow - 1][nextCol + 1].setColor('0');
                        board[nextRow][nextCol].setColor(player);
                        board[nextRow][nextCol].setEmpty(false);
                        this.setColor('0');
                        this.setEmpty(true);
                        board[nextRow][nextCol].makeQueen(board, nextPlace, player);
                        return true;
                    }
                }
                if ((nextCol == currCol + 2) && (player == 'W')) {
                    if ((board[nextRow + 1][nextCol - 1].getColor() != player) && !(board[nextRow + 1][nextCol - 1].isEmpty()) && !(board[nextRow + 1][nextCol - 1] instanceof Queen)) {
                        board[nextRow + 1][nextCol - 1].setEmpty(true);
                        board[nextRow + 1][nextCol - 1].setColor('0');
                        board[nextRow][nextCol].setColor(player);
                        board[nextRow][nextCol].setEmpty(false);
                        this.setColor('0');
                        this.setEmpty(true);
                        board[nextRow][nextCol].makeQueen(board, nextPlace, player);
                        return true;
                    }
                }
                if ((nextCol == currCol + 2) && (player == 'B')) {
                    if ((board[nextRow - 1][nextCol - 1].getColor() != player) && !(board[nextRow - 1][nextCol - 1].isEmpty()) && !(board[nextRow - 1][nextCol - 1] instanceof Queen)) {
                        board[nextRow - 1][nextCol - 1].setEmpty(true);
                        board[nextRow - 1][nextCol - 1].setColor('0');
                        board[nextRow][nextCol].setColor(player);
                        board[nextRow][nextCol].setEmpty(false);
                        this.setColor('0');
                        this.setEmpty(true);
                        board[nextRow][nextCol].makeQueen(board, nextPlace, player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return empty;
    }


    public boolean makeQueen(Pon[][] board, int nextPlace, char player) {
        //if a pon reaches the other side of the board it becomes a queen
        int nextRow = nextPlace/SIZE;
        int nextCol = nextPlace%SIZE;
        if(player == 'B' && nextRow == 7) {
            board[nextRow][nextCol] = new Queen(this.getColor(), false);
            return true;
        }
        if(player == 'W' && nextRow == 0) {
            board[nextRow][nextCol] = new Queen(this.getColor(), false);
            return true;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //implementation of clone method, since this object is a simple one a shallow clone will do the work
        return super.clone();
    }
}
