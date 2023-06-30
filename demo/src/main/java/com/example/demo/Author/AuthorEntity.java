package com.example.demo.Author;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

public class AuthorEntity implements Serializable {

    private long id;
    private Date birthday;
    private Date deathday;
    private String name;
    private String description;
    private String pictureurl;
    private String websiteurl;

    public AuthorEntity(long id, Date birthday, Date deathday, String name, String description, String pictureurl, String websiteurl) {
        this.id = id;
        this.birthday = birthday;
        this.deathday = deathday;
        this.name = name;
        this.description = description;
        this.pictureurl = pictureurl;
        this.websiteurl = websiteurl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorEntity that = (AuthorEntity) o;

        if (id != that.id) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (deathday != null ? !deathday.equals(that.deathday) : that.deathday != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (pictureurl != null ? !pictureurl.equals(that.pictureurl) : that.pictureurl != null) return false;
        if (websiteurl != null ? !websiteurl.equals(that.websiteurl) : that.websiteurl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (deathday != null ? deathday.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pictureurl != null ? pictureurl.hashCode() : 0);
        result = 31 * result + (websiteurl != null ? websiteurl.hashCode() : 0);
        return result;
    }
}
