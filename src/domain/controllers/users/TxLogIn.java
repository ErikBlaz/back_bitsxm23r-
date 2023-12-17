package domain.controllers.users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import domain.User;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.UserDataCtrl;

public class TxLogIn {
    private String result;
    private String password;
    private String username;

    private DataCtrl dataCtrl = DataCtrl.getInstance();
    private UserDataCtrl udc = dataCtrl.getUserDataCtrl();

    public TxLogIn(String username, String password){
        this.username = username;
        this.password = password;
        this.result = "null";
    }

    public void execute(){
        password = hash(password, "password");

        User u = udc.selectByUsername(username);
        if(u == null) 
            u = udc.selectByEmail(username);
        if(u == null) 
            return;
        if(u.getPassword().equals(password)) 
            result = u.getToken();
    }

    public String getResult(){
        dispose();
        return result;
    }

    private String hash(String text, String word) {
		MessageDigest digest;
		String hash = "";
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
		    digest.update((text+word).getBytes());
		    hash = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

    private void dispose(){
        udc.dispose();
    }
}
