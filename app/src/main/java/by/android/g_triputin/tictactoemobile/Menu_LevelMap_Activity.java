package by.android.g_triputin.tictactoemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.android.g_triputin.tictactoemobile.R;


public class Menu_LevelMap_Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_map);
        findViewById(R.id.buttonLevelMap_Back).setOnClickListener(this);
        Intent intent = getIntent();
        ((TextView)findViewById(R.id.textViewLevelMap)).setText(Integer.toString(intent.getIntExtra("CurrentLevel",1)+1)+" / "+Integer.toString(MainActivity.levelCount));
    }

    @Override
    public void onBackPressed() {
        onClick(null);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Menu_LevelMap_Activity.class);
        setResult(RESULT_OK, intent);

        finish();

    }
}
