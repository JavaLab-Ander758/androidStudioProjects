package com.tictactoe.bondesjakk;

class GameBoard extends MainActivity {
    private int boardSize = 3;
    private int[][] boardArray;
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    GameBoard (int boardSize) {
        this.boardSize = boardSize;
        resetBoard();
    }

    /**
     * Plots the pressed button in the array, and checks if a player won
     * @param player Player that pressed the
     * @param x x-coordinate of button
     * @param y y-coordinate of button
     */
    public void plotNewEntryInArray(int player, int x, int y) {
        boardArray[x][y] = player;
        if (checkIfWon() != -1) {
            if (checkIfWon() == 1)
                scorePlayer1++;
            else
                scorePlayer2++;
            resetBoard();
        }
    }

    /**
     * Checks if a player has won in the game board
     * @return Player number or -1 if no result
     */
    public int checkIfWon() {
        if (checkRows() != -1)
            return checkRows();
        if (checkColumns() != -1)
            return checkColumns();
        if (checkDiagonally() != -1)
            return checkDiagonally();
        return -1;
    }

    /**
     * Scans rows in the array
     * @return -1 if no match, n(#player) for same occurrence vertically
     */
    private int checkRows() {
        for (int x = 0; x < boardSize; x++)
            if (checkIfAllValuesInArrayIsEqual(new int[]{boardArray[x][0], boardArray[x][1], boardArray[x][2]}))
                return boardArray[x][0];
        return -1;
    }

    /**
     * Scans columns in the array
     * @return -1 if no match, n(#player) for same occurrence horizontally
     */
    private int checkColumns() {
        for (int y = 0; y < boardSize; y++)
            if (checkIfAllValuesInArrayIsEqual(new int[]{boardArray[0][y], boardArray[1][y], boardArray[2][y]}))
                return boardArray[0][y];
        return -1;
    }

    /**
     * Scans diagonally in the array
     * @return -1 if no match, n(#player) for same occurrence diagonally
     */
    private int checkDiagonally() {
        if (checkIfAllValuesInArrayIsEqual(new int[]{boardArray[0][0], boardArray[1][1], boardArray[2][2]}))
            return boardArray[0][0];
        else if (checkIfAllValuesInArrayIsEqual(new int[]{boardArray[2][0], boardArray[1][1], boardArray[0][2]}))
            return boardArray[2][0];
        else
            return -1;
    }

    /**
     * Checks if all values in an integer array is equal
     * @param tiles Array to check
     * @return True if all was equal
     */
    private boolean checkIfAllValuesInArrayIsEqual(int[] tiles) {
        for (int i = 1; i < tiles.length; i++)
            if (tiles[0] != tiles[i] || tiles[i] == -1)
                return false;
        return true;
    }


    /**
     * Initializes the board Array
     */
    private void resetBoard() {
        boardArray = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                boardArray[i][j] = -1;
    }



    /* Getters */
    public int getScorePlayer1() {
        return scorePlayer1;
    }
    public int getScorePlayer2() {
        return scorePlayer2;
    }
}
