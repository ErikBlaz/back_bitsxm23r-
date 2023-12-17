package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.User;
import domain.dataCtrl.UserDataCtrl;

public class UserDB implements UserDataCtrl{
    Connection conn;
    PreparedStatement insert;
    PreparedStatement update;
    PreparedStatement delete;
    PreparedStatement select;
    PreparedStatement selectAll;
    PreparedStatement selectByUsername;
    PreparedStatement selectByToken;
    PreparedStatement selectByEmail;

    public UserDB(){
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = conn.prepareStatement("INSERT INTO Users(username, email, password, token, hash) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            selectByUsername = conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
            select = conn.prepareStatement("SELECT * FROM Users WHERE id = ?");
            selectAll = conn.prepareStatement("SELECT * FROM Users");
            delete = conn.prepareStatement("DELETE FROM Users WHERE id = ?");
            update = conn.prepareStatement("UPDATE Users SET username = ?, email = ?, password = ?, hash = ?, token = ? WHERE id = ?");
            selectByToken = conn.prepareStatement("SELECT * FROM Users WHERE token = ?");
            selectByEmail = conn.prepareStatement("SELECT * FROM Users WHERE email = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dispose(){
        ConnectionFactory.getInstance().releaseConnection(conn);
    }

    public boolean insert(User u) {
        try {
            insert.setString(1, u.getUsername());
            insert.setString(2, u.getEmail());
            insert.setString(3, u.getPassword());
            insert.setString(4, u.getToken());
            insert.setString(5, u.getHash());
            insert.executeUpdate();
            ResultSet r = insert.getGeneratedKeys();
            if (r.next()){
                u.setId(r.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(int id){
        try {
            delete.setInt(1, id);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void update(User u){
        try {
            update.setString(1, u.getUsername());
            update.setString(2, u.getEmail());
            update.setString(3, u.getPassword());
            update.setString(4, u.getHash());
            update.setString(5, u.getToken());
            update.setInt(6, u.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User select(int id){
        try {
            select.setInt(1, id);
            ResultSet r = select.executeQuery();
            while(r.next()) {
                String username = r.getString("username");
                String email = r.getString("email");
                String password = r.getString("password");
                String token = r.getString("token");
                String hash = r.getString("hash");
                User u = new User(username, id, hash, password, email, token);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> selectAll(){
        ArrayList<User> ret = new ArrayList<>();
        try {
            ResultSet r = selectAll.executeQuery();
            while(r.next()) {
                String username = r.getString("username");
                String email = r.getString("email");
                String password = r.getString("password");
                int id = r.getInt("id");
                String token = r.getString("token");
                String hash = r.getString("hash");
                User u = new User(username, id, hash, password, email, token);
                u.setId(id);
                ret.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public User selectByUsername(String un){
        try {
            selectByUsername.setString(1, un);
            ResultSet r = selectByUsername.executeQuery();
            while(r.next()) {
                int id = r.getInt("id");
                String username = r.getString("username");
                String hash = r.getString("hash");
                String email = r.getString("email");
                String password = r.getString("password");
                String token = r.getString("token");
                User u = new User(username, id, hash, password, email, token);
                u.setToken(token);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectByToken(String token){
        try {
            selectByToken.setString(1, token);
            ResultSet r = selectByToken.executeQuery();
            while(r.next()) {
                int id = r.getInt("id");
                String username = r.getString("username");
                String hash = r.getString("hash");
                String email = r.getString("email");
                String password = r.getString("password");
                User u = new User(username, id, hash, password, email, token);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectByEmail(String email){
        try {
            selectByEmail.setString(1, email);
            ResultSet r = selectByEmail.executeQuery();
            while(r.next()) {
                int id = r.getInt("id");
                String username = r.getString("username");
                String hash = r.getString("hash");
                String token = r.getString("token");
                String password = r.getString("password");
                User u = new User(username, id, hash, password, email, token);
                u.setId(id);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}