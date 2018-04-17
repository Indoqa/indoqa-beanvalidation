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

package com.indoqa.beanvalidation.simple;

import static org.junit.Assert.*;

import java.util.Collections;

import com.indoqa.beanvalidation.*;
import org.junit.Test;

public class MultiplePropertyValidatorTest extends AbstractValidatorTest {

    @Test
    public void testMultiplePropertiesValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("value");
        simpleBean.setNumbers(new Integer[2]);
        simpleBean.setProperty(new SimpleProperty());
        simpleBean.setMappings(Collections.singletonMap("akey", "avalue"));
        simpleBean.setMessages(Collections.singletonList("listvalue"));
        simpleBean.setSimple(true);

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getId).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getNumbers).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMappings).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMessages).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::isSimple).isTrue())
            .validateAll(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testMultiplePropertiesInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("");
        simpleBean.setNumbers(new Integer[0]);
        simpleBean.setProperty(new SimpleProperty());
        simpleBean.setMappings(Collections.emptyMap());
        simpleBean.setMessages(Collections.emptyList());
        simpleBean.setSimple(false);

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getId).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getNumbers).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMappings).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMessages).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::isSimple).isTrue())
            .validateAll(simpleBean);
        assertNotNull(result);
        assertTrue("ValidationResult should have no errors", result.hasErrors());
        assertFalse("ValidationResult should be valid", result.isValid());

        assertValidationErrors(result, "id", 1, "is_not_empty");
        assertValidationErrors(result, "numbers", 1, "is_not_empty");
        assertValidationErrors(result, "mappings", 1, "is_not_empty");
        assertValidationErrors(result, "simple", 1, "is_true");
    }

    @Test
    public void testMultiplePropertiesNull() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId(null);
        simpleBean.setNumbers(null);
        simpleBean.setProperty(null);
        simpleBean.setMappings(null);
        simpleBean.setMessages(null);
        simpleBean.setSimple(false);

        ValidationResult result = BeanValidator
            .forClass(SimpleBean.class)
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getId).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getNumbers).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMappings).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::getMessages).isNotNull().isNotEmpty())
            .addPropertyValidator(PropertyValidator.forMethod(SimpleBean::isSimple).isTrue())
            .validateAll(simpleBean);
        assertNotNull(result);
        assertTrue("ValidationResult should have no errors", result.hasErrors());
        assertFalse("ValidationResult should be valid", result.isValid());

        assertValidationErrors(result, "id", 2, "is_not_empty", "is_not_null");
        assertValidationErrors(result, "numbers", 2, "is_not_empty", "is_not_null");
        assertValidationErrors(result, "mappings", 2, "is_not_empty", "is_not_null");
        assertValidationErrors(result, "simple", 1, "is_true");
    }
}
