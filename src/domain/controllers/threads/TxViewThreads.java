package domain.controllers.threads;

import java.util.ArrayList;

import domain.Thread;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.ThreadDataCtrl;

public class TxViewThreads {
    
    String theme;

    ArrayList<Thread> result;

    DataCtrl dataCtrl = DataCtrl.getInstance();
    ThreadDataCtrl tdc = dataCtrl.getThreadDataCtrl();

    public TxViewThreads(String theme) {
        this.theme = theme;
        result = new ArrayList<Thread>();
    }

    public void execute() {
        if (theme.equals("all")) 
            result = tdc.selectAll();
        else 
            result = tdc.selectByTheme(theme);
    }

    public ArrayList<Thread> getResult() {
        return result;
    }
}
