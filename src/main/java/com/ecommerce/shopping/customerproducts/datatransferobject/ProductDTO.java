package com.ecommerce.shopping.customerproducts.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private UUID id;

    @NotNull(message = "Title can not be null!")
    @Length(max = 255)
    private String title;

    @Length(max = 1024)
    private String description;

    @Positive
    private BigDecimal price;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifiedAt;

}
