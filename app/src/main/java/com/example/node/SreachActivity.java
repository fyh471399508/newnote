package com.example.node;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SreachActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText sreachEdit;
    private Button buttton_sreach;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sreach);
        init();

    }

    public void init() {
        db = DataBaseUtil.returnDataBase(this);

        sreachEdit = findViewById(R.id.sreacch);
        buttton_sreach = findViewById(R.id.button_sreach);
        buttton_sreach.setOnClickListener(this);

    }

    // String sql = "create table nodepad(_id integer primary key autoincrement," + "title varchar(50),"+"content varchar(500),"+"date varchar(50),"+"type integer)";

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_sreach:
                String sreachEditString = sreachEdit.getText().toString();
                String sql= "select * from nodepad where title="+"'"+sreachEditString+"'" ;
                Cursor cursor = db.rawQuery(sql, null);
                cursor.moveToFirst();
                if(cursor.getCount()!=0){
                    Intent intent=new Intent(SreachActivity.this,DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                    Log.d("zhao", cursor.getString(cursor.getColumnIndex("title")));
                    bundle.putString("title",cursor.getString(cursor.getColumnIndex("title")));
                    bundle.putString("content",cursor.getString(cursor.getColumnIndex("content")));
                    bundle.putInt("type",Integer.parseInt(cursor.getString(cursor.getColumnIndex("type"))));
                    bundle.putString("date",cursor.getString(cursor.getColumnIndex("date")));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "查询笔记题目为空,请从新输入", Toast.LENGTH_LONG).show();
                    sreachEdit.setText("");
                }break;


        }
    }
}
