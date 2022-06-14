package com.ayoapp.cryptocurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>  {
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public boolean updown;
    public String urls;
    public StringBuilder temp = new StringBuilder();
    public String temp2;
    public boolean favorite;
    SharedPreferences preferences;


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
        preferences=  PreferenceManager.getDefaultSharedPreferences(context);
        return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModel currencyRVModel = currencyRVModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModel.getName());
        holder.ids = currencyRVModel.getId();
        holder.symbolTV.setText(currencyRVModel.getSymbol());
        holder.rateTV.setText("$ " +df2.format(currencyRVModel.getPrice()));
        double per=currencyRVModel.getPercentchange();
        double per2 = Math.abs(per);
        if(per>0)
            updown = true;
        else
            updown = false;
        if(preferences.getString("Name","").contains(holder.currencyNameTV.getText() + "")){
            favorite = false;
            holder.imageView2.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        else{
            favorite = true;
            holder.imageView2.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        holder.percentTV.setText(df2.format(per2)+" %");
        Glide.with(context).load(currencyRVModel.getImageurl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView currencyNameTV,symbolTV,rateTV,percentTV;
        private ImageView imageView,imageView2;
        private int ids;

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
            imageView2 = itemView.findViewById(R.id.imageView);
            Log.d("Preferences", preferences.getString("Name",""));


            imageView2.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent chartIntent = new Intent(context,ChartActivity.class);
                    chartIntent.putExtra("coin_name",symbolTV.getText());
                    chartIntent.putExtra("coin_names",currencyNameTV.getText());
                    chartIntent.putExtra("coin_id",ids);
                    Log.d("id",ids+"");
                    context.startActivity(chartIntent);
                }
            });
        }

        @Override
        public void onClick(View v) {

            //Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show();
            if (imageView2.getDrawable().getConstantState() == v.getResources().getDrawable( R.drawable.ic_baseline_favorite_border_24).getConstantState()) {
                imageView2.setImageResource(R.drawable.ic_baseline_favorite_24);
                SharedPreferences.Editor editor = preferences.edit();
                temp2 = (preferences.getString("Name","")+currencyNameTV.getText()+",");
                Log.d("temp2",temp2);
                editor.putString("Name",temp2.toString());
                editor.apply();
                //Toast.makeText(context, temp2.toString(), Toast.LENGTH_SHORT).show();
                favorite = false;
            } else {
                //imageView2.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                //favorite = true;
            }
        }
    }
}
