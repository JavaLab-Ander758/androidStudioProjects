package com.andyeseapps.volleyogrest.model;

// Might need to hardcode url and thumbnailUrl, see Lab5.1 module apge
public class Photo {
    private int albumID;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public Photo() {
        this.albumID = -1;
        this.id = -1;
        this.title = "nullTitle";
        this.url = "nullUrl";
        this.thumbnailUrl = "nullThumbnailUrl";
    }

    public Photo(int albumID, int id, String title, String url, String thumbnailUrl) {
        this.albumID = albumID;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = url;
    }



    /* Getters & Setters */
    public int getAlbumID() {
        return albumID;
    }
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
