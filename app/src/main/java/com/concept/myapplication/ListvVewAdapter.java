package com.concept.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListvVewAdapter extends BaseAdapter {
    private View view;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private ArrayList<Item> mItems;
    public ListvVewAdapter(Context context, ArrayList<Item> items){
        mContext = context;
        mItems = items;
    }
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (null == view){
            layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lv_view,parent,false);
            //get the textview for item name
            viewHolder.mNote = view.findViewById(R.id.main_lv_text);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        //get current item to bbe displayed
//        Item currentItem = (Item) getItem(position);
//        viewHolder.mNote.setText(currentItem.getItemName());
        viewHolder.mNote.setText(mItems.get(position).getItemName());
        view.setTag(viewHolder);
        return view;
    }
    private static class ViewHolder{
        private TextView mNote;
    }
}
