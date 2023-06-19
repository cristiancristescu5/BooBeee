package com.example.demo.GroupMembers;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "group_members", schema = "public", catalog = "BooDB")
@NamedQuery(name = "groupMembers.findByUserId", query = "select a from GroupMembersEntity a where a.userId = ?1 ")
@NamedQuery(name = "groupMembers.findByGroupId", query = "select a from GroupMembersEntity a where a.groupId = ?1 ")
@NamedQuery(name = "groupMembers.findAll", query = "select a from GroupMembersEntity  a")
public class GroupMembersEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "group_id", nullable = true)
    private Long groupId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMembersEntity that = (GroupMembersEntity) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
