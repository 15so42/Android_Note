package com.example.a12930.note.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a12930.note.pojo.Note;

import java.util.ArrayList;
import java.util.Collections;

public class Dao {
    public Context context;
    public DBOpenHelper dbOpenHelper;
    SQLiteDatabase myDatabase;

    public Dao(Context context) {
        this.context=context;
        dbOpenHelper=new DBOpenHelper(context,"note",null,1);
        myDatabase =dbOpenHelper.getWritableDatabase();


    }

    public ArrayList<Note> getAllNote(){
        ArrayList<Note> notes=new ArrayList<Note>();
        Cursor cursor= myDatabase.rawQuery("select id,title,content,time from note" , null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String times = cursor.getString(cursor.getColumnIndex("time"));
            Note note = new Note(id, title,content, times);
            notes.add(note);
            cursor.moveToNext();
        }
        myDatabase.close();
        Collections.reverse(notes);
        return notes;

    }

    ///获取正在修改中的便签
    public Note getUpdatingNoteById(int id){

        Note note;
        Cursor cursor=myDatabase.rawQuery("select title,content,time from note where id='"+id+"'" , null);
        if(cursor.moveToFirst());
        {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            note = new Note(id, title, content, time);
        }
        myDatabase.close();
        return note;

    }

    public void InsertNote(Note note){

        myDatabase.execSQL("insert into note(title,content,time)values('"
                + note.getTitle()+"','"
                +note.getContent()+"','"
                +note.getTime()
                +"')");
        myDatabase.close();
    }

    /*
     * 用来修改日记
     */
    public void updateNote(Note note){

        //数据库此时处于关闭状态，必须重新启动一次数据库，不然无法读取
        myDatabase=dbOpenHelper.getWritableDatabase();
        myDatabase.execSQL(
                "update note set title='"+ note.getTitle()+
                        "',time='"+note.getTime()+
                        "',content='"+note.getContent() +
                        "' where id='"+ note.getId()+"'");
        myDatabase.close();
    }

    /*
     * 长按点击后选择删除日记
     */
    public void deleteById(int id){
        myDatabase  = dbOpenHelper.getWritableDatabase();
        myDatabase.execSQL("delete from note where id="+id+"");
        myDatabase.close();
    }





}
