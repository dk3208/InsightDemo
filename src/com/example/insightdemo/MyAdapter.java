package com.example.insightdemo;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private LayoutInflater myInflater;
    private List<List_Initem> items;
    
    public MyAdapter(Context context,List<List_Initem> item){
        myInflater = LayoutInflater.from(context);
        this.items = item;
        }
    
    @Override
    public int getCount() {
            return items.size();
    }

    @Override
    public Object getItem(int arg0) {
            return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
            return items.indexOf(getItem(position));
    }
    
    private class ViewHolder {
        TextView txtTitle;
        //ImageView img;
        public ViewHolder(TextView txtTitle/*, ImageView img*/){
                this.txtTitle = txtTitle;
               //this.img = img;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder = null;
    	if(convertView==null){
    	        convertView = myInflater.inflate(R.layout.list_item, null);
    	        holder = new ViewHolder(
    	                (TextView) convertView.findViewById(R.id.item_name)//,
    	                ///(ImageView) convertView.findViewById(R.id.item_image)
    	                );
    	        convertView.setTag(holder);
    	}else{
    	        holder = (ViewHolder) convertView.getTag();
    	}
    	List_Initem items = (List_Initem)getItem(position);
        //0 = movie, 1 = title, 2 = nine
        int color_title[] = {Color.GRAY,Color.BLACK};
        //int color_time[] = {Color.WHITE,Color.WHITE,Color.YELLOW};
        //int color_back[] = {Color.BLACK,Color.BLUE,Color.BLACK};
        //int time_vis[] = {View.VISIBLE,View.GONE,View.VISIBLE};
       
        int type_num = items.getType();
        holder.txtTitle.setText(items.getName());
        holder.txtTitle.setTextColor(color_title[type_num]);

    	return convertView;
    }
}
