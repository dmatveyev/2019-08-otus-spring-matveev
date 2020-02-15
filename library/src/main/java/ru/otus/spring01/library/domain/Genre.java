package ru.otus.spring01.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring01.library.dto.GenreDto;

@Getter
@Setter
@ToString
@Document(collection = "Genres")
@AllArgsConstructor
public class Genre {

    @Id
    private String id;

    private String name;

    private String code;

    public Genre() {
    }

    public GenreDto toDto() {
        return new GenreDto(id, name, code);
    }

}
