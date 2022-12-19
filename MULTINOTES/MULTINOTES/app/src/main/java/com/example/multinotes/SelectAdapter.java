package com.example.multinotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectAdapter extends BaseAdapter {

    private String[] result;
    private int[] imageId;
    private Context context;

    public SelectAdapter(Context context, String[] result, int[] imageId) {
        this.context = context;
        this.result = result;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_listview_select_item, parent, false);

        TextView tvPriority = rowView.findViewById(R.id.priority_text);
        ImageView imgPriority = rowView.findViewById(R.id.priority_image);

        tvPriority.setText(result[position]);
        imgPriority.setImageResource(imageId[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rowView;
    }
}
