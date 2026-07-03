package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String genre;
    @Positive
    private int publicationYear;
}