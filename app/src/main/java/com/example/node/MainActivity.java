package com.example.node;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    protected RecyclerView mRecyclerView;
    private List<Node> listNode=new ArrayList<Node>();
    private SQLiteDatabase db;

    //悬浮窗
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        mRecyclerView = findViewById(R.id.recycleview_like);
        fab_setting=findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);

/*        for(int i=1;i<=4;i++){

            Node node=new Node();
            node.setNodeTitle("今日日记");
            node.setNodeContent("今天贼鸡儿高兴，因为我今天过生日，哈哈哈哈哈哈");
            node.setSaveDate("2012-20-12");
            node.setNodeClass(i);
            listNode.add(node);
        }*/

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0,0));
        final NodeAdapter nodeAdapter = new NodeAdapter(this, listNode);
        mRecyclerView.setAdapter(nodeAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", listNode.get(position).getNodeID());
                bundle.putString("title",listNode.get(position).getNodeTitle());
                bundle.putString("content",listNode.get(position).getNodeContent());
                bundle.putInt("type",listNode.get(position).getNodeClass());
                bundle.putString("date",listNode.get(position).getSaveDate());
                intent.putExtras(bundle);
                startActivity(intent);
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
    protected void onResume() {
        super.onResume();
        this.db = DataBaseUtil.returnDataBase(this);
        Cursor cursor = db.rawQuery("select * from nodepad order by _id desc", null);
        if (cursor.moveToFirst()) {
            do{
                Node node=new Node();
                node.setNodeID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                node.setNodeTitle(cursor.getString(cursor.getColumnIndex("title")));
                node.setNodeContent(cursor.getString(cursor.getColumnIndex("content")));
                node.setSaveDate(cursor.getString(cursor.getColumnIndex("date")));
                node.setNodeClass(Integer.parseInt(cursor.getString(cursor.getColumnIndex("type"))));
                listNode.add(node);
            }while(cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}

