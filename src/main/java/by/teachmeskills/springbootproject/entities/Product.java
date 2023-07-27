package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Product extends BaseEntity{
    private String name;
    private String description;
    private String imagePath;
    private String category;
    private BigDecimal price;
}
