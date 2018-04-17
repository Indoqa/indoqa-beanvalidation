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

import static org.junit.Assert.assertNotNull;

import com.indoqa.beanvalidation.AbstractValidatorTest;
import com.indoqa.beanvalidation.PropertyValidator;
import com.indoqa.beanvalidation.SimpleBean;
import com.indoqa.beanvalidation.ValidationResult;
import org.junit.Test;

public class NullValidation extends AbstractValidatorTest {

    @Test
    public void isNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("value");

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getId).isNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "id", "is_null");
    }

    @Test
    public void isNullValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId(null);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getId).isNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, "id");
    }

    @Test
    public void isNotNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId(null);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getId).isNotNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "id", "is_not_null");
    }

    @Test
    public void isNotNullValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId("");

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getId).isNotNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, "id");
    }
}
