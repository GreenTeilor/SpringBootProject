package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Order extends BaseEntity{
    private LocalDate date;
    private List<Product> products;
    private int userId;
    private BigDecimal price;
}
