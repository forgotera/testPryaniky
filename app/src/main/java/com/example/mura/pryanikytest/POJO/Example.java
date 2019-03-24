package com.example.mura.pryanikytest.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Example {

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("view")
    @Expose
    private List<String> view = new ArrayList<String>();

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<String> getView() {
        return view;
    }

    public void setView(List<String> view) {
        this.view = view;
    }

}