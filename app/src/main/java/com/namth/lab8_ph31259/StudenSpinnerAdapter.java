package com.namth.lab8_ph31259;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class StudenSpinnerAdapter extends BaseAdapter implements Filterable {
    private   Context context;
    delete deleteitem;
    updata updataitem;
    private final ArrayList<Studen> listol;
    private  ArrayList<Studen> list;

    public StudenSpinnerAdapter(ArrayList<Studen> list,Context context, delete deleteitem, updata updataitem) {
        this.context = context;
        this.deleteitem = deleteitem;
        this.updataitem = updataitem;
        this.listol = list;
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
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        view=inflater.inflate(R.layout.activity_item_listview,viewGroup,false);
        TextView txt_namelv=view.findViewById(R.id.txt_nameLv);
        TextView txt_brand=view.findViewById(R.id.txt_branch);
        TextView txt_address=view.findViewById(R.id.txt_address);
        Button btn_updeat=view.findViewById(R.id.btn_update);
        Button btn_deleta=view.findViewById(R.id.btn_delete);
        txt_brand.setText(list.get(i).getBranch());
        txt_namelv.setText(list.get(i).getName());
        txt_address.setText(list.get(i).getAddress());


        btn_deleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteitem.onClickButtonDeleteItem(i);
            }
        });
        btn_updeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updataitem.onClickButtonUpdateItem(i);
            }
        });
        return view;
    }

    @Override
    public Filter getFilter() {

        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String s=charSequence.toString();
                if(s.isEmpty()){
                    list=listol;
                } else{
                    ArrayList<Studen> listtim=new ArrayList<>();
                    for (Studen x:listol) {
                        if(x.getName().toLowerCase().contains(s.toLowerCase())){
                            listtim.add(x);
                        }
                    }
                    list=listtim;


                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                        list=(ArrayList<Studen>) filterResults.values;
                        notifyDataSetChanged();
            }
        };
    }
}
