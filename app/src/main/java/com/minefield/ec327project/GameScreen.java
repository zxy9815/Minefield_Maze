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

    //This Matrix is for Level ONE.
    private int[][] levelOne = {{0, 0, 0, 0, 0, 0, 0, 1, 3}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0}, {2, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level TWO.
    private int[][] levelTwo = {{0, 0, 0, 3, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 1, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level THREE
    private int[][] levelThree = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}, {3, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 1, 1, 2}, {0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level FOUR.
    private int[][] levelFour = {{0, 0, 0, 1, 1, 1, 0, 1, 3}, {0, 0, 0, 1, 0, 1, 1, 1, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 1, 2}, {0, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level FIVE.
    private int[][] levelFive = {{0, 0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 3, 1, 0}, {0, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 1, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 2, 0, 0, 0, 0, 0}};

    //This Matrix is for Level SIX.
    private int[][] levelSix = {{0, 0, 0, 0, 1, 1, 0, 1, 2}, {0, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 0, 0, 0}, {1, 0, 1, 0, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0}, {3, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level SEVEN.
    private int[][] levelSeven = {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 0, 1, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 1, 0, 0, 0}, {1, 0, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 2, 0, 0}};

    //This Matrix is for Level EIGHT.
    private int[][] levelEight = {{1, 1, 0, 0, 1, 1, 0, 0, 0}, {0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1}, {0, 0, 0, 1, 0, 0, 1, 0, 1}, {0, 1, 1, 1, 1, 0, 1, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 2}, {0, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //This Matrix is for Level NINE.
    private int[][] levelNine  = {{1, 0, 1, 1, 1, 1, 1, 0, 1}, {1, 1, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 0, 1, 0, 0, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1}, {1, 0, 0, 1, 1, 1, 0, 1, 1}, {1, 1, 1, 1, 0, 1, 2, 0, 1}};


    //This is the Matrix that will be used for gameplay.
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

        //Sets the Screen to be the GAME_SCREEN
        setContentView(R.layout.game_screen);
        super.onCreate(savedInstanceState);
        // Get a reference to our posts
        instance = FirebaseFirestore.getInstance();
        dialog = new PostScoreDialog(this);

        //Creates reference that allows us to check local memory on phone. Will be important for reading high scores
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences prefs2 = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);

        //Calls function to check what level was passed to the gameScreen from levelScreen
        level = getLevel();

        //Stores the player's color choice in a string so we can update map accordingly
        playerColorChoice = prefs.getString("color", "RED");

        //Stores the highscore in an int that we can reference later if the player beats previous highscore.
        playerHighScore = prefs2.getInt("highScore" + level, 0);

        System.out.println("High Score: " + level + " - " +  playerHighScore);


        //Connects Textviews from Design to actual Code
        highscoreTextView = findViewById(R.id.highScore);
        levelTextView = findViewById(R.id.levelText);
        timeTextView = findViewById(R.id.timeText);
        timeTextView.setText("");

        //Connects Buttons from Design to actual code.
        backButton = findViewById(R.id.gameBackBUTTON);
        backButton.setOnClickListener(this);

        upButton = findViewById(R.id.gameButtonUP);
        downButton = findViewById(R.id.gameButtonDOWN);
        leftButton = findViewById(R.id.gameButtonLEFT);
        rightButton = findViewById(R.id.gameButtonRIGHT);

        //Creates listeners for all our buttons.
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);

        //Message that instructs user what to do to beat game
        levelTextView.setText("Guide the WHITE Square to the BLACK Square to Complete the level");
        highscoreTextView.setText("");


        //Checking what level we received from previous screen and upating grid accordingly.
        //If Level = case, we will load corresponding grid/matrix;
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

            //If level was random, we need to generate the level for them.
            case ("RANDOM"): {

                //Creates new instance of LevelGenerator Class
                levelGenerator RandomLevel = new levelGenerator();

                //New Random numbers
                Random rand = new Random();

                //Seed generation so we can repeat a level or generate the same "UNique" level
                long seed = rand.nextLong();

                //Directness is how straight we will make path
                double directness = rand.nextDouble();

                //Minechance is change for a random mine.
                double mineChance = rand.nextDouble();

                //Ended up hard coding values passed to levelGenerator so that we would get more consistent levels
                grid = levelGenerator.levelGeneratorFn(0.66, 0.9, 7, seed);
                break;
            }
        }



        //Function is called to reveal the board
        //This is where the user will try to memorize the board.
        revealBoard();

        //This is used so we can create the delay.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //Clears baord, settings all squares back to grtey, the start to white, amnd the end to black.
                clearBoard();
                levelTextView.setText("");
                startTime = System.currentTimeMillis();

                //Calls updateTime to start the counter for the highscore and current timer that gets displayed.
                updateTime();

                //Player is no longer in memory phase, and we will allow them to click buttons.
                memoryPhase = false;

                //We will check if the player has a high score and not RANDOM level, and we will load the high score.
                if(playerHighScore > 0 && level != "RANDOM"){

                    //Convets int of seconds to MM:SS format time
                    int displayMinutes = playerHighScore / 60;
                    int displaySeconds = playerHighScore % 60;

                    String minutesString;
                    String secondsString;


                    //Displays time. Checks for cases to make it look nicer.
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


    //This function sets all the squares to grey, and then ultimately sends the starting square to white where the player currently is.
    private void clearBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                changeColor(j, i, "Clear");
            }
        }
        changeColor(currentX, currentY, "Start");
    }


    //When we reveal board, we iterate over grid and have to check values of grid.
    private void revealBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                //If location is a 1, that means we should make it a safe square, and change to color choice of player
                if (grid[j][i] == 1) {
                    changeColor(i, j, "Reveal");


                }
                //If locations is a 2, that is the finish square.
                else if (grid[j][i] == 2) {
                    changeColor(i, j, "Winner");
                }
                //If location is a 3, that means it is start, and we inintialize values for currentX and currentY which should be starting locations
                else if (grid[j][i] == 3) {
                    changeColor(i, j, "Start");
                    currentX = i;
                    currentY = j;
                }
            }
        }


    }


    //Event when button is clicked.
    public void onClick(View v) {
        System.out.println(gameState);

        //Checks to make sure we are in right state
        if (!memoryPhase && gameState) {

            //If back button
            if (v.getId() == R.id.gameBackBUTTON) {
                gameState = false;
                finish();
            } else {

                //If directional Button
                switch (v.getId()) {

                    //If up, updateBoard
                    case (R.id.gameButtonUP): {
                        if (checkCanMove("UP")) {
                            updateBoard();
                        }
                        break;
                    }
                    //If down
                    case (R.id.gameButtonDOWN): {
                        if (checkCanMove("DOWN")) {
                            updateBoard();
                        }
                        break;
                    }

                    //if left
                    case (R.id.gameButtonLEFT): {
                        if (checkCanMove("LEFT")) {
                            updateBoard();
                        }
                        break;

                    }//if rifht
                    case (R.id.gameButtonRIGHT): {
                        if (checkCanMove("RIGHT")) {
                            updateBoard();
                        }
                        break;

                    }


                }

                //Gets current Gamestatus
                gameStatus = updateGameStatus();

                //Checks if player lost
                if (gameStatus.equals("Lost")) {
                    Intent loseIntent = new Intent(v.getContext(), LoseScreen.class);
                    startActivity(loseIntent);
                    finish();
                }
            }
        }
    }


    //Updates gate status by checking current posiition of player
    private String updateGameStatus() {
        //System.out.println("X: " + indexX + " Y: " + indexY + " Val: " + grid[indexX][indexY]);
        if(gameState) {

            //if current position of player is on a 0, they are on a  bomb and have lost the game
            switch (grid[currentY][currentX]) {
                case (0): {
                    gameState = false;
                    return "Lost";
                }
                //if player is on a 1, they are safe and still playing
                case (1): {
                    return "Playing";
                }

                //if player is on a 2, they just won the game
                case (2): {

                    //Game is no longer being played
                    gameState = false;
                    endTime = System.currentTimeMillis();
                    changeColor(currentX, currentY, "Winner");
                    Utils.showToast("You Win!");

                    //Checks to make sure level is not equal to random, and sends score to firebase
                    if ((SpUtils.getInt("level", 1) >= 1 || level.equals("RANDOM")) && !isPost)
                        scoreExists();

                    int currentScore = minutes * 60 + seconds - 1;
                    System.out.println(currentScore);

                    //Checks if we should update the player's highscore, if it is, we will write to memory
                    if (currentScore < playerHighScore || playerHighScore == 0) {
                        System.out.println(currentScore);
                        SharedPreferences prefs = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("highScore" + level, currentScore);
                        editor.commit();
                    }

                    //Delay to display winscreen/Situation
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

    //Checks level
    private String getLevel() {
        //Returns value that was passed to this instance when class was initialized.
        return getIntent().getExtras().getString(LevelsScreen.LEVEL_TYPE);
    }


    //Updates Time
    private void updateTime(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        //Gets Text to display on
                        TextView tv = (TextView) findViewById(R.id.timeText);

                        //If timer should be on
                        if(gameState){
                            if (updateGameStatus() == "Playing") {
                                //Checks to make sure we can nicely disply time in MM:SS format
                                if (seconds <= 9) {
                                    tv.setText(String.valueOf(minutes) + ":" + "0" + String.valueOf(seconds));
                                } else {
                                    tv.setText(String.valueOf(minutes) + ':' + String.valueOf(seconds));
                                }

                                //increment seconds
                                seconds += 1;

                                //chcek if 60 seconds, if so set seconds to 0 and increase minutes.
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


    //Updates the board based on a color
    private void changeColor(int x, int y, String type) {
        String buttonID = "b" + x + y;
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        Button updateButton = findViewById(resID);

        /*

            if(type == "Reveal"){
                updateButton.setBackgroundColor(Color.WHITE);
            }else
         */
        //If update type is clear, we set to gray
        if (type == "Clear") {
            updateButton.setBackgroundColor(Color.GRAY);
        }
        //if Update type is Winner, we set to black
        else if (type == "Winner") {
            updateButton.setBackgroundColor(Color.BLACK);
        }

        //If update type is Start, we set to WHITE
        else if (type == "Start") {
            updateButton.setBackgroundColor(Color.WHITE);
        }

        //Otherwise, we check player choice and update their color based on their choiuce
        else {
            switch (playerColorChoice) {

                //If they choose red we turn color red
                case ("RED"): {
                    updateButton.setBackgroundResource(R.color.choiceRED);
                    break;
                }

                //If they choose orange we turn color orange
                case ("ORANGE"): {
                    updateButton.setBackgroundResource(R.color.choiceORANGE);
                    break;
                }

                //if they choiose yellow, we turn yellow
                case ("YELLOW"): {
                    updateButton.setBackgroundResource(R.color.choiceYELLOW);
                    break;
                }

                //If they choose lime
                case ("LIME"): {
                    updateButton.setBackgroundResource(R.color.choiceLIME);
                    break;
                }

                //if they chose lightblue
                case ("LIGHTBLUE"): {
                    updateButton.setBackgroundResource(R.color.choiceLIGHTBLUE);
                    break;
                }

                //if they choose purplpe
                case ("PURPLE"): {
                    updateButton.setBackgroundResource(R.color.choicePURPLE);
                    break;

                }
            }
        }

    }

    //Updates indexes
    private void updateBoard() {
        changeColor(previousX, previousY, "Update");
        changeColor(currentX, currentY, "Start");
    }


    //Checks to see if player can move in that direction
    private boolean checkCanMove(String direction) {
        previousX = currentX;
        previousY = currentY;

        switch (direction) {
            case ("UP"): {

                //checks if player isn't at top, and can in fact move up
                if (currentY <= 0) {
                    return false;
                } else {
                    currentY -= 1;
                    return true;
                }
            }
            case ("DOWN"): {
                //checks if user is at bottom and can't move any lower.
                if (currentY >= 8) {
                    return false;
                } else {
                    currentY += 1;
                    return true;
                }

            }


            case ("RIGHT"): {

                //checks if user is at right hand side of screen and can't go more right
                if (currentX >= 8) {
                    return false;
                } else {
                    currentX += 1;
                    return true;
                }
            }
            case ("LEFT"): {

                //checkjs if user is at left and cant go more left.
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


    //Code to update the firebase. Dennis worked on this and pasted it in here. They get code
    //off of internet/friend and don't actually understand it
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
    //Code to update the firebase. Dennis worked on this and pasted it in here. They get code
    //off of internet/friend and don't actually understand it
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
    //Code to update the firebase. Dennis worked on this and pasted it in here. They get code
    //off of internet/friend and don't actually understand it
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