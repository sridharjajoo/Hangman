package com.example.sridhar123.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private String[] words,hints,got_hints,got_answers,tv_answers,tv_hints;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private GridView gridView;
    private LetterAdapter adapter;
    private String[] letters ={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private ImageView[] bodyParts;
    private Integer numParts=6;
    private Integer currPart;
    private Integer numCorr;
    private Integer numChars,pos;
    private Integer current_count;
    private Character current_letter;
    private Integer k=0;
    private Integer no_letter;
    private static final String TAG="GameActivity";
    private Integer[] flag;
    private int j,key,index1=0,index2=0,index3=0;
    private TextView hintView;
    private String j1="",j2="",j3="";
    private Integer positionChecked[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle b = getIntent().getExtras();
        index1 =b.getInt("tech");
        index2 = b.getInt("got");
        index3 = b.getInt("tv");

        Resources resource = getResources();
        if(index1==1) {
            words = resource.getStringArray(R.array.words);
            hints = resource.getStringArray(R.array.hints);
        }

        else if(index2==2)
        {
            got_answers = resource.getStringArray(R.array.got_answers);
            got_hints = resource.getStringArray(R.array.got_hints);
        }

        else if(index3==3)
        {
            tv_answers=resource.getStringArray(R.array.tv_answers);
            tv_hints=resource.getStringArray(R.array.tv_hints);

        }

        flag = new Integer[26];
        for(int i=0;i<26;i++)
            flag[i]=1;
        rand = new Random();
        currWord="";

        wordLayout = (LinearLayout) findViewById(R.id.word);
        positionChecked = new Integer[10];


        for(int i=0;i<10;i++)
            positionChecked[i]=1;

        gridView = (GridView) findViewById(R.id.gridview_letters);
        adapter = new LetterAdapter(this , letters);
        gridView.setAdapter(adapter);


        playGame();
    }

    private void playGame(){

        currPart=0;
        numChars=currWord.length();
        numCorr=0;
        j=0;
        current_count=0;
        no_letter=0;
        for(int i=0;i<26;i++)
            flag[i]=1;

        if(index1==1) {
            pos = rand.nextInt(words.length);
            String newWord = words[pos];
            while (positionChecked[pos] == 0)    //.equals is used to comapare two strings
                pos = rand.nextInt(words.length);
            newWord = words[pos];
            currWord = newWord;
            k++;
            hintView = (TextView) findViewById(R.id.hint);
            hintView.setText(hints[pos]);
            positionChecked[pos]=0;

        }

        else if(index2==2)
        {

            pos = rand.nextInt(got_answers.length);
            String newWord = got_answers[pos];
            while (positionChecked[pos] == 0)    //.equals is used to comapare two strings
                pos = rand.nextInt(got_answers.length);
            newWord = got_answers[pos];
            currWord = newWord;
            k++;
            hintView = (TextView) findViewById(R.id.hint);
            hintView.setText(got_hints[pos]);
            positionChecked[pos]=0;

        }

        else if(index3==3)
        {

            pos = rand.nextInt(tv_answers.length);
            String newWord = tv_answers[pos];
            while (positionChecked[pos] == 0)    //.equals is used to comapare two strings
                pos = rand.nextInt(tv_answers.length);
            newWord = tv_answers[pos];
            currWord = newWord;
            k++;
            hintView = (TextView) findViewById(R.id.hint);
            hintView.setText(tv_hints[pos]);
            positionChecked[pos]=0;
        }

       if(k==10)
       {
           for(int i=0;i<10;i++)
               positionChecked[i]=1;
       }

        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.left_arm);
        bodyParts[3] = (ImageView)findViewById(R.id.right_arm);
        bodyParts[4] = (ImageView)findViewById(R.id.left_leg);
        bodyParts[5] = (ImageView)findViewById(R.id.right_leg);
        for(int p = 0; p < numParts; p++) {
            Log.i(TAG,"Invisible");
            bodyParts[p].setVisibility(View.INVISIBLE);
        }


        for(int i=0;i<currWord.length();i++){
            Log.i("abc","Inside");
            charViews[i] = new TextView(this);
            charViews[i].setText("" +currWord.charAt(i));
            charViews[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            charViews[i].setTextColor(Color.WHITE);
            charViews[i].setBackgroundResource(R.drawable.letter_bg);
            wordLayout.addView(charViews[i]);

        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Log.i(TAG, "Inside onItemCLick");

                if (flag[position] == 1) {
                    view.setEnabled(false);
                    view.setClickable(false);
                    view.setBackgroundResource(R.drawable.letter_up);


                    current_letter = letters[position].charAt(0);
                    for (int i = 0; i < currWord.length(); i++) {
                        if (currWord.charAt(i) == current_letter) {
                            charViews[i].setTextColor(Color.BLACK);
                            current_count++;
                            if (current_count == currWord.length()) {

                                // Toast.makeText(GameActivity.this, "", Toast.LENGTH_LONG).show();
                                AlertDialog.Builder winBuild = new AlertDialog.Builder(GameActivity.this);
                                winBuild.setTitle("You Win");
                                winBuild.setMessage("Wanna Play again pup?");
                                winBuild.setPositiveButton("Play Next",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                gridView.setAdapter(adapter);
                                                GameActivity.this.playGame();
                                            }});

                                winBuild.setNegativeButton("Exit",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                GameActivity.this.finish();
                                            }});

                                winBuild.show();
                            }

                        } else {
                            Log.i("GameActivity", "Inside Else " + no_letter);
                            no_letter++;
                        }

                    }

                    if (no_letter == currWord.length()) {
                        Log.i("GameActivity", "Inside Body Parts " + no_letter);
                        bodyParts[j].setVisibility(View.VISIBLE);
                        j++;

                    }

                    if (j == 6) {
                        //Toast.makeText(GameActivity.this, "Game Over Mate", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder winBuild = new AlertDialog.Builder(GameActivity.this);
                        winBuild.setTitle("You Loose");
                        winBuild.setMessage("Go jerk off! You are worth nothing -" + currWord);
                        winBuild.setPositiveButton("Play Again",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        gridView.setAdapter(adapter);
                                        GameActivity.this.playGame();
                                    }});

                        winBuild.setNegativeButton("Exit",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        GameActivity.this.finish();
                                    }});

                        winBuild.show();

                    }

                    no_letter = 0;

                    flag[position]=0;
                }



            }


        });


    }


}
