package com.ecommerce.shopping.customerproducts.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private UUID id;

    @NotNull(message = "Title can not be null!")
    @Length(max = 255)
    private String title;

    private Instant createdAt;

    private Instant modifiedAt;
}
