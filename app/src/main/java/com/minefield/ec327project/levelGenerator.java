package com.minefield.ec327project;

import java.util.Random;

public class levelGenerator{



    public static int[][] levelGeneratorFn(double directness, double mineChance, int minDist, long seed){

        //Generates new rand based on seed that was passed
        Random rand = new Random(seed);


        //Positions for start and end of board
        int startPosX = 0;
        int startPosY = 0;
        int endPosX = 0;
        int endPosY = 0;


        //We have min distance that we want user to traverse, if they aren't a certain distance away, we try to generate again.
        while (Math.abs(startPosX - endPosX) + Math.abs(startPosY - endPosY) < minDist){
            startPosX = rand.nextInt(9);
            startPosY = rand.nextInt(9);
            endPosX   = rand.nextInt(9);
            endPosY   = rand.nextInt(9);
        }


        //Initializes a new grid, and randomly generates mines to add more randomness
        int[][] grid = new int[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(rand.nextDouble() < mineChance)
                    grid[i][j] = 0;
                else
                    grid[i][j] = 1;
            }
        }


        //sets start and end positions
        grid[startPosX][startPosY] = 3;
        grid[endPosX][endPosY]     = 2;

        //updates current position to start pos
        int curPosX = startPosX;
        int curPosY = startPosY;


        //next position is current position, then we will update it
        int nextPosX = curPosX;
        int nextPosY = curPosY;


        int priorCorrectDir = 1; // uses 2 as false. Increases likeliehood of multimisteps in a row so paths aren't just wider

        //while we aren't one block away from the end position in any direction. We check if we are greater than or less than,
        //check if we want to update vertical or horizontal positioin, and then try to move towards the end goal in a somewhat
        //random and weavy path.
        while (Math.abs(curPosX - endPosX) + Math.abs(curPosY - endPosY) > 1){
            int vertPriority = rand.nextInt(2); //vertical is 1, horizontal is 0
            int correctDir = 1;
            boolean moved = false;

            //checks if we should keep going in same direction
            if( priorCorrectDir * rand.nextDouble() > directness){
                correctDir = 0;
                priorCorrectDir = 2;
            }
            else
                priorCorrectDir = 1;

            //if we haven't move yet, and we want to move in vertical position, and we aren't at winner square.
            if(moved == false && vertPriority == 1 && curPosY != endPosY){
                if (curPosY < endPosY){
                    //we will incrwase y direction
                    if(correctDir == 1)
                        nextPosY++;

                        //we will decrease y directiin
                    else if (curPosY > 0)
                        nextPosY--;
                    moved = true;
                }
                if (curPosY > endPosY){

                    //we will decrease y direction
                    if(correctDir == 1)
                        nextPosY--;

                        //We will increase Y directiion
                    else if (curPosY < 8)
                        nextPosY++;
                    moved = true;
                }
            }

            //If we want to move at horizontal direction and haven't found winner square yet.
            else if(moved == false && vertPriority == 0 && curPosX != endPosX){
                if (curPosX < endPosX){

                    //We will move increased x direction
                    if(correctDir == 1)
                        nextPosX++;

                        //We will decrease x direction
                    else if (curPosX > 0)
                        nextPosX--;
                    moved = true;
                }
                if (curPosX > endPosX){
                    //decrease x direction
                    if(correctDir == 1)
                        nextPosX--;

                        //increase x direction
                    else if (curPosX < 8)
                        nextPosX++;
                    moved = true;
                }
            }

            //updates positions
            curPosX = nextPosX;
            curPosY = nextPosY;
            if(grid[curPosX][curPosY] == 0){
                grid[curPosX][curPosY] = 1;
            }
        }

        return grid;
    }
}