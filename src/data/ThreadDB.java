package data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Thread;
import domain.User;
import domain.dataCtrl.ThreadDataCtrl;

public class ThreadDB implements ThreadDataCtrl{
    Connection conn;
    PreparedStatement insert;
    PreparedStatement update;
    PreparedStatement delete;
    PreparedStatement select;
    PreparedStatement selectAll;
    PreparedStatement selectByUser;
    PreparedStatement selectByTheme;

    public ThreadDB(){
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = conn.prepareStatement("INSERT INTO Threads(userId, title, description, theme, created_at) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM Threads WHERE id = ?");
            selectAll = conn.prepareStatement("SELECT * FROM Threads");
            delete = conn.prepareStatement("DELETE FROM Threads WHERE id = ?");
            update = conn.prepareStatement("UPDATE Threads SET userId = ?, title = ?, theme = ?, description = ? WHERE id = ?");
            selectByUser = conn.prepareStatement("SELECT * FROM Threads WHERE userId = ?");
            selectByTheme = conn.prepareStatement("SELECT * FROM Threads WHERE theme = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dispose(){
        ConnectionFactory.getInstance().releaseConnection(conn);
    }

    public boolean insert(Thread t) {
        try {
            insert.setInt(1, t.getCreator().getId());
            insert.setString(2, t.getTitle());
            insert.setString(3, t.getDescription());
            insert.setString(4, t.getTheme());
            insert.setDate(5, new Date(t.getCreated_at().getTime()));
            insert.executeUpdate();
            ResultSet r = insert.getGeneratedKeys();
            if (r.next()){
                t.setId(r.getInt(1));
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

    public void update(Thread u){
        try {
            update.setInt(1, u.getCreator().getId());
            update.setString(2, u.getTitle());
            update.setString(3, u.getTheme());
            update.setString(4, u.getDescription());
            update.setInt(5, u.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Thread select(int id){
        try {
            select.setInt(1, id);
            ResultSet r = select.executeQuery();
            UserDB udb = new UserDB();
            while(r.next()) {
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                String description = r.getString("description");
                String theme = r.getString("theme");
                String title = r.getString("title");
                Date created_at = r.getDate("created_at");
                int likes = r.getInt("likes");
                Thread t =  new Thread(id, u, title, description, theme, created_at, likes);
                return t;
            }
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Thread> selectAll(){
        ArrayList<Thread> ret = new ArrayList<>();
        try {
            ResultSet r = selectAll.executeQuery();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                String description = r.getString("description");
                String theme = r.getString("theme");
                String title = r.getString("title");
                Date created_at = r.getDate("created_at");
                int likes = r.getInt("likes");
                Thread t =  new Thread(id, u, title, description, theme, created_at, likes);
                ret.add(t);
            }
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Thread selectByUser(int userId){
        try {
            selectByUser.setInt(1, userId);
            ResultSet r = selectByUser.executeQuery();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                User u = udb.select(userId);
                String description = r.getString("description");
                String theme = r.getString("theme");
                String title = r.getString("title");
                Date created_at = r.getDate("created_at");
                int likes = r.getInt("likes");
                Thread t =  new Thread(id, u, title, description, theme, created_at, likes);
                return t;
            }
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Thread> selectByTheme(String theme){
        System.out.println(theme);
        ArrayList<Thread> ret = new ArrayList<>();
        try {
            selectByTheme.setString(1, theme);
            ResultSet r = selectByTheme.executeQuery();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                String description = r.getString("description");
                String title = r.getString("title");
                Date created_at = r.getDate("created_at");
                int likes = r.getInt("likes");
                Thread t =  new Thread(id, u, title, description, theme, created_at, likes);
                ret.add(t);
            }
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}