package com.example.v_triputin.tictactoemobile;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class Menu_Activity extends AppCompatActivity implements OnClickListener {
    int gameSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        findViewById(R.id.buttonContinue).setOnClickListener(this);
        findViewById(R.id.buttonLevelMap).setOnClickListener(this);
        findViewById(R.id.buttonSettings).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);
        Intent intent = getIntent();
        gameSize = intent.getIntExtra("gameSize",3);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Menu_Activity.class);
        switch (view.getId()) {
            case R.id.buttonContinue:
                intent.putExtra("gameSize",gameSize);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.buttonLevelMap:
                break;
            case R.id.buttonSettings:
                 intent = new Intent(this, Menu_Settings_Activity.class);
                 intent.putExtra("gameSize",gameSize);
                startActivityForResult(intent, 2);
                break;
            case R.id.buttonExit:
                break;

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
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result 2", Toast.LENGTH_SHORT).show();
        }
    }
}
