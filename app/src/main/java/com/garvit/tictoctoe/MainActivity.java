package com.garvit.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    Integer no = 0;
    AlertDialog.Builder builder;
    Boolean playerChance = true;
    String winMessage = "";
    TextView status;
     Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.gry));
        }
        status = findViewById(R.id.status);
        if (no == 0) {
            resetView();
        }
        builder = new AlertDialog.Builder(this);
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
    }

    private void checkWinner() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {

                if (gameState[winPosition[0]] == 0) {
                    winMessage = "Player first win";
                } else {
                    winMessage = "Player second win";

                }
                showDialog();
            }
        }
    }

    public void OnClick(View view) {
        Button btn = (Button) view;
        btn.startAnimation(animation);
        int tappedBtn = Integer.parseInt(btn.getTag().toString());
        if(gameState[tappedBtn-1] != 2)
        {
            Toast.makeText(this , "Play some where else", Toast.LENGTH_LONG).show();
            return;
        }
        no++;
        if (playerChance) {
            btn.setText("X");
            status.setText("Player 2 change");
            gameState[tappedBtn - 1] = 0;
            playerChance = false;
        } else {
            btn.setText("O");
            gameState[tappedBtn - 1] = 1;
            playerChance = true;
            status.setText("Player 1 chance");
        }
        if (no > 4)
            checkWinner();
    }

    public void Reset(View view) {
        resetView();
    }

    private void resetView() {
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        playerChance = true;
        status.setText("Player 1 chance");
        no = 0;
        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    }

    private void showDialog() {
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to Play again ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Start",
                                Toast.LENGTH_SHORT).show();
                        resetView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Game Close",
                                Toast.LENGTH_SHORT).show();
                        System.exit(0);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("");
        alert.show();
    }

}