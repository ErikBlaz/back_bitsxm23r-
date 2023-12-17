package domain.controllers.users;

import domain.User;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.UserDataCtrl;

public class TxGetLoggedUser {
    
    String token;

    User result = null;

    DataCtrl dataCtrl = DataCtrl.getInstance();
    UserDataCtrl udc = dataCtrl.getUserDataCtrl();

    public TxGetLoggedUser(String token) {
        this.token = token;
    }

    public void execute() {
        result = udc.selectByToken(token);
    }

    public User getResult() {
        return result;
    }
}
