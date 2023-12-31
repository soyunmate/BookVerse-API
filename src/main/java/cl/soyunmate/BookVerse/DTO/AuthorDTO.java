package cl.soyunmate.BookVerse.DTO;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class AuthorDTO {
    private Long id;
    private String firstName;
    private  String lastName;
    private String nationality;
    private String birthDate;
    private String biography;
}
