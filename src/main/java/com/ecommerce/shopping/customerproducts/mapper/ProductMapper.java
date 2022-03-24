package com.ecommerce.shopping.customerproducts.mapper;

import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
    }

    public static List<ProductDTO> makeProductDTOList(Collection<ProductDO> products){
        return products.stream()
                .map(ProductMapper::makeProductDTO)
                .collect(Collectors.toList());
    }

    public static ProductDTO makeProductDTO(ProductDO product){
        return new ProductDTO(product.getId(), product.getTitle(), product.getDescription(), product.getPrice(), product.getCreatedAt(), product.getModifiedAt());
    }

    public static ProductDO makeProductDO(ProductDTO product){
        return new ProductDO(product.getTitle(), product.getDescription(), product.getPrice());
    }
}
