package com.yibao.tablayoutdemo;

/**
 * @ Author: Luoshipeng
 * @ Name:   UserInfo
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/6/9/ 15:16
 * @ Des:    //TODO
 */
public class UserInfo {
    private String imageUrl;
    private String userName;

    public UserInfo(String imageUrl, String userName) {
        this.imageUrl = imageUrl;
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
