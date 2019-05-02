package com.minefield.ec327project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends Activity implements OnClickListener {

    public  String   level;
    private TextView levelTextView;
    private TextView timeTextView;
    private TextView highscoreTextView;

    private ImageButton backButton;

    private ImageButton downButton;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private ImageButton upButton;

    private boolean gameState = true;

    public int currentX;
    public int previousX;
    public int currentY;
    public int previousY;

    private int playerHighScore;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;




    private String gameStatus;

    private int[][] levelOne = {{0, 0, 0, 0, 0, 0, 0, 1, 3}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0}, {2, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] levelTwo = {{0, 0, 0, 3, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 1, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] levelThree = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}, {3, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 1, 1, 2}, {0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] levelFour = {{0, 0, 0, 1, 1, 1, 0, 1, 3}, {0, 0, 0, 1, 0, 1, 1, 1, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 1, 2}, {0, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] levelFive = {{0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 3, 1, 0}, {0, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 1, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 2, 0, 0, 0, 0, 0}};

    private int[][] levelSix = {{0, 0, 0, 0, 1, 1, 0, 1, 2}, {0, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 0, 0, 0}, {1, 0, 1, 0, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0}, {3, 0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] levelSeven = {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 0, 1, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 1, 0, 0, 0}, {1, 0, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 2, 0, 0}};

    private int[][] levelEight = {{1, 1, 0, 0, 1, 1, 0, 0, 0}, {0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1}, {0, 0, 0, 1, 0, 0, 1, 0, 1}, {0, 1, 1, 1, 1, 0, 1, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 2}, {0, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};
    private int[][] levelNine  = {{1, 0, 1, 1, 1, 1, 1, 0, 1}, {1, 1, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 0, 1, 0, 0, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1}, {1, 0, 0, 1, 1, 1, 0, 1, 1}, {1, 1, 1, 1, 0, 1, 2, 0, 1}};

    private int[][] grid;

    private boolean memoryPhase = true;

    public String playerColorChoice;

    public  Long              startTime;
    public  Long              endTime;
    public  boolean           success = false;
    private PostScoreDialog   dialog;
    private FirebaseFirestore instance;

    public int seconds = 0;
    public int minutes = 0;


    @Override
    //When this activity is called, onCreate is called
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.game_screen);
        super.onCreate(savedInstanceState);
        // Get a reference to our posts
        instance = FirebaseFirestore.getInstance();
        dialog = new PostScoreDialog(this);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences prefs2 = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);

        level = getLevel();

        playerColorChoice = prefs.getString("color", "RED");
        playerHighScore = prefs2.getInt("highScore" + level, 0);

        System.out.println("High Score: " + level + " - " +  playerHighScore);

        highscoreTextView = findViewById(R.id.highScore);
        levelTextView = findViewById(R.id.levelText);
        timeTextView = findViewById(R.id.timeText);

        timeTextView.setText("");

        backButton = findViewById(R.id.gameBackBUTTON);
        backButton.setOnClickListener(this);

        upButton = findViewById(R.id.gameButtonUP);
        downButton = findViewById(R.id.gameButtonDOWN);
        leftButton = findViewById(R.id.gameButtonLEFT);
        rightButton = findViewById(R.id.gameButtonRIGHT);

        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);

        levelTextView.setText("Guide the WHITE Square to the BLACK Square to Complete the level");
        highscoreTextView.setText("");

        switch (level) {
            case ("ONE"): {
                grid = levelOne;
                break;
            }
            case ("TWO"): {
                grid = levelTwo;
                break;
            }
            case ("THREE"): {
                grid = levelThree;
                break;
            }
            case ("FOUR"): {
                grid = levelFour;
                break;
            }
            case ("FIVE"): {
                grid = levelFive;
                break;
            }
            case ("SIX"): {
                grid = levelSix;
                break;
            }
            case ("SEVEN"): {
                grid = levelSeven;
                break;
            }
            case ("EIGHT"): {
                grid = levelEight;
                break;
            }
            case ("NINE"): {
                grid = levelNine;
                break;
            }
            case ("RANDOM"): {
                levelGenerator RandomLevel = new levelGenerator();
                Random rand = new Random();
                long seed = rand.nextLong();
                double directness = rand.nextDouble();
                double mineChance = rand.nextDouble();
                grid = levelGenerator.levelGeneratorFn(0.66, 0.9, 7, seed);
                break;
            }
        }

















        revealBoard();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                clearBoard();
                levelTextView.setText("");
                startTime = System.currentTimeMillis();
                updateTime();
                memoryPhase = false;
                if(playerHighScore > 0 && level != "RANDOM"){
                    int displayMinutes = playerHighScore / 60;
                    int displaySeconds = playerHighScore % 60;

                    String minutesString;
                    String secondsString;

                    if(displayMinutes <= 9){
                        minutesString = "0" + Integer.toString(displayMinutes);
                    }else{
                        minutesString = Integer.toString(displayMinutes);
                    }

                    if(displaySeconds <= 9){
                        secondsString = "0" + Integer.toString(displaySeconds);
                    }else{
                        secondsString = Integer.toString(displaySeconds);
                    }

                    highscoreTextView.setText(minutesString + ":" + secondsString);

                }else{
                    highscoreTextView.setText("None");
                }
            }
        }, 2500);
    }

    private void clearBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                changeColor(j, i, "Clear");
            }
        }
        changeColor(currentX, currentY, "Start");
    }

    private void revealBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[j][i] == 1) {
                    changeColor(i, j, "Reveal");
                } else if (grid[j][i] == 2) {
                    changeColor(i, j, "Winner");
                } else if (grid[j][i] == 3) {
                    changeColor(i, j, "Start");
                    currentX = i;
                    currentY = j;
                }
            }
        }


    }

    public void onClick(View v) {
        System.out.println(gameState);
        if (!memoryPhase && gameState) {
            if (v.getId() == R.id.gameBackBUTTON) {
                gameState = false;
                finish();
            } else {
                switch (v.getId()) {
                    case (R.id.gameButtonUP): {
                        if (checkCanMove("UP")) {
                            updateBoard();
                        }
                        break;
                    }
                    case (R.id.gameButtonDOWN): {
                        if (checkCanMove("DOWN")) {
                            updateBoard();
                        }
                        break;
                    }
                    case (R.id.gameButtonLEFT): {
                        if (checkCanMove("LEFT")) {
                            updateBoard();
                        }
                        break;

                    }
                    case (R.id.gameButtonRIGHT): {
                        if (checkCanMove("RIGHT")) {
                            updateBoard();
                        }
                        break;

                    }


                }
                gameStatus = updateGameStatus();
                if (gameStatus.equals("Lost")) {
                    Intent loseIntent = new Intent(v.getContext(), LoseScreen.class);
                    startActivity(loseIntent);
                    finish();
                }
            }
        }
    }

    private String updateGameStatus() {
        //System.out.println("X: " + indexX + " Y: " + indexY + " Val: " + grid[indexX][indexY]);
        if(gameState) {
            switch (grid[currentY][currentX]) {
                case (0): {
                    gameState = false;
                    return "Lost";
                }
                case (1): {
                    return "Playing";
                }
                case (2): {
                    gameState = false;
                    endTime = System.currentTimeMillis();
                    changeColor(currentX, currentY, "Winner");
                    Utils.showToast("You Win!");
                    if ((SpUtils.getInt("level", 1) >= 1 || level.equals("RANDOM")) && !isPost)
                        scoreExists();

                    int currentScore = minutes * 60 + seconds - 1;
                    System.out.println(currentScore);
                    if (currentScore < playerHighScore || playerHighScore == 0) {
                        System.out.println(currentScore);
                        SharedPreferences prefs = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("highScore" + level, currentScore);
                        editor.commit();
                    }

                    Handler winScreen = new Handler();
                    winScreen.postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 2000);

                    return "Winner!";
                }
                case (3): {
                    return "Playing";
                }
            }
            return "Error";
        }
        return "Error";
    }


    private String getLevel() {

        return getIntent().getExtras().getString(LevelsScreen.LEVEL_TYPE);
    }

    private void updateTime(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            TextView tv = (TextView) findViewById(R.id.timeText);
                            if(gameState){
                                if (updateGameStatus() == "Playing") {
                                    if (seconds <= 9) {
                                        tv.setText(String.valueOf(minutes) + ":" + "0" + String.valueOf(seconds));
                                    } else {
                                        tv.setText(String.valueOf(minutes) + ':' + String.valueOf(seconds));
                                    }

                                    seconds += 1;

                                    if (seconds == 60) {
                                        seconds = 0;
                                        minutes += 1;
                                    }
                                }
                                System.out.println("Minutes: " + minutes + " Seconds: " + seconds);
                            }
                        }

                    });
                }
        }, 0, 1000);
   }



    private void changeColor(int x, int y, String type) {
        String buttonID = "b" + x + y;
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        Button updateButton = findViewById(resID);

        /*

            if(type == "Reveal"){
                updateButton.setBackgroundColor(Color.WHITE);
            }else
         */

        if (type == "Clear") {
            updateButton.setBackgroundColor(Color.GRAY);
        } else if (type == "Winner") {
            updateButton.setBackgroundColor(Color.BLACK);
        } else if (type == "Start") {
            updateButton.setBackgroundColor(Color.WHITE);
        } else {
            switch (playerColorChoice) {
                case ("RED"): {
                    updateButton.setBackgroundResource(R.color.choiceRED);
                    break;
                }
                case ("ORANGE"): {
                    updateButton.setBackgroundResource(R.color.choiceORANGE);
                    break;
                }
                case ("YELLOW"): {
                    updateButton.setBackgroundResource(R.color.choiceYELLOW);
                    break;
                }
                case ("LIME"): {
                    updateButton.setBackgroundResource(R.color.choiceLIME);
                    break;
                }
                case ("LIGHTBLUE"): {
                    updateButton.setBackgroundResource(R.color.choiceLIGHTBLUE);
                    break;
                }
                case ("PURPLE"): {
                    updateButton.setBackgroundResource(R.color.choicePURPLE);
                    break;

                }
            }
        }

    }

    private void updateBoard() {
        changeColor(previousX, previousY, "Update");
        changeColor(currentX, currentY, "Start");
    }


    private boolean checkCanMove(String direction) {
        previousX = currentX;
        previousY = currentY;

        switch (direction) {
            case ("UP"): {
                if (currentY <= 0) {
                    return false;
                } else {
                    currentY -= 1;
                    return true;
                }
            }
            case ("DOWN"): {
                if (currentY >= 8) {
                    return false;
                } else {
                    currentY += 1;
                    return true;
                }

            }
            case ("RIGHT"): {
                if (currentX >= 8) {
                    return false;
                } else {
                    currentX += 1;
                    return true;
                }
            }
            case ("LEFT"): {
                if (currentX <= 0) {
                    return false;
                } else {
                    currentX -= 1;
                    return true;
                }
            }
        }
        return false;
    }

    boolean isPost = false;

    void scoreExists() {
        if (isPost)
            return;
        isPost = true;
        final String time = Utils.formatTime(endTime - startTime);
        dialog.show(time, getLevel());
        CollectionReference collection = instance.collection(Utils.getTableName());
        // Attach a listener to read the data at our posts reference
        collection.whereEqualTo("phone", SpUtils.getString(StringUtils.USER_ID, ""))
                .whereEqualTo("level", getLevel() + "")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        Log.e("yxs", "Length：" + queryDocumentSnapshots.getDocuments().size());
                        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            Log.e("yxs", "Data：" + queryDocumentSnapshots.getDocuments().toString());
                        }
                        if (queryDocumentSnapshots.getDocuments().size() != 0) {
                            updateScore(endTime - startTime, queryDocumentSnapshots.getDocuments().get(0).getId());
                            return;
                        }
                        postScore(endTime - startTime);
                    }
                });
    }

    private void postScore(Long time) {
        ScoreBean bean = new ScoreBean(SpUtils.getString(StringUtils.USER_ID, ""), getLevel() + "", time);
        instance.collection(Utils.getTableName()).add(bean).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Utils.showToast("Post Success!");
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Utils.showToast("Post Failure!");
                        dialog.dismiss();
                    }
                });
    }

    private void updateScore(Long time, String id) {
        ScoreBean bean = new ScoreBean(SpUtils.getString(StringUtils.USER_ID, ""),
                getLevel() + "",
                time);
        instance.collection(Utils.getTableName()).document(id).set(bean, SetOptions.merge()).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Utils.showToast("Post Success!");
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Utils.showToast("Post Failure!");
                        dialog.dismiss();
                    }
                });
    }
}
