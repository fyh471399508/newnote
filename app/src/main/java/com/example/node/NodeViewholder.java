package com.example.node;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NodeViewholder extends RecyclerView.ViewHolder {

    public TextView nodeClass;
    public TextView nodeTitle;
    public TextView nodeContent;
    public TextView nodeDate;


    public NodeViewholder(@NonNull View itemView) {
        super(itemView);
        nodeClass=itemView.findViewById(R.id.class_name);
        nodeTitle=itemView.findViewById(R.id.title);
        nodeContent = itemView.findViewById(R.id.content);
        nodeDate = itemView.findViewById(R.id.date);
    }
}
