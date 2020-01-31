package com.piramideofra.aprw.manedger.game;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.piramideofra.aprw.R;

public class GameDialog extends Dialog {

    private OnGameDialogClickListener listener;

    public GameDialog(Context context, String msg, String leftMsg, String rightMsg) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = getLayoutInflater().inflate(R.layout.game_dialog, null);
        TextView tv = (TextView) v.findViewById(R.id.game_dialog_textview_msg);
        tv.setText(msg);
        Button left = (Button) v.findViewById(R.id.game_dialog_button_left);
        left.setText(leftMsg);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftClick();
                }
            }
        });
        Button right = (Button) v.findViewById(R.id.game_dialog_button_right);
        right.setText(rightMsg);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightClick();
                }
            }
        });
        setCancelable(false);
        setContentView(v);
    }

    public void setOnGameDialogClickListener(OnGameDialogClickListener listener) {
        this.listener = listener;
    }
}
