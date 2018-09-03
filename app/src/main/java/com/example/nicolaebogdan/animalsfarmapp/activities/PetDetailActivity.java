package com.example.nicolaebogdan.animalsfarmapp.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nicolaebogdan.animalsfarmapp.Action;
import com.example.nicolaebogdan.animalsfarmapp.Chicken;
import com.example.nicolaebogdan.animalsfarmapp.Constants;
import com.example.nicolaebogdan.animalsfarmapp.DataProvider;
import com.example.nicolaebogdan.animalsfarmapp.PriceManager;
import com.example.nicolaebogdan.animalsfarmapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class PetDetailActivity extends AppCompatActivity {

    public static final String TAG = PetDetailActivity.class.getName();


    TextView petTitle;
    ImageView petImage;
    static TextView coinTextView;
    ProgressBar happinessProgressBar;
    ProgressBar sleepProgressBar;
    Button sleepButton;
    ProgressBar drinkProgressBar;
    Button drinkButton;
    Button collectButton;
    Button eatButton;
    Button playButton;
    ProgressBar eatProgressBar;
    ProgressBar playProgressBar;
    Button reviveMe;

    int startValAction = 0;
    int finishValAction = 100;

    int startHappinessBarValue = 100;
    int finishHappinessBarValue = 0;

    ValueAnimator valueAnimator;

    int sleepProgressStatus;
    int drinkProgressStatus;
    int happinessProgressStatus;
    int eatProgressStatus;
    int playProgressStatus;


    int listPosition;

    int earnCoin = 0;

    boolean isAction;
    boolean isDead;

    SharedPreferences pref;
    SharedPreferences myPrefs;


    DataProvider dh;

    SharedPreferences preferences;

    Map<String,List<DataProvider>> pets;

    List<DataProvider> lst;



    ValueAnimator valueAnimatorHappiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_pet_detail);
        petTitle = findViewById(R.id.dialog_detail_text_view);
        petImage = findViewById(R.id.pet_photo);
        coinTextView = findViewById(R.id.coin_detail_pet);
        happinessProgressBar = findViewById(R.id.progressBar);
        sleepProgressBar = findViewById(R.id.progressBar_Sleep);
        sleepButton = findViewById(R.id.sleep_button);
        drinkButton = findViewById(R.id.Drink_button);
        drinkProgressBar = findViewById(R.id.progressBar_Drink);
        collectButton = findViewById(R.id.collect_button);
        eatButton = findViewById(R.id.Eat_button);
        playButton = findViewById(R.id.Play_button);
        eatProgressBar = findViewById(R.id.progressBar_Eat);
        playProgressBar = findViewById(R.id.progressBar_Play);
        reviveMe = findViewById(R.id.revive_pet_button);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        pref = getSharedPreferences(Constants.PREFNAMECOIN, MODE_PRIVATE);

        myPrefs = getSharedPreferences(Constants.PREFMAP,MODE_PRIVATE);

        if(myPrefs.contains(Constants.PETSMAPTOSTRING)) {

            pets = loadMap(Constants.PETSMAPTOSTRING,this);
        }


        collectButton.setText("COLLECT  (" + earnCoin +")");
        coinTextView.setText(String.valueOf(MainActivity.Coin));

        dh = (DataProvider) getIntent().getSerializableExtra(Constants.ANIMALOBJECT);

        Bundle extras = getIntent().getExtras();
        listPosition = extras.getInt(Constants.LISTPOSITION);

        Log.d(TAG, "position: " + listPosition);


        happinessProgressStatus = dh.getaHappiness();

        eatProgressStatus = 0;
        sleepProgressStatus = 0;
        drinkProgressStatus = 0;
        playProgressStatus = 0;

        isAction = dh.getisAction();
        isDead = dh.getisDead();


        Log.d(TAG, "onPauseafter: " + sleepProgressStatus);

        lst = pets.get(dh.getaPetName());


        petTitle.setText(dh.getEachAnimalName());
        if(isDead){
            petImage.setImageResource(R.drawable.poison);
            happinessProgressBar.setProgress(finishHappinessBarValue);
            DisableButton();
            reviveMe.setVisibility(View.VISIBLE);
        } else {
            petImage.setImageResource(dh.getaImage());
//            startHappinessBar();
            EnabledButton();
            reviveMe.setVisibility(View.INVISIBLE);
        }


        reviveMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.Coin < Constants.REVIVEPET) {
                    Toast.makeText(PetDetailActivity.this,"You don't have enougth money to revive your pet.",Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.Coin = MainActivity.Coin - Constants.REVIVEPET;
                    changeImageLife();
                    startHappinessBar();
                    EnabledButton();
                    coinTextView.setText(String.valueOf(MainActivity.Coin));
                    pref.edit().putInt(Constants.PREFCOIN, MainActivity.Coin).apply();
                }
            }
        });




        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                valueAnimator = ValueAnimator.ofInt(startValAction, finishValAction);
                valueAnimator.setDuration(Constants.SLEEPDURATION);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {


                       sleepProgressStatus = (Integer) animation.getAnimatedValue();


                       sleepProgressBar.setProgress(sleepProgressStatus);

                    }
                });


                //it's start time for sleepProgressBar

               valueAnimator.addListener(new Animator.AnimatorListener() {
                   @Override
                   public void onAnimationStart(Animator animation) {
                       DisableButton();
                       dh.setlActionStart(System.currentTimeMillis());
                       dh.seteAction(Action.SLEEP);
                       dh.setAction(true);
                   }

                   @Override
                   public void onAnimationEnd(Animator animation) {
                       Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName()+ " " + "sleeps" + " "+ Constants.SLEEPDURATION / 1000 + " " + "seconds", Toast.LENGTH_SHORT).show();
                       earnCoin = earnCoin + Constants.SLEEPMONEY;

                       sleepProgressBar.setProgress(startValAction);
                       dh.setAction(false);
                       collectButton.setText("COLLECT  (" + earnCoin +")");
                       EnabledButton();
                       valueAnimatorHappiness.cancel();
                       valueAnimatorHappiness.removeAllUpdateListeners();
                       int newStartValue = ((happinessProgressStatus + 20) > 100) ? 100 : (happinessProgressStatus + 20);
                       valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                       valueAnimatorHappiness.setDuration(newStartValue * 1000);

                       valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                           @Override
                           public void onAnimationUpdate(ValueAnimator animation) {

                               happinessProgressStatus = (Integer) animation.getAnimatedValue();

                               if(happinessProgressStatus == 0){

                                   Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                   DisableButton();
                                   changeDeadImage();

                               }else {

                                   happinessProgressBar.setProgress(happinessProgressStatus);
                               }

                           }
                       });

                       valueAnimatorHappiness.start();
                       dh.setHappinessActionStart(System.currentTimeMillis());

                   }

                   @Override
                   public void onAnimationCancel(Animator animation) {

                   }

                   @Override
                   public void onAnimationRepeat(Animator animation) {

                   }
               });

                valueAnimator.start();
            }
        });


        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueAnimator = ValueAnimator.ofInt(startValAction, finishValAction);
                valueAnimator.setDuration(Constants.DRINKDURATION);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {


                        drinkProgressStatus = (Integer) animation.getAnimatedValue();

                        drinkProgressBar.setProgress(drinkProgressStatus);

                    }
                });


                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        DisableButton();
                        dh.setlActionStart(System.currentTimeMillis());
                        dh.seteAction(Action.DRINK);
                        dh.setAction(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Toast.makeText(PetDetailActivity.this, "Drink Done!", Toast.LENGTH_SHORT).show();
                        earnCoin = earnCoin + Constants.DRINKMONEY;
                        drinkProgressBar.setProgress(startValAction);
                        collectButton.setText("COLLECT  (" + earnCoin +")");
                        dh.setAction(false);
                        EnabledButton();
                        valueAnimatorHappiness.cancel();
                        valueAnimatorHappiness.removeAllUpdateListeners();
                        int newStartValue = ((happinessProgressStatus + 10) > 100) ? 100 : (happinessProgressStatus + 10);
                        valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                        valueAnimatorHappiness.setDuration(newStartValue * 1000);

                        valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                if(happinessProgressStatus == 0){

                                    Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                    DisableButton();
                                    changeDeadImage();
                                }
                                else {
                                    happinessProgressBar.setProgress(happinessProgressStatus);
                                }

                            }
                        });

                        valueAnimatorHappiness.start();
                        dh.setHappinessActionStart(System.currentTimeMillis());

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
            }
        });

        eatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueAnimator = ValueAnimator.ofInt(startValAction,finishValAction);
                valueAnimator.setDuration(Constants.EATDURATION);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {


                        eatProgressStatus = (Integer) animation.getAnimatedValue();
                        eatProgressBar.setProgress(eatProgressStatus);


                    }
                });


                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        DisableButton();
                        dh.setlActionStart(System.currentTimeMillis());
                        dh.seteAction(Action.EAT);
                        dh.setAction(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        earnCoin = earnCoin + Constants.EATMONEY;
                        eatProgressBar.setProgress(startValAction);
                        collectButton.setText("COLLECT  (" + earnCoin +")");
                        dh.setAction(false);
                        EnabledButton();
                        valueAnimatorHappiness.cancel();
                        valueAnimatorHappiness.removeAllUpdateListeners();

                        int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                        valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                        valueAnimatorHappiness.setDuration(newStartValue * 1000);
                        valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                if(happinessProgressStatus == 0){

                                    Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                    DisableButton();
                                    changeDeadImage();
                                }
                                else {
                                    happinessProgressBar.setProgress(happinessProgressStatus);
                                }

                            }
                        });

                        valueAnimatorHappiness.start();
                        dh.setHappinessActionStart(System.currentTimeMillis());
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueAnimator = ValueAnimator.ofInt(startValAction,finishValAction);
                valueAnimator.setDuration(Constants.PLAYDURATION);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {


                        playProgressStatus = (Integer) animation.getAnimatedValue();
                        playProgressBar.setProgress(playProgressStatus);


                    }
                });

                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        DisableButton();
                        dh.setlActionStart(System.currentTimeMillis());
                        dh.seteAction(Action.PLAY);
                        dh.setAction(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName() + " " + "ends Play", Toast.LENGTH_SHORT).show();
                        earnCoin = earnCoin + Constants.PLAYMONEY;
                        playProgressBar.setProgress(startValAction);
                        collectButton.setText("COLLECT  (" + earnCoin +")");
                        dh.setAction(false);
                        EnabledButton();
                        valueAnimatorHappiness.cancel();
                        valueAnimatorHappiness.removeAllUpdateListeners();

                        int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                        valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                        valueAnimatorHappiness.setDuration(newStartValue * 1000);

                        valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                if(happinessProgressStatus == 0) {

                                    Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                    DisableButton();
                                    changeDeadImage();

                                }
                                else {
                                    happinessProgressBar.setProgress(happinessProgressStatus);
                                }

                            }
                        });


                        valueAnimatorHappiness.start();
                        dh.setHappinessActionStart(System.currentTimeMillis());

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
            }
        });

        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Coin  = MainActivity.Coin + earnCoin;
                coinTextView.setText(String.valueOf(MainActivity.Coin));
                Toast.makeText(PetDetailActivity.this,"You recive" + " " + earnCoin + " " + "Coins",Toast.LENGTH_LONG).show();
                pref.edit().putInt(Constants.PREFCOIN, MainActivity.Coin).apply();
                earnCoin = 0;
                collectButton.setText("COLLECT  (" + earnCoin +")");

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        dh = (DataProvider) getIntent().getSerializableExtra(Constants.ANIMALOBJECT);

        if(valueAnimator != null) {
            valueAnimator.pause();
        }
        if(valueAnimatorHappiness != null) {
            valueAnimatorHappiness.pause();
        }

        lst.set(listPosition,dh);
        pets.put(dh.getaPetName(),lst);



        saveMap(this,Constants.PETSMAPTOSTRING,pets);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dh = (DataProvider) getIntent().getSerializableExtra(Constants.ANIMALOBJECT);

        long happinessStatus = (System.currentTimeMillis() - dh.getHappinessActionStart());

        if(happinessStatus >= Constants.HAPPINESSDURATION){

            Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
            DisableButton();
            changeDeadImage();


        } else {

            int happinessProgress = (int) (((Constants.HAPPINESSDURATION - happinessStatus) * startHappinessBarValue) / Constants.HAPPINESSDURATION);
            Log.d("PetDetail", "Happiness status: " + happinessStatus + " , hapiness progress: " + happinessProgress);
            valueAnimatorHappiness = ValueAnimator.ofInt(happinessProgress , finishHappinessBarValue);
            valueAnimatorHappiness.setDuration(Constants.HAPPINESSDURATION - happinessStatus);
                valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {



                    happinessProgressStatus = (Integer) animation.getAnimatedValue();
                    if(happinessProgressStatus == 0){

                        Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                        DisableButton();
                        changeDeadImage();

                    }else {
                        happinessProgressBar.setProgress(happinessProgressStatus);
                    }
                }
            });

            valueAnimatorHappiness.start();

        }


        if(isAction) {
           if(dh.geteAction() == Action.SLEEP) {

               final long sleepStatus = (System.currentTimeMillis() - dh.getlActionStart());

               if (sleepStatus >= Constants.SLEEPDURATION) {

                   Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName()+ " " + "sleeps" + " "+ Constants.SLEEPDURATION / 1000 + " " + "seconds", Toast.LENGTH_SHORT).show();
                   earnCoin = earnCoin + Constants.SLEEPMONEY;

                   sleepProgressBar.setProgress(startValAction);
                   dh.setAction(false);
                   collectButton.setText("COLLECT  (" + earnCoin +")");
                   EnabledButton();
                   valueAnimatorHappiness.cancel();
                   valueAnimatorHappiness.removeAllUpdateListeners();
                  // int happinessProgress = (int) (((Constants.HAPPINESSDURATION - happinessStatus) * startHappinessBarValue) / Constants.HAPPINESSDURATION);
                   int newStartValue = ((happinessProgressStatus + 20) > 100) ? 100 : (happinessProgressStatus + 20);
                   valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                   valueAnimatorHappiness.setDuration(newStartValue * 100);

                   valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                       @Override
                       public void onAnimationUpdate(ValueAnimator animation) {

                           happinessProgressStatus = (Integer) animation.getAnimatedValue();

                           if(happinessProgressStatus == 0){

                               Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                               DisableButton();
                               changeDeadImage();

                           }else {

                               happinessProgressBar.setProgress(happinessProgressStatus);
                           }

                       }
                   });

                   valueAnimatorHappiness.start();
                   dh.setHappinessActionStart(System.currentTimeMillis());

               } else {
                   int sleepProgress = (int) ((finishValAction * sleepStatus) / Constants.SLEEPDURATION);
                   DisableButton();
                   valueAnimator = ValueAnimator.ofInt(sleepProgress, finishValAction);
                   valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                       @Override
                       public void onAnimationUpdate(ValueAnimator animation) {

                           animation.setDuration(Constants.SLEEPDURATION - sleepStatus);

                           sleepProgressStatus = (Integer) animation.getAnimatedValue();
                           sleepProgressBar.setProgress(sleepProgressStatus);

                       }
                   });


                   valueAnimator.addListener(new Animator.AnimatorListener() {
                       @Override
                       public void onAnimationStart(Animator animator) {

                       }

                       @Override
                       public void onAnimationEnd(Animator animator) {

                           Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName()+ " " + "sleeps" + " "+ Constants.SLEEPDURATION / 1000 + " " + "seconds", Toast.LENGTH_SHORT).show();
                           earnCoin = earnCoin + Constants.SLEEPMONEY;

                           sleepProgressBar.setProgress(startValAction);
                           dh.setAction(false);
                           collectButton.setText("COLLECT  (" + earnCoin +")");
                           EnabledButton();
                           valueAnimatorHappiness.cancel();
                           valueAnimatorHappiness.removeAllUpdateListeners();
                           int newStartValue = ((happinessProgressStatus + 20) > 100) ? 100 : (happinessProgressStatus + 20);
                           valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                           valueAnimatorHappiness.setDuration(newStartValue * 1000);

                           valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                               @Override
                               public void onAnimationUpdate(ValueAnimator animation) {

                                   happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                   if(happinessProgressStatus == 0){

                                       Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                       DisableButton();
                                       changeDeadImage();

                                   }else {

                                       happinessProgressBar.setProgress(happinessProgressStatus);
                                   }

                               }
                           });

                           valueAnimatorHappiness.start();
                           dh.setHappinessActionStart(System.currentTimeMillis());

                       }

                       @Override
                       public void onAnimationCancel(Animator animator) {

                       }

                       @Override
                       public void onAnimationRepeat(Animator animator) {

                       }
                   });
                   valueAnimator.start();
               }
           }


            if(dh.geteAction() == Action.DRINK) {

                final long drinkStatus = (System.currentTimeMillis() - dh.getlActionStart());

                if (drinkStatus >= Constants.DRINKDURATION) {
                    Toast.makeText(PetDetailActivity.this, "Drink Done!", Toast.LENGTH_SHORT).show();
                    earnCoin = earnCoin + Constants.DRINKMONEY;
                    drinkProgressBar.setProgress(startValAction);
                    collectButton.setText("COLLECT  (" + earnCoin +")");
                    dh.setAction(false);
                    EnabledButton();
                    valueAnimatorHappiness.cancel();
                    valueAnimatorHappiness.removeAllUpdateListeners();
                    int newStartValue = ((happinessProgressStatus + 10) > 100) ? 100 : (happinessProgressStatus + 10);
                    valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                    valueAnimatorHappiness.setDuration(newStartValue * 1000);

                    valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            happinessProgressStatus = (Integer) animation.getAnimatedValue();

                            if(happinessProgressStatus == 0){

                                Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                DisableButton();
                                changeDeadImage();
                            }
                            else {
                                happinessProgressBar.setProgress(happinessProgressStatus);
                            }

                        }
                    });

                    valueAnimatorHappiness.start();
                    dh.setHappinessActionStart(System.currentTimeMillis());

                } else {

                    int drinkProgress = (int) ((finishValAction * drinkStatus) / Constants.DRINKDURATION);
                    DisableButton();
                    valueAnimator = ValueAnimator.ofInt(drinkProgress, finishValAction);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            animation.setDuration(Constants.DRINKDURATION - drinkStatus);

                            drinkProgressStatus = (Integer) animation.getAnimatedValue();
                            drinkProgressBar.setProgress(drinkProgressStatus);
                        }
                    });


                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Toast.makeText(PetDetailActivity.this, "Drink Done!", Toast.LENGTH_SHORT).show();
                            earnCoin = earnCoin + Constants.DRINKMONEY;
                            drinkProgressBar.setProgress(startValAction);
                            collectButton.setText("COLLECT  (" + earnCoin +")");
                            dh.setAction(false);
                            EnabledButton();
                            valueAnimatorHappiness.cancel();
                            valueAnimatorHappiness.removeAllUpdateListeners();
                            int newStartValue = ((happinessProgressStatus + 10) > 100) ? 100 : (happinessProgressStatus + 10);
                            valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                            valueAnimatorHappiness.setDuration(newStartValue * 1000);

                            valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {

                                    happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                    if(happinessProgressStatus == 0){

                                        Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                        DisableButton();
                                        changeDeadImage();
                                    }
                                    else {
                                        happinessProgressBar.setProgress(happinessProgressStatus);
                                    }

                                }
                            });

                            valueAnimatorHappiness.start();
                            dh.setHappinessActionStart(System.currentTimeMillis());

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    valueAnimator.start();
                }

            }

            if(dh.geteAction() == Action.EAT) {

                final long eatStatus = (System.currentTimeMillis() - dh.getlActionStart());

                if (eatStatus >= Constants.EATDURATION) {
                    Toast.makeText(PetDetailActivity.this, "Eat Done!", Toast.LENGTH_SHORT).show();
                    earnCoin = earnCoin + Constants.EATMONEY;
                    eatProgressBar.setProgress(startValAction);
                    collectButton.setText("COLLECT  (" + earnCoin +")");
                    dh.setAction(false);
                    EnabledButton();
                    valueAnimatorHappiness.cancel();
                    valueAnimatorHappiness.removeAllUpdateListeners();

                    int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                    valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                    valueAnimatorHappiness.setDuration(newStartValue * 1000);
                    valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            happinessProgressStatus = (Integer) animation.getAnimatedValue();

                            if(happinessProgressStatus == 0){

                                Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                DisableButton();
                                changeDeadImage();
                            }
                            else {
                                happinessProgressBar.setProgress(happinessProgressStatus);
                            }

                        }
                    });

                    valueAnimatorHappiness.start();
                    dh.setHappinessActionStart(System.currentTimeMillis());

                } else {

                    int eatProgress = (int) ((finishValAction * eatStatus) / Constants.EATDURATION);
                    DisableButton();
                    valueAnimator = ValueAnimator.ofInt(eatProgress, finishValAction);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            animation.setDuration(Constants.EATDURATION - eatStatus);

                            eatProgressStatus = (Integer) animation.getAnimatedValue();
                            eatProgressBar.setProgress(eatProgressStatus);
                        }
                    });


                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                            Toast.makeText(PetDetailActivity.this, "Eat Done!", Toast.LENGTH_SHORT).show();
                            earnCoin = earnCoin + Constants.EATMONEY;
                            eatProgressBar.setProgress(startValAction);
                            collectButton.setText("COLLECT  (" + earnCoin +")");
                            dh.setAction(false);
                            EnabledButton();
                            valueAnimatorHappiness.cancel();
                            valueAnimatorHappiness.removeAllUpdateListeners();

                            int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                            valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                            valueAnimatorHappiness.setDuration(newStartValue * 1000);
                            valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {

                                    happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                    if(happinessProgressStatus == 0){

                                        Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                        DisableButton();
                                        changeDeadImage();
                                    }
                                    else {
                                        happinessProgressBar.setProgress(happinessProgressStatus);
                                    }

                                }
                            });

                            valueAnimatorHappiness.start();
                            dh.setHappinessActionStart(System.currentTimeMillis());

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    valueAnimator.start();
                }

            }

            if(dh.geteAction() == Action.PLAY) {

                final long playStatus = (System.currentTimeMillis() - dh.getlActionStart());

                if (playStatus >= Constants.PLAYDURATION) {
                    Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName() + " " + "ends Play", Toast.LENGTH_SHORT).show();
                    earnCoin = earnCoin + Constants.PLAYMONEY;
                    playProgressBar.setProgress(startValAction);
                    collectButton.setText("COLLECT  (" + earnCoin +")");
                    dh.setAction(false);
                    EnabledButton();
                    valueAnimatorHappiness.cancel();
                    valueAnimatorHappiness.removeAllUpdateListeners();

                    int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                    valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                    valueAnimatorHappiness.setDuration(newStartValue * 10000);

                    valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            happinessProgressStatus = (Integer) animation.getAnimatedValue();

                            if(happinessProgressStatus == 0) {

                                Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                DisableButton();
                                changeDeadImage();

                            }
                            else {
                                happinessProgressBar.setProgress(happinessProgressStatus);
                            }

                        }
                    });


                    valueAnimatorHappiness.start();
                    dh.setHappinessActionStart(System.currentTimeMillis());

                } else {

                    int playProgress = (int) ((finishValAction * playStatus) / Constants.PLAYDURATION);
                    DisableButton();
                    valueAnimator = ValueAnimator.ofInt(playProgress, finishValAction);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            animation.setDuration(Constants.PLAYDURATION - playStatus);

                            playProgressStatus = (Integer) animation.getAnimatedValue();
                            playProgressBar.setProgress(playProgressStatus);

                        }
                    });


                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Toast.makeText(PetDetailActivity.this, dh.getEachAnimalName() + " " + "ends Play", Toast.LENGTH_SHORT).show();
                            earnCoin = earnCoin + Constants.PLAYMONEY;
                            playProgressBar.setProgress(startValAction);
                            collectButton.setText("COLLECT  (" + earnCoin +")");
                            dh.setAction(false);
                            EnabledButton();
                            valueAnimatorHappiness.cancel();
                            valueAnimatorHappiness.removeAllUpdateListeners();

                            int newStartValue = ((happinessProgressStatus + 15) > 100) ? 100 : (happinessProgressStatus + 15);
                            valueAnimatorHappiness = ValueAnimator.ofInt(newStartValue, finishHappinessBarValue);
                            valueAnimatorHappiness.setDuration(newStartValue * 1000);

                            valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {

                                    happinessProgressStatus = (Integer) animation.getAnimatedValue();

                                    if(happinessProgressStatus == 0) {

                                        Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                                        DisableButton();
                                        changeDeadImage();

                                    }
                                    else {
                                        happinessProgressBar.setProgress(happinessProgressStatus);
                                    }

                                }
                            });


                            valueAnimatorHappiness.start();
                            dh.setHappinessActionStart(System.currentTimeMillis());

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    valueAnimator.start();
                }
            }
        }
    }

    private void EnabledButton(){
        sleepButton.setEnabled(true);
        drinkButton.setEnabled(true);
        eatButton.setEnabled(true);
        playButton.setEnabled(true);
    }



    private void DisableButton(){
        sleepButton.setEnabled(false);
        drinkButton.setEnabled(false);
        eatButton.setEnabled(false);
        playButton.setEnabled(false);
    }


    private void startHappinessBar() {

        valueAnimatorHappiness = ValueAnimator.ofInt(startHappinessBarValue,finishHappinessBarValue);
        valueAnimatorHappiness.setDuration(Constants.HAPPINESSDURATION);
        valueAnimatorHappiness.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                happinessProgressStatus = (Integer) animation.getAnimatedValue();
                happinessProgressBar.setProgress(happinessProgressStatus);


                if(happinessProgressStatus == 0){

                    Toast.makeText(PetDetailActivity.this,dh.getEachAnimalName() + " is dead.",Toast.LENGTH_LONG).show();
                    DisableButton();
                    changeDeadImage();

                }else {

                    happinessProgressBar.setProgress(happinessProgressStatus);
                }
            }
        });

        valueAnimatorHappiness.start();
        dh.setHappinessActionStart(System.currentTimeMillis());
    }

    private void changeDeadImage(){
        petImage.setImageResource(R.drawable.poison);
        dh.setDead(true);
        reviveMe.setVisibility(View.VISIBLE);
    }

    private void changeImageLife(){
        petImage.setImageResource(dh.getaImage());
        dh.setDead(false);
        reviveMe.setVisibility(View.INVISIBLE);
    }


    public static Map<String,List<DataProvider>> loadMap(String key,Context context){
        Map<String,List<DataProvider>> outputMap = new HashMap<String, List<DataProvider>>();
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.PREFMAP, Context.MODE_PRIVATE);
        try{
            //get from shared prefs
            String storedHashMapString = pSharedPref.getString(key, (new JSONObject()).toString());
            java.lang.reflect.Type type = new TypeToken<HashMap<String, List<DataProvider>>>(){}.getType();
            Gson gson = new Gson();
            return  gson.fromJson(storedHashMapString, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }



    //To save Hash Map
    public static void saveMap(Context context,String key, Map<String,List<DataProvider>> inputMap){
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.PREFMAP, Context.MODE_PRIVATE);
        if (pSharedPref != null){
            Gson gson = new Gson();
            String hashMapString = gson.toJson(inputMap);
            Log.d(TAG, "saveMap: " + hashMapString);
            //save in shared prefs
            pSharedPref.edit().putString(key, hashMapString).apply();
        }
    }


}

