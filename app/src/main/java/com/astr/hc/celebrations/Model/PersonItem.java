package com.astr.hc.celebrations.Model;

public class PersonItem {
    private String personName;
    private String personid;
    private String photos;
    private String videos;
    private String amount;

    public PersonItem(String personName, String personid, String photos, String videos, String amount) {
        this.personName = personName;
        this.personid = personid;
        this.photos = photos;
        this.videos = videos;
        this.amount = amount;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
