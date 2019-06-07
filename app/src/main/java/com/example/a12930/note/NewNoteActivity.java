package com.example.a12930.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a12930.note.dao.DBOpenHelper;
import com.example.a12930.note.dao.Dao;
import com.example.a12930.note.pojo.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {
    EditText ed1,ed2;
    ImageButton imageButton;
    Dao dao;
    Note note;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        //ed1=(EditText) findViewById(R.id.editText1);
        TextView time=findViewById(R.id.time);
        ed2=(EditText) findViewById(R.id.editText2);
        imageButton=(ImageButton) findViewById(R.id.saveButton);
        dao=new Dao(this);

        Intent intent=this.getIntent();
        id=intent.getIntExtra("id", 0);
        //默认为0，不为0,则为修改数据时跳转过来的
        if(id!=0){
            note=dao.getUpdatingNoteById(id);
            time.setText(note.getTime());
            //ed1.setText(note.getTitle());
            ed2.setText(note.getContent());
        }
        //保存按钮的点击事件，他和返回按钮是一样的功能，所以都调用isSave()方法；
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    /*
     * 返回按钮调用的方法。
     */
    @Override
    public void onBackPressed() {


        saveNote();
    }
    private void saveNote(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String times = formatter.format(curDate);

        String title="";
        String content=ed2.getText().toString();
        //正在修改便签
        if(id!=0){
            note=new Note(id,title, content, times);
            dao.updateNote(note);
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            NewNoteActivity.this.finish();
        }
        //新建便签
        else{
            //无内容时不保存
            if(title.equals("")&&content.equals("")){
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                NewNoteActivity.this.finish();
                return;
            }
            //有内容无标题,截取内容作为标题
            if(title.equals("")&&!content.equals(""))
            {
                title=content.substring(0,content.length()>50?50:content.length());
            }
            note=new Note(id,title,content,times);
            dao.InsertNote(note);
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

}
