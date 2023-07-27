package by.teachmeskills.springbootproject.entities;

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
    private String name;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private BigDecimal balance;
    private String password;
    private String address;
    private String phoneNumber;
}