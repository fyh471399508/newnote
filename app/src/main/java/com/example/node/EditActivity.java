package com.example.node;

import android.app.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity implements View.OnClickListener{

    private Button complete;
    private Button changeBackgroud;
    private EditText titleText;
    private EditText contentText;
    protected RecyclerView mTypeRecycleView;
    private List<String> listType= new ArrayList<String>();
    private SQLiteDatabase db;
    private int type=0;


    private String[] types = {"通知", "日记", "博客", "工作"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit);

        init();
    }

    public void init(){
        db = DataBaseUtil.returnDataBase(this);

        complete=findViewById(R.id.complete);
        changeBackgroud = findViewById(R.id.change_background);
        mTypeRecycleView = findViewById(R.id.recycleview_edit_type);
        titleText = findViewById(R.id.node_title);
        contentText = findViewById(R.id.node_content);

        complete.setOnClickListener(this);
        changeBackgroud.setOnClickListener(this);

        mTypeRecycleView.setLayoutManager(new GridLayoutManager(this,4));
        mTypeRecycleView.addItemDecoration(new SpaceItemDecoration(0,0));
        final TypeAdapter typeAdapter = new TypeAdapter(this, toList(this.types));
        mTypeRecycleView.setAdapter(typeAdapter);

        mTypeRecycleView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TypeViewholder viewHolder=(TypeViewholder)mTypeRecycleView.getChildViewHolder(mTypeRecycleView.getChildAt(position));
                viewHolder.mTypeText.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.mTypeText.setBackgroundResource(R.drawable.selectedtextbackground);
                type=position+1;
            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onScroll(View view, int position) {

            }
        }));

    }

    // String sql = "create table nodepad(_id integer primary key autoincrement," + "title varchar(50),"+"content varchar(500),"+"date varchar(50),"+"type integer)";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:


                String title = titleText.getText().toString();
                String content = contentText.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date(System.currentTimeMillis());
                String date = simpleDateFormat.format(currentDate);

                String insertSql="insert into nodepad(title,content,date,type)values(?,?,?,?)";

                db.execSQL(insertSql,new String[]{title,content,date,type+""});
                Toast.makeText(this, "增添成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.change_background:
                showDialog();
                break;

        }
    }

    public List<String> toList(String likes[]){
        for(int i=0;i<likes.length;i++){
            this.listType.add(likes[i]);
        }
        return this.listType;
    }

    public void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(EditActivity.this).inflate(R.layout.layout_dialog, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();

        ImageView mainImg = view.findViewById(R.id.back_main);
        ImageView noticeImg = view.findViewById(R.id.back_notice);
        ImageView diaryImg = view.findViewById(R.id.back_diary);
        ImageView jobImg = view.findViewById(R.id.back_job);
        ImageView blogImg = view.findViewById(R.id.back_blog);

        mainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setBackgroundColor(Color.parseColor("#2DBE60"));
                contentText.setTextColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });

        noticeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setBackgroundColor(Color.parseColor("#FFB343"));
                contentText.setTextColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });

        diaryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setBackgroundColor(Color.parseColor("#8086FF"));
                contentText.setTextColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });


        jobImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setBackgroundColor(Color.parseColor("#45CDE5"));
                contentText.setTextColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });

        blogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setBackgroundColor(Color.parseColor("#FE6B64"));
                contentText.setTextColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });

    }
}
