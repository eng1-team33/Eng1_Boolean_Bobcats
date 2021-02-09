package com.team5.game.tools;

public class Difficulty {

    //0 = easy, 1 = normal, 2 = hard
    public static int difficulty = 1;

    public static float changeCooldown = 18f;
    public static int noNPCs = 72;
    public static int noInfiltrators = 6;

    public static void setDifficulty(int difficulty){


        Difficulty.difficulty = difficulty;
        switch(difficulty){
            case 0:
                Difficulty.changeCooldown = 24f;
                Difficulty.noNPCs = 60;
                Difficulty.noInfiltrators = 4;
                break;
            case 1:
                Difficulty.changeCooldown = 18f;
                Difficulty.noNPCs = 72;
                Difficulty.noInfiltrators = 6;
                break;
            case 2:
                Difficulty.changeCooldown = 12f;
                Difficulty.noNPCs = 80;
                Difficulty.noInfiltrators = 8;
                break;
        }
    }

    public static float getChangeCooldown() {
        return changeCooldown;
    }

    public static int getNoNPCs() {
        return noNPCs;
    }

    public static int getNoInfiltrators() {
        return noInfiltrators;
    }

}
