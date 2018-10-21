package com.example.mostafa.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>  {

    private List<Country> Countries = new ArrayList<Country>();
    private Context context;
    private DisplayData displayData;

    public CountryAdapter(List<Country> c, Context context,DisplayData displayData) {
        this.context = context;
        this.Countries = c;
        this.displayData=displayData;
    }

    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountryAdapter.MyViewHolder holder, int position) {
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").fit().centerCrop().into(holder.imageView1);
        //Glide.with(context).load(Countries.get(position)).into(holder.imageView1);
        SvgLoader.pluck()
                .with(displayData)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(Countries.get(position).flag, holder.imageView1);
        holder.textView1.setText(Countries.get(position).name);
        holder.textView3.setText(Countries.get(position).capital);
        holder.textView4.setText(Countries.get(position).region);
    }

    @Override
    public int getItemCount() {
        return Countries.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView1;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;


        public MyViewHolder(View itemView) {
            super(itemView);

            imageView1 = (ImageView) itemView.findViewById(R.id.c_image);
            textView1 = (TextView) itemView.findViewById(R.id.c_name);
            textView3 = (TextView) itemView.findViewById(R.id.c_capital);
            textView4 = (TextView) itemView.findViewById(R.id.c_region);

        }
    }

}
