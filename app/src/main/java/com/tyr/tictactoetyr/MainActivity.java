package com.tyr.tictactoetyr;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean GameIsActive;
    boolean OptionsIsVisible;
    boolean audioPaused;
    int playerToPlay;
    MediaPlayer mplayer;
    AudioManager audioManager;
    int GameTable[][] = new int[3][3];
    int clickValidTable[] = new int[9];
    ImageView nextPlayerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int MaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVoulme = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volumeControl = (SeekBar)findViewById(R.id.seekBar);
        volumeControl.setMax(MaxVolume);
        volumeControl.setProgress(currVoulme);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //RED is 0
        //Blue is 1
        playerToPlay = 0;
        nextPlayerImage = (ImageView)findViewById(R.id.nextPlayer);
        restartGame();
    }

    public void onRestartClick(View view) {
        restartGame();
    }
    public void onOptionsClick(View view) {
        ConstraintLayout oLayout=(ConstraintLayout)findViewById(R.id.OptionsLayout);
        ConstraintLayout gLayout=(ConstraintLayout)findViewById(R.id.GameTableLayout);
        Button restartButton=(Button)findViewById(R.id.restartButton);
        Button optionsButton=(Button)findViewById(R.id.optionsButton);
        if(!OptionsIsVisible){
            oLayout.setVisibility(View.VISIBLE);
            gLayout.setVisibility(View.INVISIBLE);
            restartButton.setVisibility(View.INVISIBLE);
            OptionsIsVisible = true;
        }else{
            oLayout.setVisibility(View.INVISIBLE);
            gLayout.setVisibility(View.VISIBLE);
            restartButton.setVisibility(View.VISIBLE);
            OptionsIsVisible = false;
        }
    }
    public void onPausePlayClick(View view){
        ImageView pausePlayButton = (ImageView)view;
        if(audioPaused){
            mplayer.start();
            audioPaused=false;
            pausePlayButton.setImageResource(R.drawable.pause);
        } else {
            mplayer.pause();
            audioPaused=true;
            pausePlayButton.setImageResource(R.drawable.play);
        }
    }
    private void restartGame() {
        if(mplayer!=null)
            mplayer.stop();
        mplayer = MediaPlayer.create(this,R.raw.got);
        mplayer.setLooping(true);
        mplayer.start();
        ConstraintLayout gameLayout=findViewById(R.id.GameTableLayout);
        ImageView cellImage;
        GameIsActive = true;
        OptionsIsVisible = false;
        audioPaused = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GameTable[i][j] = -1;
                clickValidTable[i*3+j]=-1;
                cellImage = (ImageView)gameLayout.getChildAt(i*3+j);
                cellImage.setImageResource(0);
            }
            clickValidTable[i] = -1;
        }
    }

        public void onCoinClick(View view){
            int coinClicked = Integer.parseInt(view.getTag().toString().substring(4));
            Log.i("CoinClicked", Integer.toString(coinClicked));
            if (GameIsActive && clickValidTable[coinClicked - 1] == -1) {
                ImageView imageClicked = (ImageView) view;
                if (playerToPlay == 0) {
                    imageClicked.setImageResource(R.drawable.redcircle);
                } else {
                    imageClicked.setImageResource(R.drawable.bluecircle);
                }
                clickValidTable[coinClicked - 1] = playerToPlay;
                if (coinClicked == 1 && GameTable[0][0] == -1) {
                    GameTable[0][0] = playerToPlay;
                    imageClicked.setTranslationX(-1000f);
                    imageClicked.setTranslationY(-1000f);
                    imageClicked.animate().translationXBy(1000f).setDuration(250);
                    imageClicked.animate().translationYBy(1000f).setDuration(250);
                } else if (coinClicked == 2 && GameTable[0][1] == -1) {
                    GameTable[0][1] = playerToPlay;
                    imageClicked.setTranslationY(-1000f);
                    imageClicked.animate().translationYBy(1000f).setDuration(250);
                } else if (coinClicked == 3 && GameTable[0][2] == -1) {
                    GameTable[0][2] = playerToPlay;
                    imageClicked.setTranslationX(1000f);
                    imageClicked.setTranslationY(-1000f);
                    imageClicked.animate().translationXBy(-1000f).setDuration(250);
                    imageClicked.animate().translationYBy(1000f).setDuration(250);
                } else if (coinClicked == 4 && GameTable[1][0] == -1) {
                    GameTable[1][0] = playerToPlay;
                    imageClicked.setTranslationX(-1000f);
                    imageClicked.animate().translationXBy(1000f).setDuration(250);
                } else if (coinClicked == 5 && GameTable[1][1] == -1) {
                    GameTable[1][1] = playerToPlay;
                    imageClicked.animate().alpha(1f).setDuration(250);
                } else if (coinClicked == 6 && GameTable[1][2] == -1) {
                    GameTable[1][2] = playerToPlay;
                    imageClicked.setTranslationX(1000f);
                    imageClicked.animate().translationXBy(-1000f).setDuration(250);
                } else if (coinClicked == 7 && GameTable[2][0] == -1) {
                    GameTable[2][0] = playerToPlay;
                    imageClicked.setTranslationX(-1000f);
                    imageClicked.setTranslationY(1000f);
                    imageClicked.animate().translationXBy(1000f).setDuration(250);
                    imageClicked.animate().translationYBy(-1000f).setDuration(250);
                } else if (coinClicked == 8 && GameTable[2][1] == -1) {
                    GameTable[2][1] = playerToPlay;
                    imageClicked.setTranslationY(1000f);
                    imageClicked.animate().translationYBy(-1000f).setDuration(250);
                } else if (coinClicked == 9 && GameTable[2][2] == -1) {
                    GameTable[2][2] = playerToPlay;
                    imageClicked.setTranslationX(1000f);
                    imageClicked.setTranslationY(1000f);
                    imageClicked.animate().translationXBy(-1000f).setDuration(250);
                    imageClicked.animate().translationYBy(-1000f).setDuration(250);
                }
                for (int i = 0; i < 3; i++) {
                    if (GameTable[0][i] == GameTable[1][i] && GameTable[0][i] == GameTable[2][i] && GameTable[1][i] != -1) {
                        GameIsActive = false;
                    } else if (GameTable[i][0] == GameTable[i][1] && GameTable[i][0] == GameTable[i][2] && GameTable[i][1] != -1) {
                        GameIsActive = false;
                    }
                }
                if (GameTable[0][0] == GameTable[1][1] && GameTable[1][1] == GameTable[2][2] && GameTable[1][1] != -1) {
                    GameIsActive = false;
                }
                if (GameTable[0][2] == GameTable[1][1] && GameTable[1][1] == GameTable[2][0] && GameTable[1][1] != -1) {
                    GameIsActive = false;
                }
                boolean TIE = true;
                for(int i=0;i<9;i++){
                    if(clickValidTable[i]==-1)
                        TIE = false;
                }
                if(TIE && GameIsActive){
                    mplayer.stop();
                    mplayer.setLooping(false);
                    mplayer = MediaPlayer.create(this,R.raw.bell);
                    mplayer.start();
                    Toast.makeText(this, "It is a TIE", Toast.LENGTH_LONG).show();
                }
                if (!GameIsActive) {
                    mplayer.stop();
                    mplayer.setLooping(false);
                    if (playerToPlay == 0) {
                        mplayer = MediaPlayer.create(this, R.raw.clap);
                        Toast.makeText(this, "RED IS THE WINNER", Toast.LENGTH_LONG).show();
                    } else {
                        mplayer = MediaPlayer.create(this, R.raw.clap);
                        Toast.makeText(this, "BLUE IS THE WINNER", Toast.LENGTH_LONG).show();
                    }
                    mplayer.start();
                }
                if (playerToPlay == 0) {
                    playerToPlay = 1;
                    nextPlayerImage.setImageResource(R.drawable.bluecircle);
                } else {
                    playerToPlay = 0;
                    nextPlayerImage.setImageResource(R.drawable.redcircle);
                }
            }
        }
    }