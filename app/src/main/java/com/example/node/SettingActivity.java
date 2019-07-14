package com.example.node;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SettingActivity extends Activity implements View.OnClickListener{

    LinearLayout writeLayout;
    LinearLayout findLayout;
    LinearLayout typeLayout;
    LinearLayout voiceLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    public void init(){
        writeLayout = findViewById(R.id.write);
        findLayout = findViewById(R.id.find);
        typeLayout = findViewById(R.id.type);
        voiceLayout = findViewById(R.id.voice);

        writeLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        typeLayout.setOnClickListener(this);
        voiceLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write:
                startActivity(new Intent(this, EditActivity.class));
                break;
            case R.id.find:
                startActivity(new Intent(this, SreachActivity.class));break;
            case R.id.type:
                startActivity(new Intent(this, NodeTypeActivity.class));break;
            case R.id.voice:
                startActivity(new Intent(this, VoiceActivity.class));break;
        }
    }
}
