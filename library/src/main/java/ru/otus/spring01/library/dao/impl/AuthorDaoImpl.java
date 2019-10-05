package ru.otus.spring01.library.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from authors", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Author author) {
        if (author.getId() == null) {
            author.setId(UUID.randomUUID());
        }
        namedParameterJdbcOperations.update(
                "insert into authors (id, `name`) values (:id, :name)", new BeanPropertySqlParameterSource(author));
    }

    @Override
    public Author getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from authors a " +
                        " left join books b on a.id = b.author_id " +
                        "where a.id = :id",
                params, new AuthorWithBooksMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from authors a " +
                " left join books b on a.id = b.author_id ", new AuthorWithBooksMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    private static class AuthorWithBooksMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            Author author = new Author();
            author.setId(id);
            author.setName(name);
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                String bookUuid = resultSet.getString(3);
                String bookName = resultSet.getString(4);
                String bookGenre = resultSet.getString(5);
                String bookIsbn= resultSet.getString(6);
                Book book = new Book();
                book.setId(UUID.fromString(bookUuid));
                book.setGenre(Genre.getGenreByCode(bookGenre));
                book.setIsbn(bookIsbn);
                book.setName(bookName);
                book.setAuthor(author);
                books.add(book);
            }
            author.setBooks(books);
            return author;
        }
    }
}
