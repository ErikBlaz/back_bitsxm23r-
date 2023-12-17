package domain.controllers.users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import domain.User;
import domain.dataCtrl.DataCtrl;
import domain.dataCtrl.UserDataCtrl;

public class TxRegister {
    
    int result = -1;
    String username;
    String password;
    String email;

    private DataCtrl dataCtrl = DataCtrl.getInstance();
    private UserDataCtrl udc = dataCtrl.getUserDataCtrl();

    public TxRegister(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void execute() {

        if (udc.selectByUsername(username) != null)
            return;
        if (udc.selectByEmail(email) != null) 
            return;
        
        String token = hash(new Date().toString(), username);
        String hash = hash(username, "username");
        password = hash(password, "password");

        User u = new User(username, hash, password, email, token);

        if (!udc.insert(u))
            return;
        result = 0;
    }

    public int getResult() {
        dispose();
        return this.result;
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
