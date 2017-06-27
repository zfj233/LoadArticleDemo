package com.zfj.android.loadarticles.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfj.android.loadarticles.R;

import java.util.List;

/**
 * Created by zfj_ on 2017/6/11.
 */

/**
 * ListView适配器
 */
public class FavorListAdapter extends BaseAdapter {
    private List<String> favorTitle;
    Context mContext;
    public FavorListAdapter(Context context,List<String> favorTitle) {
        this.favorTitle = favorTitle;
        mContext = context;
    }

    @Override
    public int getCount() {
        return favorTitle.size();
    }

    @Override
    public String getItem(int position) {
        return favorTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.favor_list_item,null);
            viewHolder.favorTitleText = (TextView) convertView.findViewById(R.id.favor_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.favorTitleText.setText(favorTitle.get(position));
        return convertView;
    }
    class ViewHolder{
        private TextView favorTitleText;
    }
}
