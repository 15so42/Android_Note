package com.example.a12930.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12930.note.R;
import com.example.a12930.note.pojo.Note;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    int itemResourceId;
    List<Note> noteData;
    public ListAdapter(Context context, int itemResourceId, List<Note> noteData) {

        this.context=context;
        this.itemResourceId=itemResourceId;
        this.noteData=noteData;
    }


    @Override
    public int getCount() {
        return noteData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(itemResourceId, parent,false);//注意导包，别导系统包

            vh.content=(TextView) convertView.findViewById(R.id.content);
            vh.time=(TextView) convertView.findViewById(R.id.time);
            convertView.setTag(vh);
        }
        vh=(ViewHolder) convertView.getTag();

        String content=noteData.get(position).getContent();
        vh.content.setText(content.substring(0,content.length()>50?50:content.length()));
        vh.time.setText(noteData.get(position).getTime());
        return convertView;
    }
    class ViewHolder{
        TextView content,time;
    }

}

