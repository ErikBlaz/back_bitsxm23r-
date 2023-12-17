package domain.dataCtrl;

import java.util.ArrayList;

import domain.User;

public interface UserDataCtrl {
    public boolean insert(User u);
    public void delete(int id);
    public void update(User u);
    public User select(int id);
    public ArrayList<User> selectAll();
    public User selectByUsername(String un);
    public User selectByToken(String token);
    public User selectByEmail(String email);
    public void dispose();
}
