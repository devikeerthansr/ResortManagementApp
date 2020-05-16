package com.example.resortmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.resortmanagement.R;
import com.example.resortmanagement.model.ListModel;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
        import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleBaseAdapter extends BaseAdapter {
    private final Context context;
    private List<ListModel> values;

    public SimpleBaseAdapter(Context context, ArrayList<ListModel> values) {
        this.context = context;
        this.values = values;
    }
    public void setData(List<ListModel> values){
        this.values = values;
    }

    @Override
    public int getCount() {
        if(values != null) {
            return values.size();
        } else {
            return 0;
        }
    }

    @Override
    public ListModel getItem(int position) {
        if(values != null) {
            return values.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView1 =  rowView.findViewById(R.id.textView1);
        TextView textView2 =  rowView.findViewById(R.id.textView2);
        TextView textView3 =  rowView.findViewById(R.id.textView3);
        TextView textView4 =  rowView.findViewById(R.id.textView4);
        Button cancel = rowView.findViewById(R.id.cancel);
        if(values != null) {
            textView1.setText(values.get(position).getString1());
            textView2.setText(values.get(position).getString2());
            textView3.setText(values.get(position).getString3());
            textView4.setText(values.get(position).getString4());
            if (values.get(position).isCancellable()) {
                cancel.setEnabled(true);
                cancel.setBackgroundColor(context.getResources().getColor(R.color.red));
            } else {
                cancel.setEnabled(false);
                cancel.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));
            }
        }
        return rowView;
    }
}
