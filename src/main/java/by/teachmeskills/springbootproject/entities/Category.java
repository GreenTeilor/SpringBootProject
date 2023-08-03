package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Category extends BaseEntity {
    @Size(min = 1, max = 50, message = "Пустое или длиннее 50 символов")
    private String name;

    @Size(min = 1, max = 50, message = "Пустое или длиннее 50 символов")
    private String imagePath;
}
