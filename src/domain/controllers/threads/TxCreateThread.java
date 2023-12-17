package domain.controllers.threads;

import java.util.Date;

import domain.User;
import domain.Thread;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.ThreadDataCtrl;
import domain.dataCtrl.UserDataCtrl;

public class TxCreateThread {

    String token;
    String title;
    String description;
    String theme;

    int result = -1;

    DataCtrl dataCtrl = DataCtrl.getInstance();
    UserDataCtrl udc = dataCtrl.getUserDataCtrl();
    ThreadDataCtrl tdc = dataCtrl.getThreadDataCtrl();
    
    public TxCreateThread(String token, String title, String description, String theme) {
        this.token = token;
        this.title = title;
        this.description = description;
        this.theme = theme;
    }

    public void execute() {
        User u = udc.selectByToken(token);
        if (u == null)
            return;
        Date created_at = new Date();
        Thread t = new Thread(-1, u, title, description, theme, created_at, 0);

        if (!tdc.insert(t))
            return;
        result = 0;
    }

    public int getResult() {
        return result;
    }
}
