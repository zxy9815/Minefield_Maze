package com.minefield.ec327project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class LevelsScreen extends Activity implements OnClickListener {

    public static final String LEVEL_TYPE = "Level";


    private ImageButton levelONE;
    private ImageButton levelTWO;
    private ImageButton levelTHREE;
    private ImageButton levelFOUR;
    private ImageButton levelFIVE;
    private ImageButton levelSIX;
    private ImageButton levelSEVEN;
    private ImageButton levelEIGHT;
    private ImageButton levelNINE;

    private ImageButton buttonRANDOM;
    private ImageButton buttonHOME;
    private int         type;


    @Override
    //When this activity is called, onCreate is called
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_screen);
        type = getIntent().getIntExtra("type", 0);

        buttonRANDOM = findViewById(R.id.levelsButtonRANDOM);
        buttonHOME = findViewById(R.id.levelsButtonHOME);

        levelONE = findViewById(R.id.levelButtonONE);
        levelTWO = findViewById(R.id.levelButtonTWO);
        levelTHREE = findViewById(R.id.levelButtonTHREE);
        levelFOUR = findViewById(R.id.levelButtonFOUR);
        levelFIVE = findViewById(R.id.levelButtonFIVE);
        levelSIX = findViewById(R.id.levelButtonSIX);
        levelSEVEN = findViewById(R.id.levelButtonSEVEN);
        levelEIGHT = findViewById(R.id.levelButtonEIGHT);
        levelNINE = findViewById(R.id.levelButtonNINE);


        buttonRANDOM.setOnClickListener(this);
        buttonHOME.setOnClickListener(this);

        levelONE.setOnClickListener(this);
        levelTWO.setOnClickListener(this);
        levelTHREE.setOnClickListener(this);
        levelFOUR.setOnClickListener(this);
        levelFIVE.setOnClickListener(this);
        levelSIX.setOnClickListener(this);
        levelSEVEN.setOnClickListener(this);
        levelEIGHT.setOnClickListener(this);
        levelNINE.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.levelsButtonHOME) {
            Intent homeIntent = new Intent(v.getContext(), HomeScreen.class);
            startActivity(homeIntent);

        } else {
            Intent gameIntent;
            if (type == 1)
                gameIntent = new Intent(v.getContext(), List_Activity.class);
            else
                gameIntent = new Intent(v.getContext(), GameScreen.class);
            switch (v.getId()) {
                case (R.id.levelButtonONE): {
                    SpUtils.putInt(StringUtils.LEVEL , 1);
                    gameIntent.putExtra(LEVEL_TYPE, "ONE");
                    break;
                }
                case (R.id.levelButtonTWO): {
                    SpUtils.putInt(StringUtils.LEVEL , 2);
                    gameIntent.putExtra(LEVEL_TYPE, "TWO");
                    break;

                }
                case (R.id.levelButtonTHREE): {
                    SpUtils.putInt(StringUtils.LEVEL , 3);
                    gameIntent.putExtra(LEVEL_TYPE, "THREE");
                    break;

                }
                case (R.id.levelButtonFOUR): {
                    SpUtils.putInt(StringUtils.LEVEL , 4);
                    gameIntent.putExtra(LEVEL_TYPE, "FOUR");
                    break;

                }
                case (R.id.levelButtonFIVE): {
                    SpUtils.putInt(StringUtils.LEVEL , 5);
                    gameIntent.putExtra(LEVEL_TYPE, "FIVE");
                    break;

                }
                case (R.id.levelButtonSIX): {
                    SpUtils.putInt(StringUtils.LEVEL , 6);
                    gameIntent.putExtra(LEVEL_TYPE, "SIX");
                    break;

                }
                case (R.id.levelButtonSEVEN): {
                    SpUtils.putInt(StringUtils.LEVEL , 7);
                    gameIntent.putExtra(LEVEL_TYPE, "SEVEN");
                    break;

                }
                case (R.id.levelButtonEIGHT): {
                    SpUtils.putInt(StringUtils.LEVEL , 8);
                    gameIntent.putExtra(LEVEL_TYPE, "EIGHT");
                    break;

                }
                case (R.id.levelButtonNINE): {
                    SpUtils.putInt(StringUtils.LEVEL , 9);
                    gameIntent.putExtra(LEVEL_TYPE, "NINE");
                    break;

                }
                case (R.id.levelsButtonRANDOM): {

                    gameIntent.putExtra(LEVEL_TYPE, "RANDOM");
                    break;

                }
            }
            startActivity(gameIntent);
        }
    }


}
