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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container to hold {@link ValidationError}s for a property.
 */
public class ValidationResult {

    private Map<String, List<ValidationError>> errors = new HashMap<>();
    private String propertySeparator = ".";

    /**
     * Adds the {@link ValidationError} to this container.
     *
     * @param validationError
     */
    public void addError(ValidationError validationError) {
        List<ValidationError> validationErrors = this.errors.getOrDefault(validationError.getProperty(), new ArrayList<>());
        validationErrors.add(validationError);
        this.errors.put(validationError.getProperty(), validationErrors);
    }

    /**
     * Adds the parameters as {@link ValidationError} to this container.
     *
     * @param property      where the validation error was encountered
     * @param validationKey of the validation error
     */
    public void addError(String property, String validationKey) {
        this.addError(ValidationError.of(property, validationKey));
    }

    /**
     * Adds all the validation errors in the {@link ValidationResult} to this result.
     *
     * @param validationResult to add
     */
    public void addErrors(ValidationResult validationResult) {
        validationResult.getErrors().forEach((property, value) -> {
            this.addErrors(property, value);
        });
    }

    /**
     * Adds all the validation errors in the {@link ValidationResult} as nested property to this result.
     *
     * @param property         as parent property for the nested properties
     * @param validationResult to add
     */
    public void addErrors(String property, ValidationResult validationResult) {
        validationResult.getErrors().forEach((nestedProperty, value) -> {
            String newProperty = prependProperty(property, nestedProperty);
            value.forEach((error) -> {
                error.setProperty(newProperty);
                error.setValidationKey(error.getValidationKey());
            });
            this.addErrors(newProperty, value);
        });
    }

    private String prependProperty(String property, String nestedProperty) {
        return property + this.propertySeparator + nestedProperty;
    }

    private void addErrors(String property, List<ValidationError> errors) {
        this.errors.merge(property, errors, (originalErrors, newErrors) -> {
            originalErrors.addAll(newErrors);
            return originalErrors;
        });
    }

    /**
     * Sets the property separator for nested properties in valiationKeys
     */
    public void setPropertySeparator(String propertySeparator) {
        this.propertySeparator = propertySeparator;
    }

    /**
     * @return True if there were no errors, false otherwise.
     */
    public boolean isValid() {
        return this.errors.isEmpty();
    }

    /**
     * @return Get all the errors for this validationResult, as Map with properties as validationKeys and their validtionErrors.
     */
    public Map<String, List<ValidationError>> getErrors() {
        return errors;
    }

    /**
     * @param property where the validationErrors were encountered
     * @return Get all validationErrors as List for the given property.
     */
    public List<ValidationError> getErrors(String property) {
        return this.errors.get(property);
    }

    /**
     * @return True if there are errors, false otherwise.
     */
    public boolean hasErrors() {
        return !this.isValid();
    }

}
