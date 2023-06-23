package com.example.demo.BookStatus;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "book_status", schema = "public", catalog = "BooDB")
@NamedQueries({
        @NamedQuery(name = "book_status.findByBookId",
                query = "select b from BookStatusEntity b where b.bookId = ?1"),
        @NamedQuery(name = "book_status.findByUserId",
                query = "select b from BookStatusEntity b where b.userId = ?1 "),
        @NamedQuery(name = "book_status.findByUserIdAndStatus",
                query = "select e from BookStatusEntity e where e.userId = ?1 and e.status = ?2"),
        @NamedQuery(name = "book_status.findByBookIdAndBookId",
                query = "select e from BookStatusEntity e where e.bookId = ?1 and e.userId = ?2")
})
public class BookStatusEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "book_id", nullable = true)
    private Long bookId;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "status", nullable = false, length = -1)
    private String status;

    public BookStatusEntity(Long id, Long bookId, String bookStatus) {
        this.userId = id;
        this.bookId = bookId;
        this.status = bookStatus;
    }
    public BookStatusEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStatusEntity that = (BookStatusEntity) o;

        if (id != that.id) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
