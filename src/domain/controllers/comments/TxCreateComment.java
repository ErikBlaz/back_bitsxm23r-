package domain.controllers.comments;

import domain.User;

import java.util.Date;

import domain.Comment;
import domain.Thread;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.UserDataCtrl;
import domain.dataCtrl.ThreadDataCtrl;
import domain.dataCtrl.CommentDataCtrl;

public class TxCreateComment {
    
    int threadId;
    String token;
    String comment;

    int result = -1;

    private DataCtrl dataCtrl = DataCtrl.getInstance();
    private UserDataCtrl udc = dataCtrl.getUserDataCtrl();
    private ThreadDataCtrl tdc = dataCtrl.getThreadDataCtrl();
    private CommentDataCtrl cdc = dataCtrl.getCommentDataCtrl();

    public TxCreateComment(int threadId, String token, String comment) {
        this.threadId = threadId;
        this.token = token;
        this.comment = comment;
    }

    public void execute() {
        User u = udc.selectByToken(token);
        Thread t = tdc.select(threadId);
        Date created_at = new Date();
        Comment c = new Comment(-1, u, t, comment, 0, created_at);
        if (!cdc.insert(c))
            return;
        result = 0;
    }

    public int getResult() {
        return result;
    }
}

