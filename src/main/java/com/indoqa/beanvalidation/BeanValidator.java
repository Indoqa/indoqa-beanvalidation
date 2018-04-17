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
import java.util.List;
import java.util.function.BiFunction;

public class BeanValidator<T> {

    private List<PropertyValidator> propertyValidators = new ArrayList<>();
    private List<BiFunction<T, PropertyValidator, ValidationResult>> nestedValidators = new ArrayList<>();

    private BeanValidator() {
        super();
    }

    public static <T> BeanValidator<T> forClass(@SuppressWarnings("unused") Class<T> t) {
        return new BeanValidator();
    }

    public BeanValidator<T> addPropertyValidator(PropertyValidator<T, ?> propertyValidator) {
        this.propertyValidators.add(propertyValidator);
        return this;
    }

    public ValidationResult validateAll(T toValidate) {
        ValidationResult result = new ValidationResult();
        this.propertyValidators.forEach(propertyValidator -> {
            ValidationResult validationResult = propertyValidator.validate(toValidate);
            if (validationResult.hasErrors()) {
                result.addErrors(validationResult);
            }
        });
        return result;
    }
}