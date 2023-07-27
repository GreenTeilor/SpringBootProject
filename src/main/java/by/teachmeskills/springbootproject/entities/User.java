package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class User extends BaseEntity {
    @Pattern(regexp = "[A-Za-z]+", message = "Некорректное имя")
    @NotBlank(message = "Не должно быть пустым")
    private String name;

    @Pattern(regexp = "[A-Za-z]+", message = "Некорректная фамилилия")
    @NotBlank(message = "Не должно быть пустым")
    private String lastName;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Не должно быть пустым")
    private String email;

    @Past(message = "Указана еще не наступившая дата")
    @NotNull(message = "Не введено")
    private LocalDate birthDate;

    private LocalDate registrationDate;
    private BigDecimal balance;

    @Min(value = 3, message = "Минимум 3 символа")
    private String password;

    @Size(min = 10, max = 90, message = "Длина адреса должна быть в пределах от 10 до 90 символов")
    private String address;

    @Pattern(regexp = "^\\+375((29)|(44)|(25)|(33))[0-9]{7}$", message = "Некорректный номер телефона")
    private String phoneNumber;
}