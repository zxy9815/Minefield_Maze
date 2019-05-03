package com.minefield.ec327project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * This class includes showing a dialog telling user that score is uploaded
 */
public class PostScoreDialog {

    private       TextView    textView;
    private final AlertDialog dialog;

    public PostScoreDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_post_score, null);
        textView = view.findViewById(R.id.Dialog_Score);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void show(String time, String level) {
        if (textView == null || dialog == null)
            return;
        textView.setText("Level：" + level + "   " + "Time " + time + "\n" + "Uploading scores");
        dialog.show();
    }

    public void progressShow(String message) {
        if (textView == null || dialog == null)
            return;
        textView.setText(message);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
    }
}
