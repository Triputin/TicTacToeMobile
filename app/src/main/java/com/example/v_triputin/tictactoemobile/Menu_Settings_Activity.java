package com.example.v_triputin.tictactoemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Menu_Settings_Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__settings_);
        Intent intent = getIntent();
        ((EditText)findViewById(R.id.gameSize)).setText(Integer.toString(intent.getIntExtra("gameSize",3)));
        findViewById(R.id.buttonBack).setOnClickListener(this);
        ListView listView = (ListView) findViewById(R.id.listViewLanguage);


        // создаем адаптер
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MainActivity.languages);

        // присваиваем адаптер списку
        listView.setAdapter(adapterLanguage);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemsCanFocus(true);
        listView.setItemChecked(intent.getIntExtra("language",0),true);
        //listView.setSelection(intent.getIntExtra("language",0));



        ListView listViewSkin = (ListView) findViewById(R.id.listViewSkin);


        // создаем адаптер
        ArrayAdapter<String> adapterSkin = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MainActivity.skins);

        // присваиваем адаптер списку
        listViewSkin.setAdapter(adapterSkin);
        listViewSkin.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewSkin.setItemsCanFocus(true);
        listViewSkin.setItemChecked(intent.getIntExtra("skin",0),true);
        //listView.setSelection(intent.getIntExtra("language",0));

        ListView listViewGameType = (ListView) findViewById(R.id.listViewGameType);


        // создаем адаптер
        ArrayAdapter<String> adapterGameType = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MainActivity.gameTypeArray);

        // присваиваем адаптер списку
        listViewGameType.setAdapter(adapterGameType);
        listViewGameType.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewGameType.setItemsCanFocus(true);
        listViewGameType.setItemChecked(intent.getIntExtra("gameType",2),true);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Menu_Settings_Activity.class);
                intent.putExtra("gameSize",Integer.parseInt(((EditText)findViewById(R.id.gameSize)).getText().toString()));

        ListView listViewLanguage = (ListView) findViewById(R.id.listViewLanguage);
        intent.putExtra("language",listViewLanguage.getCheckedItemPosition());

        ListView listViewSkin = (ListView) findViewById(R.id.listViewSkin);
        intent.putExtra("skin",listViewSkin.getCheckedItemPosition());

        ListView listViewGameType = (ListView) findViewById(R.id.listViewGameType);
        intent.putExtra("gameType",listViewGameType.getCheckedItemPosition());
                setResult(RESULT_OK, intent);
                finish();

    }
}
