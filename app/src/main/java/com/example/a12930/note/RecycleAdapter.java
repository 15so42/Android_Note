package com.example.a12930.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import com.example.a12930.note.dao.Dao;
import com.example.a12930.note.pojo.Note;

import org.w3c.dom.Text;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {
    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * 数据集合
     */
    private static List<Note> data;


    public RecycleAdapter(List<Note> data, Context context) {
        this.data = data;
        this.mContext = context;
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        //将数据设置到item上
        Note note = data.get(position);

        String content=note.getContent();
        int splitIndex=0;
        splitIndex=content.length()>18?18:content.length();
        holder.title.setText(content.substring(0,splitIndex));
        holder.content.setText(content.substring(splitIndex,content.length()>80?80:content.length()));
        holder.time.setText(note.getTime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView title;//第一行用粗体显示
        TextView content;
        TextView time;


        public RecycleViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.r_title);
            content = itemView.findViewById(R.id.r_content);

            time = itemView.findViewById(R.id.r_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,NewNoteActivity.class);
                    intent.putExtra("id", data.get(getLayoutPosition()).getId());
                    mContext.startActivity(intent);
                    RecycleActivity recycleActivity= (RecycleActivity) mContext;
                    recycleActivity.finish();

                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //AlertDialog,来判断是否删除日记。
                    new AlertDialog.Builder(mContext).setTitle("删除").setMessage("是否删除笔记").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Dao(mContext.getApplicationContext()).deleteById(data.get(getLayoutPosition()).getId());
                            //删除后重新加载页面
                            data=new Dao(mContext.getApplicationContext()).getAllNote();
                            //RecycleAdapter adapter=new RecycleAdapter(data,mContext.getApplicationContext());
                            RecycleActivity recycleActivity=(RecycleActivity)mContext;
                            recycleActivity.adapter.notifyDataSetChanged();

                        }
                    })
                            .create().show();
                    return true;
                }
            });





        }



    }
}