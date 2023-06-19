package com.example.demo.BookGenres;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "book_genres", schema = "public", catalog = "BooDB")
@NamedQueries({
        @NamedQuery(name = "book_genre.findByGenreId",
                query = "select b from BookGenresEntity b where b.genreId = ?1"),
        @NamedQuery(name = "book_genre.findByBookId",
                query = "select b from BookGenresEntity b where b.bookId = ?1")
})
public class BookGenresEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "book_id", nullable = true)
    private Long bookId;
    @Basic
    @Column(name = "genre_id", nullable = true)
    private Long genreId;

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

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookGenresEntity that = (BookGenresEntity) o;

        if (id != that.id) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (genreId != null ? !genreId.equals(that.genreId) : that.genreId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        return result;
    }
}
