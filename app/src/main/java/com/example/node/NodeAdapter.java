package com.example.node;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeAdapter extends RecyclerView.Adapter<NodeViewholder> {

    protected Context context;
    protected List<Node> nodeList;
    protected Map nodeViewholderMap = new HashMap<>();

    public NodeAdapter(Context context,List<Node> nodes){
        this.context=context;
        this.nodeList = nodes;
    }

    @NonNull
    @Override
    public NodeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NodeViewholder(LayoutInflater.from(context).inflate(R.layout.layout_item,null));
    }
    public Map getNodeViewholderMap(){
        return nodeViewholderMap;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewholder likeViewholder, int i) {
        Node node=nodeList.get(i);
        switch (node.getNodeClass()){
            case 1:
                likeViewholder.nodeClass.setText("通知");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.noticebackground);break;
            case 2:
                likeViewholder.nodeClass.setText("日记");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.diarybackground);break;
            case 3:
                likeViewholder.nodeClass.setText("博客");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.blogbackground);break;
            case 4:
                likeViewholder.nodeClass.setText("工作");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.jobbackground);break;
        }
        likeViewholder.nodeTitle.setText(node.getNodeTitle());
        likeViewholder.nodeContent.setText(node.getNodeContent());
        likeViewholder.nodeDate.setText(node.getSaveDate());
    }

    @Override
    public int getItemCount() {
        return null!=nodeList?nodeList.size():0;
    }
}
