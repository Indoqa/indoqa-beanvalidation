/*
 * Licensed to the Indoqa Software Design und Beratung GmbH (Indoqa) under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Indoqa licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.indoqa.beanvalidation.nested;

import static org.junit.Assert.assertNotNull;

import com.indoqa.beanvalidation.*;
import org.junit.Test;

public class NestedPropertyValidatorTest extends AbstractValidatorTest {

    @Test
    public void testNestedProperties() {
        SimpleBean simpleBean = new SimpleBean();
        SimpleProperty property = new SimpleProperty();
        property.setItems(3);
        simpleBean.setProperty(property);

        BeanValidator<SimpleProperty> simplePropertyBeanValidator = BeanValidator
            .forClass(SimpleProperty.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleProperty::getItems).isNotNull().isNotEmpty());

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getProperty).withBeanValidator(simplePropertyBeanValidator))
            .validateAll(simpleBean);

        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testNestedPropertiesInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        SimpleProperty property = new SimpleProperty();
        property.setItems(null);
        simpleBean.setProperty(property);

        BeanValidator<SimpleProperty> simplePropertyBeanValidator = BeanValidator
            .forClass(SimpleProperty.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleProperty::getItems).isNotNull().isNotEmpty());

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getProperty).withBeanValidator(simplePropertyBeanValidator))
            .validateAll(simpleBean);

        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);
        this.assertValidationErrors(result, "property.items", 2, "is_not_null", "is_not_empty");
    }

    @Test
    public void testNestedNtestedProperty() {
        SimpleProperty simpleProperty = new SimpleProperty();
        simpleProperty.setItems(0);
        NestedSimpleProperty nested = new NestedSimpleProperty();
        nested.setSimpleProperty(simpleProperty);
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setNested(nested);

        BeanValidator<SimpleProperty> simplePropertyBeanValidator = BeanValidator
            .forClass(SimpleProperty.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleProperty::getItems).isNotNull().isNotEmpty());

        BeanValidator<NestedSimpleProperty> nestedSimplePropertyBeanValidator = BeanValidator
            .forClass(NestedSimpleProperty.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(NestedSimpleProperty::getSimpleProperty)
                .withBeanValidator(simplePropertyBeanValidator));

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(SimpleBean::getNested)
                .withBeanValidator(nestedSimplePropertyBeanValidator))
            .validateAll(simpleBean);

        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testNestedNtestedPropertyInvalid() {
        SimpleProperty simpleProperty = new SimpleProperty();
        simpleProperty.setItems(null);
        NestedSimpleProperty nested = new NestedSimpleProperty();
        nested.setSimpleProperty(simpleProperty);
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setNested(nested);

        BeanValidator<SimpleProperty> simplePropertyBeanValidator = BeanValidator
            .forClass(SimpleProperty.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleProperty::getItems).isNotNull().isNotEmpty());

        BeanValidator<NestedSimpleProperty> nestedSimplePropertyBeanValidator = BeanValidator
            .forClass(NestedSimpleProperty.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(NestedSimpleProperty::getSimpleProperty)
                .withBeanValidator(simplePropertyBeanValidator));

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(SimpleBean::getNested)
                .withBeanValidator(nestedSimplePropertyBeanValidator))
            .validateAll(simpleBean);

        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);
        this.assertValidationErrors(result, "nested.simpleProperty.items", 2, "is_not_null", "is_not_empty");
    }

    @Test
    public void testNestedNtestedPropertyInvalidSeparator() {
        SimpleProperty simpleProperty = new SimpleProperty();
        simpleProperty.setItems(null);
        NestedSimpleProperty nested = new NestedSimpleProperty();
        nested.setSimpleProperty(simpleProperty);
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setNested(nested);

        BeanValidator<SimpleProperty> simplePropertyBeanValidator = BeanValidator
            .forClass(SimpleProperty.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleProperty::getItems).isNotNull().isNotEmpty());

        BeanValidator<NestedSimpleProperty> nestedSimplePropertyBeanValidator = BeanValidator
            .forClass(NestedSimpleProperty.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(NestedSimpleProperty::getSimpleProperty)
                .propertySeparator("~")
                .withBeanValidator(simplePropertyBeanValidator))
            .addPropertyValidator(PropertyValidator
                .forMethod(NestedSimpleProperty::getNestedText)
                .isNotNull());

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator
                .forMethod(SimpleBean::getNested)
                .propertySeparator("#")
                .withBeanValidator(nestedSimplePropertyBeanValidator))
            .validateAll(simpleBean);

        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);
        this.assertValidationErrors(result, "nested#simpleProperty~items", 2, "is_not_null", "is_not_empty");
        this.assertValidationErrors(result, "nested#nestedText", 1, "is_not_null");
    }
}
