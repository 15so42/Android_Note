package com.example.a12930.note.pojo;
public class Note {
    private int id;        //编号
    private String title;   //标题
    private String content; //内容
    private String time;   //时间

    public Note(int id, String title, String content, String times) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String times) {
        this.time = times;
    }
}
