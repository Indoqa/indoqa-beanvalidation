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

import java.util.Arrays;
import java.util.Collections;

import com.indoqa.beanvalidation.*;
import org.junit.Test;

public class SimplePropertyValidatorTest extends AbstractValidatorTest {

    @Test
    public void testIsEmptyDefaultValue() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setProperty(new SimpleProperty());

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getProperty).isEmpty().validate(simpleBean);
        assertNotNull(result);
        assertFalse("ValidationResult should not have errors", result.hasErrors());
        assertResultIsValid(result);
    }

    @Test
    public void testIsNotEmptyDefaultValue() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setProperty(new SimpleProperty());

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getProperty).isNotEmpty().validate(simpleBean);
        assertNotNull(result);
        assertFalse("ValidationResult should not have errors", result.hasErrors());
        assertResultIsValid(result);
    }

    @Test
    public void testProperty() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("value");

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getId).isNull().property("ID").validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "ID", "is_null");
    }

    @Test
    public void testLambda() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("value");

        ValidationResult result = PropertyValidator
            .forLambda(SimpleBean.class, (t) -> t.getId().substring(3), "id-partial")
            .isNull()
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "id-partial", "is_null");
    }
}
