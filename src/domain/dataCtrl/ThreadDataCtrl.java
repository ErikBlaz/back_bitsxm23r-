package domain.dataCtrl;

import java.util.ArrayList;

import domain.Thread;

public interface ThreadDataCtrl {
    public boolean insert(Thread u);
    public void delete(int id);
    public void update(Thread u);
    public Thread select(int id);
    public ArrayList<Thread> selectAll();
    public Thread selectByUser(int idUser);
    public ArrayList<Thread> selectByTheme(String theme);
    public void dispose();
}
