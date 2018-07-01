package com.example.mostafa.gplanettask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class User {

    private int userId;
    private String userName;
    private int uniquePages;

    public User(String userName, List<Session> sessionsList) {

        this.userName = userName;
        this.uniquePages = 0;

        Set<Integer> pagesSet = new HashSet<>();
        for (Session session : sessionsList) {

            for (int i = session.getFrom(); i <= session.getTo(); ++i) {
                boolean added = pagesSet.add(i);
                if (added)
                    uniquePages++;
            }
        }
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUniquePages() {
        return uniquePages;
    }

    public void setUniquePages(int uniquePages) {
        this.uniquePages = uniquePages;
    }


    public double getReadingPercentage() {
        return this.uniquePages / 70.0;
    }
}
