package com.minefield.ec327project;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

// This class is a collection of utility functions needed


public class Utils {
    private static Toast toast = null;
    //print string depending on context
    public static void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(MyApplication.mAppContext, msg, Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }

    //format timer in mm:ss
    public static String formatTime(Long l) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss.SSS");
        return format.format(new Date(l));
    }

    //return level string according to put int
    public static String getTableName() {
        switch (SpUtils.getInt("level", 1)) {
            case 5:
                return "FIVE";
            case 6:
                return "SIX";
            case 7:
                return "SEVEN";
            case 8:
                return "EIGHT";
            case 9:
                return "NINE";
        }
        return "FIVE";
    }

    //compare user's time in leader board database
    public static Comparator<ScoreBean> comparator = new Comparator<ScoreBean>() {
        @Override
        public int compare(ScoreBean o1, ScoreBean o2) {
            return (int) (o1.getTime() - o2.getTime());
        }
    };

}
