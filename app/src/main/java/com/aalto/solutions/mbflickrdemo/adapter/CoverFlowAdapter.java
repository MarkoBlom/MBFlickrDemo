package com.aalto.solutions.mbflickrdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aalto.solutions.mbflickrdemo.R;
import com.aalto.solutions.mbflickrdemo.model.ImageModel;

import java.util.ArrayList;

/**
 * Created by marko
 */
public class CoverFlowAdapter extends BaseAdapter
{
    private Context mContext;

    private ArrayList<ImageModel> mData= new ArrayList<>();

    //=================================================

    public CoverFlowAdapter(Context context) { mContext = context; }

    public void setData(ArrayList<ImageModel> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;

        if (rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow_view, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.image.setImageBitmap( mData.get(position).getMediumBitmap() );

        holder.text.setText( mData.get(position).getName() );

        return rowView;
    }

    //=================================================

    static class ViewHolder
    {
        public TextView text;
        public ImageView image;
    }

    //=================================================
}