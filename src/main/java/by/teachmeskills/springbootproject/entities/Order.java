package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @Past(message = "Дата еще не наступила")
    @NotNull(message = "Не введено")
    private LocalDate date;

    @NotNull(message = "Не введено")
    private List<Product> products;

    @NotNull(message = "Не введено")
    private int userId;

    @Digits(integer = 6, fraction = 2, message = "Не соответствует формату цены")
    private BigDecimal price;
}
