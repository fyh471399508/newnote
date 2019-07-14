package com.example.node;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    public SQLiteDatabase db;
    protected RecyclerView mRecyclerView;
    private List<Node> listNode=new ArrayList<Node>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        init();
    }

    public void init(){
        mRecyclerView = getView().findViewById(R.id.notice_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0,0));
        final NodeAdapter nodeAdapter = new NodeAdapter(this.getContext(), listNode);
        mRecyclerView.setAdapter(nodeAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
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

    public void initData(){
        db= DataBaseUtil.returnDataBase(this.getContext());
        Cursor cursor = db.rawQuery("select * from nodepad where type="+"'"+1+"'", null);
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
}
