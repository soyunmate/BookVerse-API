package cl.soyunmate.BookVerse.DTO.mapper;

import cl.soyunmate.BookVerse.DTO.AuthorDTO;
import cl.soyunmate.BookVerse.DTO.BookDTO;
import cl.soyunmate.BookVerse.DTO.PublisherDTO;
import cl.soyunmate.BookVerse.model.*;
import cl.soyunmate.BookVerse.model.enums.ETag;

import java.util.stream.Collectors;

public class BookMapper {

    public BookDTO toDto(Book book) {

        return BookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName())
                .genres(book.getGenre().stream().map(Genre::getName)
                        .collect(Collectors.toList()))
                .description(book.getDescription())
                .publishDate(book.getPublishDate())
                .publisher(book.getPublisher().getName())
                .language(book.getLanguage())
                .pages(book.getPages())
                .tags(book.getTags().stream().map(tg -> tg.getName().name())
                        .collect(Collectors.toList()))
                .stock(book.getStock())
                .build();
    }

    public Book toEntity(BookDTO bookDTO) {
        return Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .author(Author.builder().id(bookDTO.getAuthorID()).build())
                .genre(bookDTO.getGenreIDs().stream().map(genre -> Genre.builder().id(genre).build()).collect(Collectors.toSet()))
                .description(bookDTO.getDescription())
                .publishDate(bookDTO.getPublishDate())
                .publisher(Publisher.builder().id(bookDTO.getPublisherID()).build())
                .language(bookDTO.getLanguage())
                .pages(bookDTO.getPages())
                .tags(bookDTO.getTagsIDs().stream().map(tag -> Tag.builder().id(tag).build()).collect(Collectors.toSet()))
                .stock(bookDTO.getStock())
                .build();
    }

}
