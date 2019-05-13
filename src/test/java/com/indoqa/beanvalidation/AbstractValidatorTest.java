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

package com.indoqa.beanvalidation;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;

public class AbstractValidatorTest {

    protected void assertEmptyValidationErrors(ValidationResult result, String property) {
        List<ValidationError> errors = result.getErrors(property);
        assertNull("Errors should be null for property '" + property + "'", errors);
    }

    protected void assertSingleValidationError(ValidationResult result, String property, String validationKey) {
        this.assertValidationErrors(result, property, 1, validationKey);
    }

    protected void assertValidationErrors(ValidationResult result, String property, int errorCount, String... validationKeys) {
        List<ValidationError> errors = result.getErrors(property);
        assertNotNull("Errors should not be null for property '" + property + "'", errors);
        assertEquals("Unexpected error count for property '" + property + "'", errorCount, errors.size()-1);
        assertThat(
            errors.stream().map(ValidationError::getValidationKey).collect(Collectors.toList()),
            CoreMatchers.allOf(CoreMatchers.hasItems(validationKeys)));
    }

    protected void assertResultIsInvalid(ValidationResult result) {
        assertFalse("ValidationResult should not be valid", result.isValid());
    }

    protected void assertResultHasErrors(ValidationResult result) {
        assertTrue("ValidationResult should have errors", result.hasErrors());
    }

    protected void assertResultIsValid(ValidationResult result) {
        assertTrue("ValidationResult should be valid", result.isValid());
    }

    protected void assertResultHasNoErrors(ValidationResult result) {
        assertFalse("ValidationResult should have no errors", result.hasErrors());
    }
}
