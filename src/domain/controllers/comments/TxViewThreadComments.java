package domain.controllers.comments;

import java.util.ArrayList;

import domain.Comment;
import domain.dataCtrl.CommentDataCtrl;
import domain.dataCtrl.DataCtrl;

public class TxViewThreadComments {

    int threadId;

    ArrayList<Comment> result;

    DataCtrl dataCtrl = DataCtrl.getInstance();
    CommentDataCtrl cdc = dataCtrl.getCommentDataCtrl();

    public TxViewThreadComments(int threadId) {
        this.threadId = threadId;
        result = new ArrayList<>();
    }

    public void execute() {
        result = cdc.selectByThread(threadId);
    }

    public ArrayList<Comment> getResult() {
        return result;
    }
}
