package ru.otus.spring01.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        if (contains(book)) {
            return;
        }
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("bookName", book.getName());
        params.put("author", book.getAuthor().getId());
        params.put("genre", book.getGenre().getId());
        params.put("isbn", book.getIsbn());
        namedParameterJdbcOperations.update(
                "insert into books (id, `name`, genre_id, isbn, author_id) values (:id, :bookName, :genre, :isbn, :author)",
                params);
    }

    @Override
    public Book getById(UUID id) {
        Map<String, UUID> params = Collections.singletonMap("id", id);
        List<Book> result = namedParameterJdbcOperations.query("select * from books b " +
                        "where b.id = :id",
                params, new BookMapper());
        return result.isEmpty() ? null: result.get(0);

    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select * from books a", new BookMapper());
    }

    @Override
    public Book getByISBN(String isbn) {
        Map<String, Object> params = Collections.singletonMap("isbn", isbn);
        List<Book> books = namedParameterJdbcOperations.query(
                "select * from books where isbn = :isbn", params, new BookMapper()
        );
        return books.isEmpty()? null: books.get(0);
    }

    @Override
    public List<Book> getByNameLike(String nameLike) {
        nameLike = ("%" + nameLike + "%").toLowerCase();
        Map<String, Object> params = Collections.singletonMap("name", nameLike);
        List<Book> list = namedParameterJdbcOperations.query(
                "select * from books where LOWER(name) like :name", params, new BookMapper()
        );
        return list;
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        Map<String, Object> params = Collections.singletonMap("genre", genre.getId());
        return namedParameterJdbcOperations.query(
                "select * from books where genre_id = :genre", params,  new BookMapper()
        );
    }

    @Override
    public List<Book> getByAuthorName(String authorName) {
        Map<String, String> params = Collections.singletonMap("authorName", authorName);
        return namedParameterJdbcOperations.query("select b.* from books b " +
                "left join authors a on b.author_id = a.id " +
                "where a.name = :authorName", params, new BookMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    @Override
    public boolean contains(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("bookName", book.getName());
        params.put("authorName", book.getAuthor().getName());
        params.put("genreId", book.getGenre().getId());
        params.put("isbn", book.getIsbn());
        Integer bookCount = namedParameterJdbcOperations.queryForObject(
                "select count(b.*) from books b " +
                        " left join authors a on a.id = b.author_id " +
                        " where b.name = :bookName and a.name = :authorName and b.genre_id = :genreId " +
                        "and b.isbn = :isbn",
                params, Integer.class);
        return bookCount > 0;
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String genreId = resultSet.getString("genre_id");
            String isbn = resultSet.getString("isbn");
            String authorId = resultSet.getString("author_id");
            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setIsbn(isbn);
            if (genreId != null) {
                Genre genre = genreDao.getById(UUID.fromString(genreId));
                book.setGenre(genre);
            }
            if (authorId != null) {
                Author author = authorDao.getById(UUID.fromString(authorId));
                book.setAuthor(author);
            }
            return book;
        }
    }
}
