package data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.User;
import domain.Thread;
import domain.Comment;
import domain.dataCtrl.CommentDataCtrl;

public class CommentDB implements CommentDataCtrl{
    Connection conn;
    PreparedStatement insert;
    PreparedStatement update;
    PreparedStatement delete;
    PreparedStatement select;
    PreparedStatement selectAll;
    PreparedStatement selectByThread;
    PreparedStatement selectByUser;

    public CommentDB(){
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = conn.prepareStatement("INSERT INTO Comments(userId, threadId, comment, likes, created_at) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            selectByThread = conn.prepareStatement("SELECT * FROM Comments WHERE threadId = ?");
            select = conn.prepareStatement("SELECT * FROM Comments WHERE id = ?");
            selectAll = conn.prepareStatement("SELECT * FROM Comments");
            delete = conn.prepareStatement("DELETE FROM Comments WHERE id = ?");
            update = conn.prepareStatement("UPDATE Comments SET userId = ?, threadId = ?, comment = ?, likes = ? WHERE id = ?");
            selectByUser = conn.prepareStatement("SELECT * FROM Comments WHERE userId = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dispose(){
        ConnectionFactory.getInstance().releaseConnection(conn);
    }

    public boolean insert(Comment c) {
        try {
            insert.setInt(1, c.getOwner().getId());
            insert.setInt(2, c.getThread().getId());
            insert.setString(3, c.getComment());
            insert.setInt(4, c.getLikes());
            insert.setDate(5, new Date(c.getDate().getTime()));
            insert.executeUpdate();
            ResultSet r = insert.getGeneratedKeys();
            if (r.next()){
                c.setId(r.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(int id) {
        try {
            delete.setInt(1, id);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void update(Comment c) {
        try {
            update.setInt(1, c.getOwner().getId());
            update.setInt(2, c.getThread().getId());
            update.setString(3, c.getComment());
            update.setInt(4, c.getLikes());
            update.setInt(5, c.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comment select(int id){
        try {
            select.setInt(1, id);
            ResultSet r = select.executeQuery();
            ThreadDB tdb = new ThreadDB();
            UserDB udb = new UserDB();
            while(r.next()) {
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                int threadId = r.getInt("threadId");
                Thread t = tdb.select(threadId);
                String comment = r.getString("comment");
                int likes = r.getInt("likes");
                Date created_at = r.getDate("created_at");
                Comment c = new Comment(id, u, t, comment, likes, created_at);
                return c;
            }
            tdb.dispose();
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Comment> selectAll(){
        ArrayList<Comment> ret = new ArrayList<>();
        try {
            ResultSet r = selectAll.executeQuery();
            ThreadDB tdb = new ThreadDB();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                int threadId = r.getInt("threadId");
                Thread t = tdb.select(threadId);
                String comment = r.getString("comment");
                int likes = r.getInt("likes");
                Date created_at = r.getDate("created_at");
                Comment c = new Comment(id, u, t, comment, likes, created_at);
                ret.add(c);
            }
            tdb.dispose();
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<Comment> selectByUser(int userId){
        ArrayList<Comment> ret = new ArrayList<>();
        try {
            selectByUser.setInt(1, userId);
            ResultSet r = selectByUser.executeQuery();
            ThreadDB tdb = new ThreadDB();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                User u = udb.select(userId);
                int threadId = r.getInt("threadId");
                Thread t = tdb.select(threadId);
                String comment = r.getString("comment");
                int likes = r.getInt("likes");
                Date created_at = r.getDate("created_at");
                Comment c = new Comment(id, u, t, comment, likes, created_at);
                ret.add(c);
            }
            tdb.dispose();
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<Comment> selectByThread(int threadId){
        ArrayList<Comment> ret = new ArrayList<>();
        try {
            selectByThread.setInt(1, threadId);
            ResultSet r = selectByThread.executeQuery();
            ThreadDB tdb = new ThreadDB();
            UserDB udb = new UserDB();
            while(r.next()) {
                int id = r.getInt("id");
                int userId = r.getInt("userId");
                User u = udb.select(userId);
                Thread t = tdb.select(threadId);
                String comment = r.getString("comment");
                int likes = r.getInt("likes");
                Date created_at = r.getDate("created_at");
                Comment c = new Comment(id, u, t, comment, likes, created_at);
                ret.add(c);
            }
            tdb.dispose();
            udb.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
