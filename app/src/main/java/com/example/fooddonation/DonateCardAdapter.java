package com.example.fooddonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonateCardAdapter extends RecyclerView.Adapter<DonateCardAdapter.ViewHolder> {
    Context context;
    ArrayList<donate_struct> donate_array;
    DonateCardAdapter(Context context, ArrayList<donate_struct> donate_array){
        this.context=context;
        this.donate_array=donate_array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donate_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fname.setText(donate_array.get(position).getFname());
        holder.lname.setText(donate_array.get(position).getLname());
        holder.email.setText(donate_array.get(position).getEmail());
        holder.address.setText(donate_array.get(position).getAddress());
        holder.ngo_address.setText(donate_array.get(position).getNgo_address());
        holder.contact.setText(donate_array.get(position).getContact());
        holder.ftype.setText(donate_array.get(position).getFtype());
        holder.quantity.setText(donate_array.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return donate_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fname,lname,email,address,ngo_address,contact,ftype,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fname=itemView.findViewById(R.id.cust_displayfname);
            lname=itemView.findViewById(R.id.cust_displaylname);
            email=itemView.findViewById(R.id.cust_displayemail);
            address=itemView.findViewById(R.id.cust_displayaddress);
            ngo_address=itemView.findViewById(R.id.cust_displayngoaddress);
            contact=itemView.findViewById(R.id.cust_displaycontact);
            ftype=itemView.findViewById(R.id.cust_displayfoodtype);
            quantity=itemView.findViewById(R.id.cust_displayquantity);
        }
    }
}
