package com.example.demo.Group;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "group_", schema = "public", catalog = "BooDB")
@NamedQuery(name = "groups.findById", query = "select a from GroupEntity a where a.id = ?1 ")
@NamedQuery(name = "groups.findAll", query = "select a from GroupEntity a")
@NamedQuery(name = "groups.findByName", query = "select  a from GroupEntity a where a.name = ?1 ")
@NamedQuery(name = "groups.findIdByName", query = "select a from GroupEntity a where a.name = ?1")
public class GroupEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "createdat", nullable = true)
    private Timestamp createdat;
    @Basic
    @Column(name = "members_count", nullable = true)
    private Integer membersCount;

    public GroupEntity(){
        this.createdat = new Timestamp(System.currentTimeMillis());
        this.membersCount = 0;
    }

    public GroupEntity(String name, String description){
        this.name = name;
        this.description = description;
        this.createdat = new Timestamp(System.currentTimeMillis());
        this.membersCount = 0;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupEntity that = (GroupEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;
        if (membersCount != null ? !membersCount.equals(that.membersCount) : that.membersCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (membersCount != null ? membersCount.hashCode() : 0);
        return result;
    }
}
