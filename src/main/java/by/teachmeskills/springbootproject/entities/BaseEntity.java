package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
public class BaseEntity {
    protected int id;
}
