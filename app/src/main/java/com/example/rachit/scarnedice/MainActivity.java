package com.example.rachit.scarnedice;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.R.attr.id;
import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    static int userOverallScore = 0;
    static int userTurnScore = 0;
    static int computerOverallScore = 0;
    static int computerTurnScore = 0;

    TextView showTurn, turnScore, overallScore;
    ImageView diceView;
    Button rollButton, holdButton, resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTurn = (TextView) findViewById(R.id.textView);
        turnScore = (TextView) findViewById(R.id.textView2);
        overallScore = (TextView) findViewById(R.id.textView3);
        diceView = (ImageView) findViewById(R.id.imageView);
        rollButton = (Button) findViewById(R.id.button2);
        holdButton = (Button) findViewById(R.id.button);
        resetButton = (Button) findViewById(R.id.button3);

        //Reset();

    }

    public int getRandomDiceRoll() {

        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public void changeImage(int i) {

        switch (i) {

            case 1:
                diceView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.dice6);
                break;

        }


    }

    public void displayTurnScore(int score) {
        turnScore.setText(String.format("Turn Score: %d", score));
    }

    public void onRollClick(View view) {
        int diceRoll = getRandomDiceRoll();
        changeImage(diceRoll);
        ControlPlayerTurn(diceRoll);
    }

    public void ControlPlayerTurn(int diceRoll) {
        if (diceRoll == 1) {
            ComputerTurn();

        } else {
            userTurnScore = userTurnScore + diceRoll;
            userOverallScore+=userTurnScore;
            displayTurnScore(userTurnScore);
        }

    }

    public void onHoldClick(View view) {
        userOverallScore = userOverallScore + userTurnScore;

        showTurn.setText("Computer Turn");
        displayTurnScore(0);
        overallScore.setText(String.format("Your score: (%d) Computer score: (%d)", userOverallScore, computerOverallScore));
        ComputerTurn();
    }

    public void ComputerTurn() {
        if (userOverallScore >= 100)
            Toast.makeText(this, "You Won", Toast.LENGTH_SHORT).show();
        else {

            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            userTurnScore = 0;
            computerTurnScore = 0;
            android.os.Handler handler = new android.os.Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveComputerTurn();

                }
            }, 1000);


            computerOverallScore = computerOverallScore + computerTurnScore;
            overallScore.setText(String.format("Your score: (%d) Computer score: (%d)", userOverallScore, computerOverallScore));
            showTurn.setText("Your Turn");
            ChangeToPlayerTurn();
        }

    }

    public void moveComputerTurn(){
            if (computerTurnScore <= 20) {
                int num = getRandomDiceRoll();
                if (num == 1) {
                    // Toast.makeText(this, "Your Turn", Toast.LENGTH_SHORT).show();
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                } else {
                    computerTurnScore = computerTurnScore + num;

                }


            }

        }




    public void ChangeToPlayerTurn() {

        if (computerOverallScore >= 100)
            Toast.makeText(this, "Ah! YOU LOSS. Try Again", Toast.LENGTH_SHORT).show();
        else {
            rollButton.setEnabled(true);
            holdButton.setEnabled(true);
        }


    }


    public void Reset(View view) {

        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        showTurn.setText("Your Turn");
        turnScore.setText("Turn Score: 0");
        overallScore.setText(String.format("Your score: (%d) Computer score: (%d)", userOverallScore, computerOverallScore));
        changeImage(0);


    }




}
