package domain.dataCtrl;

import java.util.ArrayList;

import domain.Comment;

public interface CommentDataCtrl {
    public boolean insert(Comment u);
    public void delete(int id);
    public void update(Comment c);
    public Comment select(int id);
    public ArrayList<Comment> selectAll();
    public ArrayList<Comment> selectByThread(int idThread);
    public ArrayList<Comment> selectByUser(int idUser);
    public void dispose();
}
