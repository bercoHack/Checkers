package com.berco.damka_final_game;

import static com.berco.damka_final_game.Model.SIZE;

public class Queen extends Pon implements Cloneable{

    public Queen(char c, boolean e){
        super(c, e);
    }

    public int checkQueenDir(int currPlace, int nextPlace){
        //method that is helping to determinate the direction of queens move since it has 4 options
        int nextRow = nextPlace/SIZE;
        int nextCol = nextPlace%SIZE;
        int currRow = currPlace/SIZE;
        int currCol = currPlace%SIZE;

        for(int i=0; i<SIZE; i++){
            if(currCol+i == nextCol && nextRow == currRow+i)
                return 1;
            if(currCol-i == nextCol && nextRow == currRow+i)
                return 2;
            if(currCol+i == nextCol && nextRow == currRow-i)
                return 3;
            if(currCol-i == nextCol && nextRow == currRow-i)
                return 4;
        }
        return 0;
    }

    @Override
    public boolean move(Pon[][] board, int nextPlace, int currPlace, char player){
        int currRow = currPlace / SIZE;
        int currCol = currPlace % SIZE;
        int nextRow = nextPlace / SIZE;
        int nextCol = nextPlace % SIZE;

        //checks for each dir if move is possible - has no to pon together in the way or next place is not empty
        //and if so eats every thing that on the way to new place
        int temp = checkQueenDir(currPlace, nextPlace);
        switch(temp) {
            case 0:
                return false;
            case 1:
                for (int i = 1; (i <= nextRow -currRow) && (i <= nextCol - currCol); i++) {
                    if (board[currRow + i][currCol + i].getColor() == this.getColor() || (!(board[currRow + i][currCol + i].isEmpty()) && !(board[currRow + i + 1][currCol + i + 1].isEmpty()))) {
                        return false;
                    }
                    if ((nextCol == currCol + i) && (nextRow == currRow + i)) {
                        Pon p = board[nextRow][nextCol];
                        board[nextRow][nextCol] = this;
                        board[currRow][currCol] = p;
                        return true;
                    }
                    if (!board[currRow + i][currCol + i].isEmpty() && board[currRow + i + 1][currCol + i + 1].isEmpty()) {
                        board[currRow + i][currCol + i].setEmpty(true);
                        board[currRow + i][currCol +i].setColor('0');
                    }
                }
                break;
            case 2:
                for (int i = 1; (i <= nextRow - currRow) && (i <= currCol - nextCol); i++) {
                    if (board[currRow + i][currCol - i].getColor() == this.getColor() || (!(board[currRow + i][currCol - i].isEmpty()) && !(board[currRow + i + 1][currCol - i - 1].isEmpty()))) {
                        return false;
                    }
                    if ((nextCol == currCol - i) && (nextRow == currRow + i)) {
                        Pon p = board[nextRow][nextCol];
                        board[nextRow][nextCol] = this;
                        board[currRow][currCol] = p;
                        return true;
                    }
                    if (!board[currRow + i][currCol - i].isEmpty() && board[currRow + i + 1][currCol - i - 1].isEmpty()) {
                        board[currRow + i][currCol - i].setEmpty(true);
                        board[currRow + i][currCol - i].setColor('0');
                    }
                }
                break;
            case 3:
                for (int i = 1; (i <= currRow  - nextRow) && (i <= nextCol - currCol); i++) {
                    if (board[currRow - i][currCol + i].getColor() == this.getColor() || (!(board[currRow - i][currCol + i].isEmpty()) && !(board[currRow - i - 1][currCol + i + 1].isEmpty()))) {
                        return false;
                    }
                    if ((nextCol == currCol + i) && (nextRow == currRow - i)) {
                        Pon p = board[nextRow][nextCol];
                        board[nextRow][nextCol] = this;
                        board[currRow][currCol] = p;
                        return true;
                    }
                    if (!board[currRow - i][currCol + i].isEmpty() && board[currRow - i - 1][currCol + i + 1].isEmpty()) {
                        board[currRow - i][currCol + i].setEmpty(true);
                        board[currRow - i][currCol + i].setColor('0');
                    }
                }
                break;
            case 4:
                for (int i = 1; (i <= currRow - nextRow) && (i <= currCol - nextCol); i++) {
                    if (board[currRow - i][currCol - i].getColor() == this.getColor() || (!(board[currRow - i][currCol - i].isEmpty()) && !(board[currRow - i - 1][currCol - i - 1].isEmpty()))) {
                        return false;
                    }
                    if ((nextCol == currCol - i) && (nextRow == currRow - i)) {
                        Pon p = board[nextRow][nextCol];
                        board[nextRow][nextCol] = this;
                        board[currRow][currCol] = p;
                        return true;
                    }
                    if (!board[currRow - i][currCol - i].isEmpty() && board[currRow - i - 1][currCol - i - 1].isEmpty()) {
                        board[currRow - i][currCol - i].setEmpty(true);
                        board[currRow - i][currCol - i].setColor('0');
                    }
                }
                break;

        }
        return false;
    }


}
