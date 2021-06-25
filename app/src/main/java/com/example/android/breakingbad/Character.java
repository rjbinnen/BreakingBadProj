package com.example.android.breakingbad;

import android.os.Parcel;

import java.io.Serializable;

public class Character implements Serializable {
    private String name;
    private String nickName;
    private String image;
    private String status;
    private String birthday;
    private String[] occupations;
    private int[] appearance;

    public Character(String name, String nickName, String image, String status, String birthday, String[] occupations, int[] appearance) {
        this.name = name;
        this.nickName = nickName;
        this.image = image;
        this.status = status;
        this.birthday = birthday;
        this.occupations = occupations;
        this.appearance = appearance;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getBirthday() {
        return birthday;
    }

    public String[] getOccupations() {
        return occupations;
    }

    public int[] getAppearance() {
        return appearance;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nickName);
        dest.writeString(image);
        dest.writeString(status);
        dest.writeString(birthday);
        dest.writeArray(occupations);
        dest.writeIntArray(appearance);
    }
}
