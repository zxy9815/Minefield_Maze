package com.minefield.ec327project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ColorScreen extends Activity implements OnClickListener {

    private ImageButton colorRED;
    private ImageButton colorORANGE;
    private ImageButton colorYELLOW;
    private ImageButton colorLIME;
    private ImageButton colorLIGHTBLUE;
    private ImageButton colorPURPLE;
    private ImageButton colorBACK;
    private TextView colorSELECT;

    private String colorChoice;

    @Override
    //When this activity is called, onCreate is called
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_screen);

        colorBACK = (ImageButton) findViewById(R.id.colorbackbutton);
        colorRED = findViewById(R.id.colorbuttonred);
        colorORANGE = findViewById(R.id.colorbuttonorange);
        colorYELLOW = findViewById(R.id.colorbuttonyellow);
        colorLIME = findViewById(R.id.colorbuttonlime);
        colorLIGHTBLUE = findViewById(R.id.colorbuttonlightblue);
        colorPURPLE = findViewById(R.id.colorbuttonpurple);
        colorSELECT = findViewById(R.id.colorSelect);
        colorSELECT.setText("Select a Color");

        colorBACK.setOnClickListener(this);
        colorRED.setOnClickListener(this);
        colorORANGE.setOnClickListener(this);
        colorYELLOW.setOnClickListener(this);
        colorLIME.setOnClickListener(this);
        colorLIGHTBLUE.setOnClickListener(this);
        colorPURPLE.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.colorbackbutton){
            finish();
        }else {
            switch (v.getId()) {
                case (R.id.colorbuttonred): {
                    colorChoice = "RED";
                    colorSELECT.setText("Color Selected: RED");
                    break;
                }
                case (R.id.colorbuttonorange): {
                    colorChoice = "ORANGE";
                    colorSELECT.setText("Color Selected: ORANGE");
                    break;
                }
                case (R.id.colorbuttonyellow): {
                    colorChoice = "YELLOW";
                    colorSELECT.setText("Color Selected: YELLOW");
                    break;
                }
                case (R.id.colorbuttonlime): {
                    colorChoice = "LIME";
                    colorSELECT.setText("Color Selected: LIME");
                    break;
                }
                case (R.id.colorbuttonlightblue): {
                    colorChoice = "LIGHTBLUE";
                    colorSELECT.setText("Color Selected: LIGHT BLUE");
                    break;
                }
                case (R.id.colorbuttonpurple): {
                    colorChoice = "PURPLE";
                    colorSELECT.setText("Color Selected: PURPLE");
                    break;
                }
            }
        }
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("color", colorChoice);
        editor.commit();

    }
}
