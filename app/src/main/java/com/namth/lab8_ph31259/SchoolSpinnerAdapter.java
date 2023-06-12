package com.namth.lab8_ph31259;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SchoolSpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<School> list;

    public SchoolSpinnerAdapter(Context context, ArrayList<School> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        view=inflater.inflate(R.layout.activity_item_spinner,viewGroup,false);
        ImageView im_Spinner=view.findViewById(R.id.im_Spinner);
        TextView txt_name=view.findViewById(R.id.txt_name);
        im_Spinner.setImageResource(list.get(i).getHinh());
        txt_name.setText(list.get(i).getTen());

        return view;
    }
}
