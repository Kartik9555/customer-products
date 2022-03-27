package com.ecommerce.shopping.customerproducts;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

import java.util.List;

class PojoPackagesTest {
    private static final String POJO_PACKAGE_DTO = "com.ecommerce.shopping.customerproducts.datatransferobject";
    private static final String POJO_PACKAGE_ENTITY = "com.ecommerce.shopping.customerproducts.domainobject";

    private final List<PojoClass> pojoClassesDto = PojoClassFactory.getPojoClasses(POJO_PACKAGE_DTO, new FilterPackageInfo());
    private final List<PojoClass> pojoClassesEntity = PojoClassFactory.getPojoClasses(POJO_PACKAGE_ENTITY, new FilterPackageInfo());

    @Test
    void testPojoStructureAndBehavior() {
        Validator validatorModel = ValidatorBuilder.create()
                .with(new SetterTester()).with(new GetterTester()).build();
        validatorModel.validate(pojoClassesDto);
        validatorModel.validate(pojoClassesEntity);
    }

}
