package com.minefield.ec327project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class HomeScreen extends Activity implements OnClickListener {
    //Initializes TextViews to display total and tip

    private ImageButton buttonLEVELS;
    private ImageButton buttonLEADERS;
    private ImageButton buttonCUSTOMIZE;


    @Override
    //When this activity is called, onCreate is called
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        buttonLEVELS = findViewById(R.id.homeButtonLEVELS);
        buttonLEADERS = findViewById(R.id.homeButtonLEADERS);
        buttonCUSTOMIZE = findViewById(R.id.homeButtonCUSTOMIZE);

        buttonLEVELS.setOnClickListener(this);
        buttonLEADERS.setOnClickListener(this);
        buttonCUSTOMIZE.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.homeButtonLEVELS): {
                Intent levelsIntent = new Intent(v.getContext(), LevelsScreen.class);
                startActivity(levelsIntent);
                break;

            }
            case (R.id.homeButtonCUSTOMIZE): {
                Intent customizeIntent = new Intent(v.getContext(), ColorScreen.class);
                startActivity(customizeIntent);
                break;
            }
            case (R.id.homeButtonLEADERS): {
                Intent levelsIntent = new Intent(v.getContext(), LevelsScreen.class);
                levelsIntent.putExtra("type", 1);
                startActivity(levelsIntent);
                break;
            }
        }
    }

}



/*Set the texts views so they display according to the parameters in result.xml
        tipTextView = (TextView) findViewById(R.id.tip);
                totalTextView = (TextView) findViewById(R.id.total);


                //Initializes button to the parameters in result.xml
                finished = (Button) findViewById(R.id.confirm);

                //Sets an onClickListener for the finished button
                finished.setOnClickListener(new OnClickListener()
                {
@Override
//If clicked, call the finish method
public void onClick(View v)
        {
        //Finish ends the current activity and goes back to the activity that called this one (Main in this case)
        finish();
        }
        });


        */