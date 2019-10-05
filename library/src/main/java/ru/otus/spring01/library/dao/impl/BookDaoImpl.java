package ru.otus.spring01.library.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    private final AuthorDao authorDao;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorDao authorDao) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("bookName", book.getName());
        params.put("author", book.getAuthor().getId());
        params.put("genre", book.getGenre().getCode());
        params.put("isbn", book.getIsbn());
        namedParameterJdbcOperations.update(
                "insert into books (id, `name`, genre, isbn, author_id) values (:id, :bookName, :genre, :isbn, :author)",
                params);
    }

    @Override
    public Book getById(UUID id) {
        Book book = new Book();
        book.setId(id);
        return namedParameterJdbcOperations.queryForObject("select * from book b " +
                        "where a.id = :id",
                new BeanPropertySqlParameterSource(book), Book.class);

    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select * from authors a", new BookMapper());
    }

    @Override
    public Book getByISBN(String isbn) {
        Map<String, Object> params = Collections.singletonMap("isbn", isbn);
        return namedParameterJdbcOperations.queryForObject(
                "select * from books where isbn = :isbn", params,  new BookMapper()
        );
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
        Map<String, Object> params = Collections.singletonMap("genre", genre.getCode());
        return namedParameterJdbcOperations.query(
                "select * from books where genre = :genre", params,  new BookMapper()
        );
    }

    @Override
    public List<Book> getByAuthorName(String authorName) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String genre = resultSet.getString("genre");
            String isbn = resultSet.getString("isbn");
            String author = resultSet.getString("author_id");
            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setIsbn(isbn);
            book.setGenre(Genre.getGenreByCode(genre));
            if (author != null) {
                book.setAuthor(authorDao.getById(UUID.fromString(author)));
            }
            return book;
        }
    }
}
