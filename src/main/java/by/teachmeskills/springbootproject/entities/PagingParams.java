package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingParams {
    @NotNull(message = "Page number in pagingParameters is empty")
    Integer pageNumber;

    @NotNull(message = "Page size in pagingParameters is empty")
    Integer pageSize;
}
