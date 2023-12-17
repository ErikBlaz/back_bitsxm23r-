package domain.controllers.threads;

import domain.Thread;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.ThreadDataCtrl;

public class TxViewThread {
    
    int threadId;

    Thread result = null;

    DataCtrl dataCtrl = DataCtrl.getInstance();
    ThreadDataCtrl tdc = dataCtrl.getThreadDataCtrl();

    public TxViewThread(int threadId) {
        this.threadId = threadId;
    }

    public void execute() {
        result = tdc.select(threadId);
    }

    public Thread getResult() {
        return result;
    }
}
