package com.example.a12930.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;


import com.example.a12930.note.dao.Dao;
import com.example.a12930.note.pojo.Note;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    private List<Note> data = new ArrayList<>();
    public RecycleAdapter adapter;
    ImageButton addButton;

    public RecyclerView getRecyclerView(){
        return  recyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences= getSharedPreferences("data", this .MODE_PRIVATE);
        String activityTarget=sharedPreferences.getString("activityTarget","main");
        if(!activityTarget.equals("recycle")){

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("activityTarget", "main");

            editor.commit();
            Intent intent=new Intent(this,RecycleActivity.class);
            startActivity(intent);
            RecycleActivity.this.finish();
        }

        setContentView(R.layout.recycle_main);
        data=new Dao(this).getAllNote();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //给recyclerView设置LayoutManager
        recyclerView.setLayoutManager(recyclerViewLayoutManager);


        adapter = new RecycleAdapter(data, this);
        //设置adapter
        recyclerView.setAdapter(adapter);







        addButton=findViewById(R.id.btnAdd);
        //新建按钮
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewNoteActivity.class);
                startActivity(intent);
                RecycleActivity.this.finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 这条表示加载菜单文件，第一个参数表示通过那个资源文件来创建菜单
        // 第二个表示将菜单传入那个对象中。这里我们用Menu传入menu
        // 这条语句一般系统帮我们创建好
        getMenuInflater().inflate(R.menu.recycle_menu, menu);
        return true;
    }

    // 菜单的监听方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                //新建便签
                Intent intent=new Intent(this,NewNoteActivity.class);
                startActivity(intent);
                RecycleActivity.this.finish();
                break;
            case R.id.item4:
                //列表模式
                SharedPreferences sharedPreferences= getSharedPreferences("data", this .MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("activityTarget", "main");

                editor.commit();
                Intent intent1=new Intent(this,MainActivity.class);
                startActivity(intent1);
                RecycleActivity.this.finish();
                break;
            case R.id.item3:
                //退出便签
                RecycleActivity.this.finish();
                break;


            default:
                break;
        }
        return true;

    }

}