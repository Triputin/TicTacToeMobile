package com.example.v_triputin.tictactoemobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class Menu_Settings_Activity extends AppCompatActivity implements View.OnClickListener{
    int activeSkin;

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
                android.R.layout.simple_list_item_activated_1, MainActivity.languages){



            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);


                return view;
            }
        };


        // присваиваем адаптер списку
        listView.setAdapter(adapterLanguage);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemsCanFocus(true);
        listView.setItemChecked(intent.getIntExtra("language",0),true);
        //listView.setItemChecked(0,true);
        //listView.setSelection(intent.getIntExtra("language",0));



        ListView listViewSkin = (ListView) findViewById(R.id.listViewSkin);


        // создаем адаптер
        ArrayAdapter<String> adapterSkin = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, MainActivity.skins){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        // присваиваем адаптер списку
        listViewSkin.setAdapter(adapterSkin);
        listViewSkin.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewSkin.setItemsCanFocus(true);
        activeSkin = intent.getIntExtra("skin",0);
        listViewSkin.setItemChecked(activeSkin,true);

        //listView.setSelection(intent.getIntExtra("language",0));

        ListView listViewGameType = (ListView) findViewById(R.id.listViewGameType);


        // создаем адаптер
        String[] strings;
        if(intent.getIntExtra("language",0)==0){
            strings= MainActivity.gameTypeArrayEn;
        }else{
            strings= MainActivity.gameTypeArrayRu;
        }

        ArrayAdapter<String> adapterGameType = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, strings){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        // присваиваем адаптер списку
        listViewGameType.setAdapter(adapterGameType);
        listViewGameType.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewGameType.setItemsCanFocus(true);
        listViewGameType.setItemChecked(intent.getIntExtra("gameType",2),true);
        setBackGround();
    }

    @Override
    public void onBackPressed() {
       onClick(null);
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

    public void setBackGround() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout2);
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
