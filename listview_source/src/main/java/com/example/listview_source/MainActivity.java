package com.example.listview_source;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        List<String> names = new ArrayList<>();
        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");
        names.add("4");

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names));

    }
}