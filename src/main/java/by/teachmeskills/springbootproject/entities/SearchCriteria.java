package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SearchCriteria extends PagingParams{
    private String keyWords;
    private String searchCategory;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
}
