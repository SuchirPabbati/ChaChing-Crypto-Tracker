package com.ayoapp.cryptocurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChartActivity extends AppCompatActivity {
    String currencyName,currencyNames;
    TextView coinName,nameSText,symbolSText,daySText,priceSText;
    ImageView coinImageView2;
    private ProgressBar loadingPB;
    private GraphView graph;
    public String imageurl;
    public String id;
    Context context;
    private LineGraphSeries<DataPoint> series;
    private String[] dates =new String[31];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        currencyName = getIntent().getExtras().get("coin_name").toString();
        currencyNames = getIntent().getExtras().get("coin_names").toString();
        id = getIntent().getExtras().get("coin_id").toString();
        //Log.d("imageurl",imageurl);
        imageurl = "https://s2.coinmarketcap.com/static/img/coins/64x64/"+id+".png";
        nameSText = findViewById(R.id.nameSText);
        coinImageView2 = findViewById(R.id.coinImageView2);
        symbolSText = findViewById(R.id.symbolSText);
        daySText = findViewById(R.id.daySText);
        priceSText = findViewById(R.id.PriceSText);
        loadingPB = findViewById(R.id.idPBloading2);
        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setMinX(0);
        graph.getViewport().setXAxisBoundsManual(true);

        getCurrencyData(currencyName);
    }
    private void getCurrencyData(String currencyName) {
        loadingPB.setVisibility(View.VISIBLE);
        DataPoint[] points = new DataPoint[31];

        String url = "https://min-api.cryptocompare.com/data/histoday?fsym="+currencyName+"&tsym=USD&limit=30";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int time=0;
                int day=0;
                double price=0;
                loadingPB.setVisibility(View.GONE);
                Picasso.get().load(imageurl).into(coinImageView2);
                symbolSText.setText(currencyName);
                nameSText.setText(currencyNames);


                try {
                    Log.d("url",url);
                    JSONArray dataArray = response.getJSONArray("Data");
                    for (int i = 0 ; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        time = dataObj.getInt("time");
                        long time2 = time * (long) 1000;
                        Date date = new Date(time2);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        format.setTimeZone(TimeZone.getTimeZone("EST"));
                        Log.d("DATE",format.format(date));
                        dates[i] = format.format(date);
                        price = dataObj.getDouble("close");
                        points[i] = new DataPoint(day,price);
                        day++;
                    }
                    series = new LineGraphSeries<>(points);
                    //coinName.setText(price+"");
                    graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.BOTH);
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Day");
                    graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
                    graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                    graph.getGridLabelRenderer().setGridColor(Color.WHITE);
                    graph.getGridLabelRenderer().setHighlightZeroLines(true);
                    //graph.setOutlineAmbientShadowColor(Color.parseColor("#00BDB0"));
                    series.setColor(Color.parseColor("#00BDB0"));


                    graph.setTitleTextSize(75);
                    graph.setTitleColor(Color.WHITE);


                    graph.getGridLabelRenderer().setPadding(3);
                    graph.setTitle("30 Day Price History");
                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);
                    graph.addSeries(series);
                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            daySText.setText("Day: "+dataPoint.getX());
                            priceSText.setText("Price: $"+dataPoint.getY());
                            //Toast.makeText(ChartActivity.this, "Day: "+(int)dataPoint.getX()+" Price: $"+dataPoint.getY(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                // show normal x values
                                return super.formatLabel(value, isValueX);
                            } else {
                                // show currency for y values
                                return "$"+super.formatLabel(value, isValueX) ;
                            }
                        }
                    });
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ChartActivity.this, "Failed to extract data from JSON...", Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }){

        };
        requestQueue.add(jsonObjectRequest);
    }
}