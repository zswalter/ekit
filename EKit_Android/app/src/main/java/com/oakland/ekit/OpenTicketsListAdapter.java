package com.oakland.ekit;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.oakland.ekit.MainListData;
import com.oakland.ekit.R;
import com.oakland.ekit.SurveyActivity;
import com.oakland.ekit.UserInformationActivity;

import java.util.List;

public class OpenTicketsListAdapter extends RecyclerView.Adapter {

    Context mContext = null;

    List mainListDataList;
    public OpenTicketsListAdapter(List mainDataList) {
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
        OpenTicketListData data= (OpenTicketListData) mainListDataList.get(position);

        //to signify disabled items
        if(position <= 1){

            holder.parent.setBackgroundColor(mContext.getColor(R.color.primaryColor));

        }else{
            //disabled
            holder.parent.setBackgroundColor(mContext.getColor(R.color.disabledOrange));
        }

        holder.cardName.setText(data.name);


        //add a on click listener to the holder item
        holder.parent.setOnClickListener(v -> {

            //create intent to pass the ticket data and present the ticket view
            Intent newTicketIntent = new Intent(mContext, TicketItemActivity.class);
            newTicketIntent.putExtra("ticketData", data.ticketItem);
            mContext.startActivity(newTicketIntent);

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