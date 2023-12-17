package domain;

import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Comment {
    
    User owner;
    String comment;
    Date date;
    int id;
    int likes;
    Thread thread;

    public Comment(int id, User owner, Thread thread, String comment, int likes, Date created_at) {
        this.id = id;
        this.owner = owner;
        this.thread = thread;
        this.comment = comment;
        this.likes = likes;
        this.date = created_at;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public Thread getThread() {
        return thread;
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("comment", comment);
            obj.put("id", id);
            obj.put("thread", thread.getId());
            obj.put("creator", owner.getId());
            obj.put("date", date.toString());
            obj.put("likes", likes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONArray toJSON(ArrayList<Comment> list) {

        JSONArray array = new JSONArray();
        for (Comment elem : list) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("comment", elem.getComment());
                obj.put("id", elem.getId());
                obj.put("thread", elem.getThread().getId());
                obj.put("creator", elem.getOwner().getId());
                obj.put("date", elem.getDate().toString());
                obj.put("likes", elem.getLikes());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        return array;
    }
}
