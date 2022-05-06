package com.example.spaceprovider;

public class SpaceProviderUser {
    public String name,mailid,mobile,address,area,city,state,profileImg;

    public SpaceProviderUser() {
    }

    public SpaceProviderUser(String name, String mailid, String mobile, String address, String area, String city, String state, String profileImg) {
        this.name = name;
        this.mailid = mailid;
        this.mobile = mobile;
        this.address = address;
        this.area = area;
        this.city = city;
        this.state = state;
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
