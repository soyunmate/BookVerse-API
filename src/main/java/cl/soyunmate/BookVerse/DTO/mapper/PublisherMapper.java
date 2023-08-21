package cl.soyunmate.BookVerse.DTO.mapper;

import cl.soyunmate.BookVerse.DTO.PublisherDTO;
import cl.soyunmate.BookVerse.model.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDTO toDto(Publisher publisher) {
        return PublisherDTO
                .builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .description(publisher.getDescription())
                .webSite(publisher.getWebSite())
                .build();
    }

    public Publisher toEntity(PublisherDTO publisherDTO) {
        return Publisher.builder()
                .name(publisherDTO.getName())
                .description(publisherDTO.getDescription())
                .webSite(publisherDTO.getWebSite())
                .build();
    }
}
