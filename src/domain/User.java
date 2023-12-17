package domain;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class User {
    String username;
    int id;
    String hash;
    String password;
    String email;
    String token;

    public User(String username, int id, String hash, String password, String email, String token) {
        this.username = username;
        this.id = id;
        this.hash = hash;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User(String username, String hash, String password, String email, String token) {
        this.username = username;
        this.hash = hash;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", username);
            obj.put("email", email);
            obj.put("hash", hash);
            obj.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
