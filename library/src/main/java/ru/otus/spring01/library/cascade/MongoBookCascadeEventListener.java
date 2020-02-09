package ru.otus.spring01.library.cascade;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookCommentDao;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeEventListener extends AbstractMongoEventListener<Book> {

    private final BookCommentDao bookCommentDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private List<Field> cascadeSaveFields = new CopyOnWriteArrayList<>();

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document source = event.getSource();
        String id = source.get("_id").toString();
        bookCommentDao.deleteByBookId(id);
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        Book book = event.getSource();

        Field[] declaredFields = book.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(CascadeSave.class)) {
                if (!cascadeSaveFields.contains(declaredField)) {
                    cascadeSaveFields.add(declaredField);
                }
            }
        }
        for (Field cascadeSaveField : cascadeSaveFields) {
            if (cascadeSaveField.getType().equals(Genre.class)) {
                Genre genre = book.getGenre();
                if (Objects.nonNull(genre)) {
                    genreDao.save(genre);
                }
            }

            if (cascadeSaveField.getType().equals(Author.class)) {
                Author author = book.getAuthor();
                if (Objects.nonNull(author)) {
                    authorDao.save(author);
                }
            }
        }
    }
}
