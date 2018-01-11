package com.emily.autocompletetextview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    public  void onCreate(Bundle  savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.auto);
        initAutoComplete("history",autoCompleteTextView);
        Button searchButton=findViewById(R.id.search);
        searchButton.setOnClickListener(new MyOnClickListner());

    }
    private final class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v){
            savehistory("history",autoCompleteTextView);
        }
    }
    private void savehistory(String field,AutoCompleteTextView autoCompleteTextView){
        String text=autoCompleteTextView.getText().toString();
        SharedPreferences sp=getSharedPreferences("network_url",0);
        String longhistory=sp.getString(field,"nothing");
        if(!longhistory.contains(text+",")){
            StringBuilder sb=new StringBuilder(longhistory);
            sb.insert(0,text+",");
            sp.edit().putString("history",sb.toString()).commit();
        }
    }
    private void initAutoComplete(String field,AutoCompleteTextView autoCompleteTextView){
        SharedPreferences sp=getSharedPreferences("network_url",0);
        String longhistory=sp.getString("history","nothing");
        String [] histories=longhistory.split(",");
        ArrayAdapter<String >adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,histories);

        if(histories.length>50){
            String[] newHistories=new String[50];
            System.arraycopy(histories,0,newHistories,0,50);
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,newHistories);

        }
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                if(hasWindowFocus()){
                    view.showDropDown();
                }
            }
        });
    }
}
