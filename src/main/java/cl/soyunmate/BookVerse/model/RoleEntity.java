package cl.soyunmate.BookVerse.model;

import cl.soyunmate.BookVerse.model.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole name;
}
