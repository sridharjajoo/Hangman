package com.example.sridhar123.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private String[] category_names={"Technology","Game of Thrones","TV Shows"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ListView list = (ListView) findViewById(R.id.category_listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category_names);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if(position==0) {
                    Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("tech",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else if(position==1) {
                    Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("got",2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else if(position==2) {
                    Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("tv",3);
                    intent.putExtras(bundle);
                    startActivity(intent); }
            }
        });

    }





}
