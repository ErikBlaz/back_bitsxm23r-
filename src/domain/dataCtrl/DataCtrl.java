package domain.dataCtrl;

import data.UserDB;
import data.ThreadDB;
import data.CommentDB;

public class DataCtrl{
    static DataCtrl instance;

    public static DataCtrl getInstance(){
        if(instance == null) instance = new DataCtrl();
        return instance;
    }

    public UserDataCtrl getUserDataCtrl(){
        return new UserDB();
    }

    public CommentDataCtrl getCommentDataCtrl(){
        return new CommentDB();
    }

    public ThreadDataCtrl getThreadDataCtrl(){
        return new ThreadDB();
    }
}