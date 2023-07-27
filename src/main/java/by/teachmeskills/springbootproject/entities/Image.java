package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Image extends BaseEntity{
   private String imagePath;
   private int categoryId;
   private int productId;
   private boolean primary;
}
