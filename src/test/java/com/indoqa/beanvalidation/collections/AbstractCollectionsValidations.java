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

package com.indoqa.beanvalidation.collections;

import static org.junit.Assert.assertNotNull;

import com.indoqa.beanvalidation.AbstractValidatorTest;
import com.indoqa.beanvalidation.PropertyValidator;
import com.indoqa.beanvalidation.SimpleBean;
import com.indoqa.beanvalidation.ValidationResult;
import org.junit.Test;

public abstract class AbstractCollectionsValidations<T> extends AbstractValidatorTest {

    protected abstract String getProperty();

    protected abstract PropertyValidator<SimpleBean, T> getPropertyValidator();

    protected abstract void setNonEmptyValue(SimpleBean simpleBean);

    protected abstract void setNullValue(SimpleBean simpleBean);

    protected abstract void setEmptyValue(SimpleBean simpleBean);

    @Test
    public void isNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        setNonEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, getProperty(), "is_null");
    }

    @Test
    public void isNullValid() {
        SimpleBean simpleBean = new SimpleBean();
        setNullValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, getProperty());
    }

    @Test
    public void isNotNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        setNullValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNotNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, getProperty(), "is_not_null");
    }

    @Test
    public void isNotNullValid() {
        SimpleBean simpleBean = new SimpleBean();
        setEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNotNull().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, getProperty());
    }

    @Test
    public void isEmptyInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        setNonEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isEmpty().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, getProperty(), "is_empty");
    }

    @Test
    public void isEmptyValid() {
        SimpleBean simpleBean = new SimpleBean();
        setEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isEmpty().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, getProperty());
    }

    @Test
    public void isNotEmptyInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        setEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNotEmpty().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, getProperty(), "is_not_empty");
    }

    @Test
    public void isNotEmptyValid() {
        SimpleBean simpleBean = new SimpleBean();
        setNonEmptyValue(simpleBean);

        ValidationResult result = getPropertyValidator().isNotEmpty().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);

        assertEmptyValidationErrors(result, getProperty());
    }
}
