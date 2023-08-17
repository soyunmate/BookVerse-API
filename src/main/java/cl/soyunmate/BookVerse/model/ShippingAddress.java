package cl.soyunmate.BookVerse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipping_address")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*@NotBlank
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UserEntity.class)
    private UserEntity user;*/
    @NotBlank
    private String name;
    @NotBlank
    private Long postalCode;
    @NotBlank
    private String streetName;
    @NotBlank
    private Integer streetNumber;

    private String department;

    private String comments;
}
