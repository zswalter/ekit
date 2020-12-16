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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.oakland.ekit.MainListData;
import com.oakland.ekit.R;
import com.oakland.ekit.SurveyActivity;
import com.oakland.ekit.UserInformationActivity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
                .inflate(R.layout.ticket_list_row, viewGroup, false);
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

        //set the color of the holder
        holder.parent.setBackgroundColor(mContext.getColor(R.color.primaryColor));

        //set the title label text
        holder.txtTicketID.setText("ID: " + data.ticketItem.getId());


        //because the date time zone conventions require sdk check, only add if it fits the check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            ZonedDateTime date = ZonedDateTime.parse(data.ticketItem.getCreatedDate());

            // get the number of days its been open for
            int numberOfDays = Period.between(date.toLocalDate(), LocalDate.now()).getDays();

            holder.txtTicketOpenDate.setText("Open Since: " + DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(date) + " (" + numberOfDays + " Days)");

            //DateTimeFormatter.ofPattern("E, MMM dd, hh:mm a").format(messageModel.messageTime)


        }else{
            holder.txtTicketOpenDate.setText("Open Since: " + data.ticketItem.getCreatedDate());
        }



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
        TextView txtTicketID;
        TextView txtTicketOpenDate;
        ConstraintLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtTicketID = itemView.findViewById(R.id.txtTicketId);
            txtTicketOpenDate = itemView.findViewById(R.id.txtOpenSince);
        }
    }
}