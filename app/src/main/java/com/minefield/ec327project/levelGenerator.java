package com.minefield.ec327project;

import java.util.Random;

public class levelGenerator{

    /*
    public static void main(String []args){

        // modifiable parameters, these are my recommended
        double directness = .66; // 0->1, 1 for direct path. I don't recommend below .5
        double mineChance = .9; // 0->1 chance any random non-path space happens to be a bomb
        int minDist = 7; // 2->16 (16 forces corner to corner)

        // so you can render the same stage any number of times like minecraft worlds
        long seed = 1;

        int[][] grid = levelGeneratorFn(directness,mineChance,minDist,seed);


    }
*/


    public static int[][] levelGeneratorFn(double directness, double mineChance, int minDist, long seed){

        Random rand = new Random(seed);

        int startPosX = 0;
        int startPosY = 0;
        int endPosX = 0;
        int endPosY = 0;

        while (Math.abs(startPosX - endPosX) + Math.abs(startPosY - endPosY) < minDist){
            startPosX = rand.nextInt(9);
            startPosY = rand.nextInt(9);
            endPosX   = rand.nextInt(9);
            endPosY   = rand.nextInt(9);
        }

        int[][] grid = new int[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(rand.nextDouble() < mineChance)
                    grid[i][j] = 0;
                else
                    grid[i][j] = 1;
            }
        }

        grid[startPosX][startPosY] = 3;
        grid[endPosX][endPosY]     = 2;


        int curPosX = startPosX;
        int curPosY = startPosY;

        int nextPosX = curPosX;
        int nextPosY = curPosY;
        int priorCorrectDir = 1; // uses 2 as false. Increases likeliehood of multimisteps in a row so paths aren't just wider
        while (Math.abs(curPosX - endPosX) + Math.abs(curPosY - endPosY) > 1){
            int vertPriority = rand.nextInt(2); //vertical is 1, horizontal is 0
            int correctDir = 1;
            boolean moved = false;
            if( priorCorrectDir * rand.nextDouble() > directness){
                correctDir = 0;
                priorCorrectDir = 2;
            }
            else
                priorCorrectDir = 1;

            if(moved == false && vertPriority == 1 && curPosY != endPosY){
                if (curPosY < endPosY){
                    if(correctDir == 1)
                        nextPosY++;
                    else if (curPosY > 0)
                        nextPosY--;
                    moved = true;
                }
                if (curPosY > endPosY){
                    if(correctDir == 1)
                        nextPosY--;
                    else if (curPosY < 8)
                        nextPosY++;
                    moved = true;
                }
            }
            else if(moved == false && vertPriority == 0 && curPosX != endPosX){
                if (curPosX < endPosX){
                    if(correctDir == 1)
                        nextPosX++;
                    else if (curPosX > 0)
                        nextPosX--;
                    moved = true;
                }
                if (curPosX > endPosX){
                    if(correctDir == 1)
                        nextPosX--;
                    else if (curPosX < 8)
                        nextPosX++;
                    moved = true;
                }
            }
            curPosX = nextPosX;
            curPosY = nextPosY;
            if(grid[curPosX][curPosY] == 0){
                grid[curPosX][curPosY] = 1;
            }
        }

        return grid;
    }
}