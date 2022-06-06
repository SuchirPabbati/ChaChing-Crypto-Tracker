package com.ayoapp.cryptocurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>  {
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public boolean updown;

    public CurrencyRVAdapter(ArrayList<CurrencyRVModel> currencyRVModelArrayList, Context context) {
        this.currencyRVModelArrayList = currencyRVModelArrayList;
        this.context = context;
    }

    public void filterList(ArrayList<CurrencyRVModel> filteredList)  {
        currencyRVModelArrayList = filteredList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item,parent,false);
        return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModel currencyRVModel = currencyRVModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModel.getName());
        holder.symbolTV.setText(currencyRVModel.getSymbol());
        holder.rateTV.setText("$ " +df2.format(currencyRVModel.getPrice()));
        double per=currencyRVModel.getPercentchange();
        double per2 = Math.abs(per);
        if(per>0)
            updown = true;
        else
            updown = false;
        holder.percentTV.setText(df2.format(per2)+" %");
        Glide.with(context).load(currencyRVModel.getImageurl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView currencyNameTV,symbolTV,rateTV,percentTV;
        private ImageView imageView,imageView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTV = itemView.findViewById(R.id.idTVCurrencyName);
            rateTV = itemView.findViewById(R.id.idTVCurrencyRate);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            imageView2 = itemView.findViewById(R.id.currencyChangeImageView);
            if(updown) {
                imageView2.setImageResource(R.drawable.ic_caret_up);
            }
            else{
                imageView2.setImageResource(R.drawable.ic_caret_down);
            }
            percentTV = itemView.findViewById(R.id.currencyChangeTextView);
            imageView = itemView.findViewById(R.id.currencyImageView);
        }
    }
}