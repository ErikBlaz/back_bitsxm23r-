package domain;

import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Thread {
    
    User creator;
    int id;
    String title;
    String description;
    Date created_at;
    String theme;
    int likes;

    public Thread(int id, User creator, String title, String description, String theme, Date created_at, int likes) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.theme = theme;
        this.likes = likes;
        this.created_at = created_at;
    }


    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public User getCreator() {
        return creator;
    }
    public void setCreator(User creator) {
        this.creator = creator;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("description", description);
            obj.put("id", id);
            obj.put("title",title);
            obj.put("theme", theme);
            obj.put("creator", creator.getId());
            obj.put("created_at", created_at.toString());
            obj.put("likes", likes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONArray toJSON(ArrayList<Thread> list) {

        JSONArray array = new JSONArray();
        for (Thread elem : list) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("description", elem.getDescription());
                obj.put("id", elem.getId());
                obj.put("title", elem.getTitle());
                obj.put("theme", elem.getTheme());
                obj.put("creator", elem.getCreator().getId());
                obj.put("created_at", elem.getCreated_at().toString());
                obj.put("likes", elem.getLikes());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        return array;
    }
}
