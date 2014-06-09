package com.anphoenix.data.model;

import com.anphoenix.data.util.TypeConvertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefanie on 6/5/14.
 */
public class Person {

    //Demographic Data
    public enum Gender{FEMALE, MALE}
    String personID;
    String screenName;
    String province;
    String city;
    String regTime;
    Gender gender;
    Boolean verified;
    Integer friendsCount;
    Integer followCount;
    Integer weiboCount;
    String description;

    //Mined Interest Data
    String[] interests;
    //Mined Social Network Attribute
    Map<String, Double> influences;

    //Temp data in analytics
    Map<String, Object> tempData;

    public Person(String personID, String screenName, String province, String city, String regTime, Gender gender, Boolean verified, Integer friendsCount, Integer followCount, Integer weiboCount, String description, String[] interests, Map<String, Double> influences, Map<String, Object> tempData) {
        this.personID = personID;
        this.screenName = screenName;
        this.province = province;
        this.city = city;
        this.gender = gender;
        this.verified = verified;
        this.friendsCount = friendsCount;
        this.followCount = followCount;
        this.weiboCount = weiboCount;
        this.description = description;
        this.interests = interests;
        this.influences = influences;
        this.tempData = tempData;
    }

    public Person() {
    }

    public Person(String[] args){
        int i = 0;
        this.personID = args[i++];
        this.screenName = args[i++];
        this.province = args[i++];
        this.city = args[i++];
        this.regTime = args[i++];
        this.gender = TypeConvertor.getGender(args[i++]);
        this.verified = TypeConvertor.getBool(args[i++]);
        this.friendsCount = TypeConvertor.getInt(args[i++]);
        this.followCount = TypeConvertor.getInt(args[i++]);
        this.weiboCount = TypeConvertor.getInt(args[i++]);
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getWeiboCount() {
        return weiboCount;
    }

    public void setWeiboCount(Integer weiboCount) {
        this.weiboCount = weiboCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public Map<String, Double> getInfluences() {
        return influences;
    }

    public void setInfluences(Map<String, Double> influences) {
        this.influences = influences;
    }

    public void addInfluences(String topic, Double score){
        if(this.influences == null)
            this.influences = new HashMap<String, Double>();
        this.influences.put(topic, score);
    }

    public Map<String, Object> getTempData() {
        return tempData;
    }

    public Object getTempData(String key){
        return tempData != null ? tempData.get(key) : null;
    }

    public void setTempData(Map<String, Object> tempData) {
        this.tempData = tempData;
    }

    public void addTempData(String key, Object obj){
        if(this.tempData == null)
            this.tempData = new HashMap<String, Object>();
        tempData.put(key, obj);
    }

}
