package com.oakland.ekit;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
        import java.util.Random;

class MainListAdapter extends RecyclerView.Adapter {

    Context mContext = null;

    List mainListDataList;
    public MainListAdapter(List mainDataList) {
        this.mainListDataList = mainDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_list_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        onBindViewHolderTest(((MyViewHolder) holder), position);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onBindViewHolderTest(@NonNull MyViewHolder holder, int position) {
        MainListData data= (MainListData) mainListDataList.get(position);



        holder.parent.setBackgroundColor(mContext.getColor(R.color.primaryColor));
        holder.cardName.setText(data.name);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {



            }
        });


    }

    @Override
    public int getItemCount() {
        return mainListDataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            cardName = itemView.findViewById(R.id.cardName);
        }
    }
}