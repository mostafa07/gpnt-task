package com.example.mostafa.gplanettask;


public class Session {

    private int sessionId;
    private int from;
    private int to;
    private int userId;


    public Session(int sessionId, int from, int to, int userId) {
        this.sessionId = sessionId;
        this.from = from;
        this.to = to;
        this.userId = userId;
    }


    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int sumOfPages() {
        return this.to - this.from + 1;
    }

    public double percentageOfBook() {
        return this.sumOfPages() / 70.0;
    }
}
