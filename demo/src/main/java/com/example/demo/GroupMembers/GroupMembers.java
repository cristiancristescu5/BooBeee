package com.example.demo.GroupMembers;

public class GroupMembers {
    private Long userId;
    private String name;
    private String imageURL;

    public GroupMembers(Long userId, String name, String imageURL) {
        this.userId = userId;
        this.name = name;
        this.imageURL = imageURL;
    }
    public GroupMembers(){}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
