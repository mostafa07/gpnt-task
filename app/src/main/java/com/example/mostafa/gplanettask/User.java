package com.example.mostafa.gplanettask;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class User implements Comparable<User> {

    private int userId;
    private String userName;
    private int uniquePages;

    public User(int userId, String userName, List<Session> sessionsList) {

        this.userId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", uniquePages=" + uniquePages +
                '}';
    }

    @Override
    public int compareTo(@NonNull User otherUser) {
        return otherUser.getUniquePages() - this.uniquePages;
    }


    public double getReadingPercentage() {
        return this.uniquePages / 70.0;
    }

}
