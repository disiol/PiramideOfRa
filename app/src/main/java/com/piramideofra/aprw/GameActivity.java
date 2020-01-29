package com.piramideofra.aprw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;


import com.piramideofra.aprw.databinding.ActivityGameBinding;

import java.util.ArrayList;

import static com.piramideofra.aprw.Constants.MYLOG_TEG;

public class GameActivity extends AppCompatActivity {
    ActivityGameBinding activityGameBinding;
    private int fildeSize = 16;
    private ArrayList<ImageView> bord = new ArrayList<ImageView>();
    private int blak_boder;
    private GridLayout bordGridLayout;
    private int sizeOfElements = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        blak_boder = R.drawable.blak_boder;

        activityGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);

        while (fildeSize > 0) {
            bordGridLayout = activityGameBinding.bordGridLayout;
            showBord(bordGridLayout);
        }

        for (int i = 0; i < bord.size(); i++) {
            ImageView imageView = bord.get(i);
            setSizeAndDrable(imageView,R.drawable.points_2);
            Log.d(MYLOG_TEG, "bord imageView  id" + imageView.getId());
            Log.d(MYLOG_TEG, "bord imageView  etContentDescription" + imageView.getContentDescription());

        }


    }

    private void showBord(GridLayout gridLayout) {
        CharSequence charSequence = "0";
        ImageView imageView = eadToGridLayout(gridLayout, blak_boder, charSequence);
        imageView.requestLayout();
        setSize(imageView);
        bord.add(imageView);

        fildeSize--;

    }

    @NotNull
    private ImageView eadToGridLayout(GridLayout gridLayout, int imageResource, CharSequence contentDescription) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageResource);
        imageView.setId(fildeSize);
        imageView.setBackground(ContextCompat.getDrawable(this, blak_boder));
        imageView.setClickable(true);
        gridLayout.addView(imageView);
        imageView.setContentDescription(contentDescription);

        return imageView;
    }

    private void setSize(ImageView imageView) {
        Display display = getWindowManager().getDefaultDisplay();
        setImageSise(imageView, display, sizeOfElements);
    }



    private void setSizeAndDrable(ImageView imageView, int imageResource) {
        Display display = getWindowManager().getDefaultDisplay();
        setImageSise(imageView, display, sizeOfElements);
        imageView.setImageResource(imageResource);

    }

    private void setImageSise(ImageView imageView, Display display, int i) {
        imageView.getLayoutParams().height = (display.getWidth() * i) / 100;
        imageView.getLayoutParams().width = (display.getWidth() * i) / 100;
    }
}
