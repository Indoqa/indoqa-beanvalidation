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

public class BooleanValidation extends AbstractValidatorTest {

    @Test
    public void testIsFalseLambdaInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(Collections.emptyList());

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isFalse("size_lower_1", t -> t.size() <= 1)
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "messages", "size_lower_1");
    }

    @Test
    public void testIsFalseLambdaNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(null);

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isFalse("size_lower_1", t -> t.size() <= 1)
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "messages", "size_lower_1");
    }

    @Test
    public void testIsFalseLambdaValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(Arrays.asList("a", "b"));

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isFalse("size_lower_1", t -> t.size() <= 1)
            .validate(simpleBean);
        assertNotNull(result);
        assertFalse("ValidationResult should not have errors", result.hasErrors());
        assertResultIsValid(result);
    }

    @Test
    public void testIsTrueLambdaInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(Collections.emptyList());

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isTrue("size_greater_0", t -> t.size() > 0)
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "messages", "size_greater_0");
    }

    @Test
    public void testIsTrueLambdaNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(null);

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isTrue("size_greater_0", t -> t.size() > 0)
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "messages", "size_greater_0");
    }

    @Test
    public void testIsTrueLambdaValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setMessages(Arrays.asList("value"));

        ValidationResult result = PropertyValidator
            .forMethod(SimpleBean::getMessages)
            .isTrue("size_greater_0", t -> t.size() > 0)
            .validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsFalseInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setSimple(true);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::isSimple).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "simple", "is_false");
    }

    @Test
    public void testIsFalseValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setSimple(false);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::isSimple).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsTrueInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setSimple(false);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::isSimple).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "simple", "is_true");
    }

    @Test
    public void testIsTrueValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setSimple(true);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::isSimple).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsTrueBooleanInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(false);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "complicated", "is_true");
    }

    @Test
    public void testIsTrueBooleanNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(null);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "complicated", "is_true");
    }

    @Test
    public void testIsTrueBooleanValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(true);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsFalseBooleanInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(true);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "complicated", "is_false");
    }

    @Test
    public void testIsTrueObjectValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setProperty(new SimpleProperty());

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getProperty).isTrue().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsFalseBooleanNullInvalid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(null);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasErrors(result);
        assertResultIsInvalid(result);

        assertSingleValidationError(result, "complicated", "is_false");
    }

    @Test
    public void testIsFalseBooleanNullValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setComplicated(false);

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getComplicated).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }

    @Test
    public void testIsFalseObjectValid() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setProperty(new SimpleProperty());

        ValidationResult result = PropertyValidator.forMethod(SimpleBean::getProperty).isFalse().validate(simpleBean);
        assertNotNull(result);
        assertResultHasNoErrors(result);
        assertResultIsValid(result);
    }
}
