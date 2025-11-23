package com.example.schedulerservice.model;

import java.io.Serializable;

public class Task implements Serializable {
    private String id;
    private String payload;
    private long submitTime;
    private int delaySeconds;

    public Task() {}

    public Task(String id, String payload, long submitTime, int delaySeconds) {
        this.id = id; this.payload = payload; this.submitTime = submitTime; this.delaySeconds = delaySeconds;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public long getSubmitTime() { return submitTime; }
    public void setSubmitTime(long submitTime) { this.submitTime = submitTime; }
    public int getDelaySeconds() { return delaySeconds; }
    public void setDelaySeconds(int delaySeconds) { this.delaySeconds = delaySeconds; }
}
