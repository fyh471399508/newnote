package com.example.node;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class DetailActivity  extends Activity implements View.OnClickListener{

    public Node node;
    public Button change;
    public Button delete;
    public EditText titleText;
    public EditText contentText;
    public TextView typeText;
    public TextView dataText;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        node=new Node();
        node.setNodeTitle(bundle.getString("title"));
        node.setNodeContent(bundle.getString("content"));
        node.setNodeClass(bundle.getInt("type", 0));
        node.setNodeID(bundle.getInt("id", 0));
        node.setSaveDate(bundle.getString("date"));

        init();
        setType();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent
    }


    public void init(){
        change = findViewById(R.id.change_node);
        delete = findViewById(R.id.delete_node);
        titleText = findViewById(R.id.detail_node_title);
        contentText = findViewById(R.id.detail_node_content);
        typeText = findViewById(R.id.detail_type);
        dataText = findViewById(R.id.detail_date);

        change.setOnClickListener(this);
        delete.setOnClickListener(this);

        titleText.setText(node.getNodeTitle());
        contentText.setText(node.getNodeContent());
        dataText.setText(node.getSaveDate());

        this.db = DataBaseUtil.returnDataBase(this);
    }


    public void setType(){
        switch (node.getNodeClass()) {
            case 1:
                typeText.setText("通知");
                typeText.setTextColor(Color.parseColor("#FFB343"));
                typeText.setBackgroundResource(R.drawable.notictextback);break;
            case 2:
                typeText.setText("日记");
                typeText.setTextColor(Color.parseColor("#8086FF"));
                typeText.setBackgroundResource(R.drawable.diarytextback);break;
            case 3:
                typeText.setText("博客");
                typeText.setTextColor(Color.parseColor("#FE6B64"));
                typeText.setBackgroundResource(R.drawable.blogtextback);break;
            case 4:
                typeText.setText("工作");
                typeText.setTextColor(Color.parseColor("#45CDE5"));
                typeText.setBackgroundResource(R.drawable.jobtextback);break;
        }
    }
    // String sql = "create table nodepad(_id integer primary key autoincrement," + "title varchar(50),"+"content varchar(500),"+"date varchar(50),"+"type integer)";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_node:
                String cdeleteSql="delete from nodepad where _id="+"'"+node.getNodeID()+"'";
                db.execSQL(cdeleteSql);

                String title = titleText.getText().toString();
                String content = contentText.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date(System.currentTimeMillis());
                String date = simpleDateFormat.format(currentDate);

                String insertSql="insert into nodepad(title,content,date,type)values(?,?,?,?)";

                db.execSQL(insertSql,new String[]{title,content,date,node.getNodeClass()+""});
                Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.delete_node:
                String deleteSql="delete from nodepad where _id="+"'"+node.getNodeID()+"'";
                db.execSQL(deleteSql);
                Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
