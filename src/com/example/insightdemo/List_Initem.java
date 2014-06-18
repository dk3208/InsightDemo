package com.example.insightdemo;

import android.widget.ImageView;


public class List_Initem {
    private int type;	//color
    private String name;//title
    private ImageView image;//image
    public List_Initem(int type,String name,ImageView image) {
            this.type = type;
            this.name = name;
            this.image = image;
    }
    public int getType(){
            return type;
    }
    public void setType(int type){
            this.type = type;
    }
    public String getName(){
            return name;
    }
    public void setName(String name){
            this.name = name;
    }
    public ImageView getImage(){
            return image;
    }
    public void setTime(ImageView image){
            this.image = image;
    }
}