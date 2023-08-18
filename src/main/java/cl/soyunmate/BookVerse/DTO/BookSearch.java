package cl.soyunmate.BookVerse.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSearch {
    private String author;
    private String genre;
    private String publisher;
    private String tag;
}
