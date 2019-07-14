package com.example.node;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TypeViewholder extends RecyclerView.ViewHolder {
    public TextView mTypeText;

    public TypeViewholder(@NonNull View itemView) {
        super(itemView);
        mTypeText=itemView.findViewById(R.id.type_text);
    }
}
