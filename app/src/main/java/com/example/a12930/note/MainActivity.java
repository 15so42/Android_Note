package com.example.a12930.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a12930.note.dao.Dao;
import com.example.a12930.note.pojo.Note;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    List<Note> data;
    Dao dao;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences sharedPreferences= getSharedPreferences("data", this .MODE_PRIVATE);
        String activityTarget=sharedPreferences.getString("activityTarget","main");
        if(!activityTarget.equals("main")){

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("activityTarget", "recycle");

            editor.commit();
            Intent intent=new Intent(this,RecycleActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }





        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        //StatusBarCompat.setStatusBarColor(this,Color.parseColor("#6bbbec"),true);
        listView= (ListView) findViewById(R.id.list);
        data= getAllNotes();
        ListAdapter adapter=new ListAdapter(this,R.layout.listview_item,data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                intent.putExtra("id", data.get(position).getId());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        /*
         * 长点后来判断是否删除数据
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //AlertDialog,来判断是否删除日记。
                new AlertDialog.Builder(MainActivity.this).setTitle("删除").setMessage("是否删除笔记").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Dao(getApplicationContext()).deleteById(data.get(position).getId());
                            //删除后重新加载页面
                            data=new Dao(getApplicationContext()).getAllNote();
                            ListAdapter adapter=new ListAdapter(getApplicationContext(),R.layout.listview_item,data);
                            listView.setAdapter(adapter);
                        }
                        })
                        .create().show();
                return true;
            }
        });

        addButton=findViewById(R.id.btnAdd);
        //新建按钮
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewNoteActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });



    }

    List<Note> getAllNotes(){
        List<Note> data=new Dao(this).getAllNote();

        return data;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 这条表示加载菜单文件，第一个参数表示通过那个资源文件来创建菜单
        // 第二个表示将菜单传入那个对象中。这里我们用Menu传入menu
        // 这条语句一般系统帮我们创建好
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
                MainActivity.this.finish();
                break;
            case R.id.item2:
                //宫格模式
                SharedPreferences sharedPreferences= getSharedPreferences("data", this .MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("activityTarget", "recycle");

                editor.commit();
                Intent intent1=new Intent(this,RecycleActivity.class);
                startActivity(intent1);
                MainActivity.this.finish();
                break;
            case R.id.item3:
                //退出便签
                MainActivity.this.finish();
                break;


            default:
                break;
        }
        return true;

    }
}
