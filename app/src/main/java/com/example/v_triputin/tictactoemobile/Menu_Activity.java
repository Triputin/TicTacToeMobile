package com.example.v_triputin.tictactoemobile;


import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class Menu_Activity extends AppCompatActivity implements OnClickListener {
    int gameSize;
    int activeLanguage;
    int activeSkin;
    int gameType;
    int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        findViewById(R.id.buttonContinue).setOnClickListener(this);
        findViewById(R.id.buttonLevelMap).setOnClickListener(this);
        findViewById(R.id.buttonSettings).setOnClickListener(this);
        Intent intent = getIntent();
        gameSize = intent.getIntExtra("gameSize",3);
        activeLanguage = intent.getIntExtra("language",0);
        activeSkin = intent.getIntExtra("skin",0);
        gameType = intent.getIntExtra("gameType",2);
        currentLevel = intent.getIntExtra("CurrentLevel",1);
        setBackGround();
    }

    @Override
    public void onBackPressed() {
        onClick(null);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Menu_Activity.class);
        if(view == null){
            intent.putExtra("gameSize",gameSize);
            intent.putExtra("language",activeLanguage);
            intent.putExtra("skin",activeSkin);
            intent.putExtra("gameType",gameType);
            setResult(RESULT_OK, intent);
            finish();
        }else {
            switch (view.getId()) {
                case R.id.buttonContinue:
                    intent.putExtra("gameSize", gameSize);
                    intent.putExtra("language", activeLanguage);
                    intent.putExtra("skin", activeSkin);
                    intent.putExtra("gameType", gameType);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case R.id.buttonLevelMap:
                    intent = new Intent(this, Menu_LevelMap_Activity.class);
                    intent.putExtra("CurrentLevel", currentLevel);
                    startActivityForResult(intent, 3);
                    break;
                case R.id.buttonSettings:
                    intent = new Intent(this, Menu_Settings_Activity.class);
                    intent.putExtra("gameSize", gameSize);
                    intent.putExtra("language", activeLanguage);
                    intent.putExtra("skin", activeSkin);
                    intent.putExtra("gameType", gameType);
                    startActivityForResult(intent, 2);
                    break;

            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2:
                    gameSize = data.getIntExtra("gameSize", 3);
                    activeLanguage = data.getIntExtra("language", 0);
                    activeSkin = data.getIntExtra("skin", 0);
                    gameType = data.getIntExtra("gameType", 2);
                    break;
                case 3:
                    currentLevel = data.getIntExtra("CurrentLevel",currentLevel);
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result 2", Toast.LENGTH_SHORT).show();
        }
    }
    public void setBackGround() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout1);
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width < height) {
            switch (activeSkin) {
                case 0:
                    constraintLayout.setBackgroundResource(R.drawable.brick01);
                    break;
                case 1:
                    constraintLayout.setBackgroundResource(R.drawable.back_nature01);
                    break;

            }
        }
        else{
            switch (activeSkin) {
                case 0:
                    constraintLayout.setBackgroundResource(R.drawable.brick02);
                    break;
                case 1:
                    constraintLayout.setBackgroundResource(R.drawable.back_nature02);
                    break;

            }

        }
    }

}
