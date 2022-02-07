package com.academy.appbraintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtTimes, txtQuestions, txtScores, txtResult;
    Button btn0, btn1, btn2, btn3, btnPlayAgain, btnPlay;
    ConstraintLayout gameLayout;
    MediaPlayer mediaPlayer;

    int locationOfCorrectAnswer;
    int score;
    int numberOfQuestion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimes=findViewById(R.id.txtTimes);
        txtQuestions=findViewById(R.id.txtQuestions);
        txtScores=findViewById(R.id.txtScores);
        txtResult=findViewById(R.id.txtResult);
        btn0=findViewById(R.id.btn0);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btnPlayAgain=findViewById(R.id.btnPlayAgain);
        btnPlay=findViewById(R.id.btnPlay);
        gameLayout = findViewById(R.id.gameLayout);

        btnPlay.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        newQuestion();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setVisibility(View.INVISIBLE);
                gameLayout.setVisibility(View.VISIBLE);

                playAgain(findViewById(R.id.txtScores));
            }
        });
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            txtResult.setText("Correct!");
            score++;
            mediaPlayer=MediaPlayer.create(this,R.raw.correctsound);
            mediaPlayer.start();
        }else {
            txtResult.setText("Wrong!");
            mediaPlayer=MediaPlayer.create(this,R.raw.wrong);
            mediaPlayer.start();
        }

        numberOfQuestion++;
        txtScores.setText(score+"/"+numberOfQuestion);
        newQuestion();
    }

    public void newQuestion(){

        Random random=new Random();
        int a=random.nextInt(21);
        int b=random.nextInt(21);

        txtQuestions.setText(a+" + "+b);

        ArrayList<Integer> answers=new ArrayList<>();
        //random vị trí đáp án đúng
        locationOfCorrectAnswer=random.nextInt(4);
        answers.clear();
        for (int i=0;i<4;i++){
            if (i == locationOfCorrectAnswer) {
                answers.add(a+b);
            }else {
                int wrongAnswer=random.nextInt(41);
                while (wrongAnswer == a + b) {
                    wrongAnswer=random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        btn0.setText(Integer.toString(answers.get(0)));
        btn1.setText(Integer.toString(answers.get(1)));
        btn2.setText(Integer.toString(answers.get(2)));
        btn3.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view) {
        newQuestion();

        score=0;
        numberOfQuestion=0;
        txtTimes.setText("30s");
        txtScores.setText(score+"/"+numberOfQuestion);
        btnPlayAgain.setVisibility(View.INVISIBLE);
        txtResult.setText("");

        btn0.setEnabled(true);
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);

        new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimes.setText(millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                txtResult.setText("Done");
                btnPlayAgain.setVisibility(View.VISIBLE);

                btn0.setEnabled(false);
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
            }
        }.start();
    }
}