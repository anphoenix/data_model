package com.anphoenix.data.model;

import com.anphoenix.data.util.TypeConvertor;

/**
 * Created by stefanie on 6/5/14.
 */
public class Weibo {
    String id;
    String personID;
    Integer rtNum;
    Integer comNum;
    String createTime;
    String tweet;

    public Weibo(String id, String personID, Integer rtNum, Integer comNum, String createTime, String tweet) {
        this.id = id;
        this.personID = personID;
        this.rtNum = rtNum;
        this.comNum = comNum;
        this.createTime = createTime;
        this.tweet = tweet;
    }

    public Weibo(){

    }

    public Weibo(String[] args){
        int i = 0;
        this.id = args[i++];
        this.personID  = args[i++];
        this.rtNum = TypeConvertor.getInt(args[i++]);
        this.comNum = TypeConvertor.getInt(args[i++]);
        this.createTime = args[i++];
        this.tweet = args[i++];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Integer getRtNum() {
        return rtNum;
    }

    public void setRtNum(Integer rtNum) {
        this.rtNum = rtNum;
    }

    public Integer getComNum() {
        return comNum;
    }

    public void setComNum(Integer comNum) {
        this.comNum = comNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
