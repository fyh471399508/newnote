package com.example.node;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeAdapter extends RecyclerView.Adapter<TypeViewholder>{
    protected Context context;
    protected List<String> types;
    private Map typeViewholderMap=new HashMap<>();
    public TypeAdapter(Context context, List<String> types){
        this.context=context;
        this.types=types;
    }

    @NonNull
    @Override
    public TypeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TypeViewholder(LayoutInflater.from(context).inflate(R.layout.layout_type_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewholder typeViewholder, int i) {
        String type=types.get(i);
        typeViewholder.mTypeText.setText(type);
        typeViewholderMap.put(i,typeViewholder);
    }

    @Override
    public int getItemCount() {
        return null!=types?types.size():0;
    }
}
