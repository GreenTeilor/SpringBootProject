package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Product extends BaseEntity{
    @Size(min = 1, max = 50, message = "Пустое или длиннее 50 символов")
    private String name;

    @Size(min = 1, max = 350, message = "Пустое или длиннее 350 символов")
    private String description;

    @Size(min = 1, max = 50, message = "Пустое или длиннее 50 символов")
    private String imagePath;

    @Size(min = 1, max = 50, message = "Пустое или длиннее 50 символов")
    private String category;

    @Digits(integer = 6, fraction = 2, message = "Не соответствует формату цены")
    private BigDecimal price;
}
