package com.example.v_triputin.tictactoemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Menu_Settings_Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__settings_);
        Intent intent = getIntent();
        ((EditText)findViewById(R.id.gameSize)).setText(Integer.toString(intent.getIntExtra("gameSize",3)));
        findViewById(R.id.buttonBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Menu_Settings_Activity.class);
                intent.putExtra("gameSize",Integer.parseInt(((EditText)findViewById(R.id.gameSize)).getText().toString()));
                setResult(RESULT_OK, intent);
                finish();

    }
}
