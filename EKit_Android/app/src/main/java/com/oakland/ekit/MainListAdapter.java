package com.oakland.ekit;



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;
import android.widget.Toast;

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


        //to signify disabled items
        if(position <= 1){

            holder.parent.setBackgroundColor(mContext.getColor(R.color.primaryColor));

        }else{
            //disabled
            holder.parent.setBackgroundColor(mContext.getColor(R.color.disabledOrange));
        }

        holder.cardName.setText(data.name);


        holder.parent.setOnClickListener(v -> {

            switch (position){

                case 0: //Account Info

                    mContext.startActivity(new Intent(mContext, UserInformationActivity.class));

                    break;

                case 1: //Survey

                    mContext.startActivity(new Intent(mContext, SurveyActivity.class));


                    break;

                default:

                    //not available feature yet
                    Toast.makeText(mContext, "Feature Not Available Yet!", Toast.LENGTH_SHORT).show();

                    break;



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