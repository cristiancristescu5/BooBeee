package com.example.demo.User;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "users", schema = "public", catalog = "BooDB")
@NamedQueries({
        @NamedQuery(name = "user.findById",
                query = "select user from UserEntity user where id = ?1"),
        @NamedQuery(name = "user.findAll",
                query = "SELECT user FROM UserEntity user")
})
public class UserEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "pictureurl", nullable = true, length = -1)
    private String pictureurl;
    @Basic
    @Column(name = "createdat", nullable = true)
    private Timestamp createdat;
    @Basic
    @Column(name = "updatedat", nullable = true)
    private Timestamp updatedat;
    @Basic
    @Column(name = "verified", nullable = true)
    private Boolean verified;
    public UserEntity(){}

    public UserEntity(String name, String email, String password, String pictureurl, Timestamp createdat, Timestamp updatedat, Boolean verified) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pictureurl = pictureurl;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.verified = verified;
    }

    public UserEntity(long id, String name, String email, String password, String pictureurl, Timestamp createdat, Timestamp updatedat, Boolean verified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.pictureurl = pictureurl;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.verified = verified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (pictureurl != null ? !pictureurl.equals(that.pictureurl) : that.pictureurl != null) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;
        if (updatedat != null ? !updatedat.equals(that.updatedat) : that.updatedat != null) return false;
        if (verified != null ? !verified.equals(that.verified) : that.verified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (pictureurl != null ? pictureurl.hashCode() : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (updatedat != null ? updatedat.hashCode() : 0);
        result = 31 * result + (verified != null ? verified.hashCode() : 0);
        return result;
    }
}
