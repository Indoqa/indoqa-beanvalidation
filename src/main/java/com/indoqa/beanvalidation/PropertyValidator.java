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

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

import com.indoqa.beanvalidation.property.PropertyExtractor;
import com.indoqa.beanvalidation.property.PropertyFunction;

/**
 * The purpose of the {@link PropertyValidator} is the creation of the validation functions and their constraints for a given
 * method or lambda expression on a bean.
 *
 * @param <P> the type of the property (method) - usually handled by the compiler for a given method reference.
 * @param <R> the return type of the method - usually handled by the compiler for a given method reference.
 */
public class PropertyValidator<P, R extends Object> {

    private PropertyFunction<P, R> function;
    private String property;
    private String propertySeparator = ".";

    private Map<Predicate, String> validationsMustBeTrue = new HashMap<>();
    private Map<Predicate, String> validationsMustBeFalse = new HashMap<>();
    private List<BeanValidator> beanValidators = new ArrayList<>();

    /**
     * Create a validator for the given method reference (property of a bean).
     *
     * @param function the method reference to validate
     * @return A PropertyValidator for a given method.
     */
    public static <P, R> PropertyValidator<P, R> forMethod(PropertyFunction<P, R> function) {
        return forMethod(function, null);
    }

    /**
     * Create a validator for the given method reference (property of a bean), locks the name of the property (for validation messages).
     *
     * @param function the method reference to validate
     * @param property the name of the property (for validation messages)
     * @return A PropertyValidator for a given method.
     */
    public static <P, R> PropertyValidator<P, R> forMethod(PropertyFunction<P, R> function, String property) {
        PropertyValidator validator = new PropertyValidator();
        validator.setFunction(function);
        validator.setProperty(property);
        return validator;
    }

    /**
     * Create a validator for the given lambda expression, locks the name of the property (for validation
     * messages).
     *
     * @param clazz    the beans class to help the compiler with type interference
     * @param function the lambda expression to validate
     * @param property the name of the property (for validation messages)
     * @return A PropertyValidator for a given method.
     */
    public static <P, R> PropertyValidator<P, R> forLambda(@SuppressWarnings("unused") Class<P> clazz, PropertyFunction<P, R> function,
        String property) {
        PropertyValidator validator = new PropertyValidator();
        validator.setFunction(function);
        validator.setProperty(property);
        return validator;
    }

    private void addValidatesIfTrue(String key, Predicate predicate) {
        this.validationsMustBeTrue.put(predicate, key);
    }

    private void addValidatesIfFalse(String key, Predicate predicate) {
        this.validationsMustBeFalse.put(predicate, key);
    }

    private void setFunction(PropertyFunction<P, R> function) {
        this.function = function;
    }

    private void setProperty(String property) {
        this.property = property;
    }

    private void setPropertySeparator(String propertySeparator) {
        this.propertySeparator = propertySeparator;
    }

    /**
     * Assigns a separator for nested properties for validation messages.
     */
    public PropertyValidator<P, R> propertySeparator(String propertySeparator) {
        this.setPropertySeparator(propertySeparator);
        return this;
    }

    /**
     * Assigns a property name for validation messages.
     */
    public PropertyValidator<P, R> property(String property) {
        this.setProperty(property);
        return this;
    }

    /**
     * Validates that the property is not null.
     */
    public PropertyValidator<P, R> isNotNull() {
        this.addValidatesIfTrue("is_not_null", (t) -> this.function.apply((P) t) != null);
        return this;
    }

    /**
     * Validates that the property is null.
     */
    public PropertyValidator<P, R> isNull() {
        this.addValidatesIfTrue("is_null", (t) -> this.function.apply((P) t) == null);
        return this;
    }

    /**
     * Validates that the property is empty.
     * The method will:
     * <ul>
     * <li>not validate if the property is <b>null</b></li>
     * <li>validate if the property is something else than ({@link String}, {@link Collection}, {@link Map} or an {@link Array})</li>
     * </ul>
     */
    public PropertyValidator<P, R> isEmpty() {
        this.addValidatesIfTrue("is_empty", (t) -> {
            Object value = this.function.apply((P) t);
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return ((String) value).isEmpty();
            }
            if (value instanceof Collection) {
                return ((Collection) value).isEmpty();
            }
            if (value instanceof Map) {
                return ((Map) value).isEmpty();
            }
            if (value instanceof Object[]) {
                return ((Object[]) value).length == 0;
            }
            if (value.getClass().isArray()) {
                return Array.getLength(value) == 0;
            }

            return true;
        });
        return this;
    }

    /**
     * Validates that the property is not empty.
     * <br/>
     * The method will:
     * <ul>
     * <li>not validate if the property is <b>null</b></li>
     * <li>validate if the property is something else than ({@link String}, {@link Collection}, {@link Map} or an {@link Array})</li>
     * </ul>
     */
    public PropertyValidator<P, R> isNotEmpty() {
        this.addValidatesIfFalse("is_not_empty", (t) -> {
            Object value = this.function.apply((P) t);
            if (value == null) {
                return true;
            }
            if (value instanceof String) {
                return ((String) value).isEmpty();
            }
            if (value instanceof Collection) {
                return ((Collection) value).isEmpty();
            }
            if (value instanceof Map) {
                return ((Map) value).isEmpty();
            }
            if (value instanceof Object[]) {
                return ((Object[]) value).length == 0;
            }
            if (value.getClass().isArray()) {
                return Array.getLength(value) == 0;
            }

            return false;
        });
        return this;
    }

    /**
     * Validates that the property is <b>true</b>.
     * <br/>
     * The method will:
     * <ul>
     * <li>not validate if the property is <b>null</b></li>
     * <li>validate if the property is not a {@link Boolean}</li>
     * </ul>
     */
    public PropertyValidator<P, R> isTrue() {
        this.addValidatesIfTrue("is_true", (t) -> {
            Object value = this.function.apply((P) t);
            if (value == null) {
                return false;
            }
            if (value instanceof Boolean) {
                return ((Boolean) value);
            }
            return true;
        });
        return this;
    }

    /**
     * Validates that the property is <b>true</b>.
     * <br/>
     * The method will:
     * <ul>
     * <li>not validate if the property is <b>null</b></li>
     * <li>validate if the property is not a {@link Boolean}</li>
     * </ul>
     */
    public PropertyValidator<P, R> isFalse() {
        this.addValidatesIfFalse("is_false", (t) -> {
            Object value = this.function.apply((P) t);
            if (value == null) {
                return true;
            }
            if (value instanceof Boolean) {
                return ((Boolean) value);
            }
            return false;
        });
        return this;
    }

    /**
     * Validates that the result of the predicate evaluation is <b>true</b>.
     * If the property is null it does not validate.
     *
     * @param key       for the validation error.
     * @param predicate to test against
     */
    public PropertyValidator<P, R> isTrue(String key, Predicate<R> predicate) {
        this.addValidatesIfTrue(key, (t) -> {
            R value = this.function.apply((P) t);
            if (value == null) {
                return false;
            }
            return predicate.test(value);
        });
        return this;

    }

    /**
     * Validates that the result of the predicate evaluation is <b>false</b>.
     * If the property is null it does not validate.
     *
     * @param key       for the validation error.
     * @param predicate to test against
     */
    public PropertyValidator<P, R> isFalse(String key, Predicate<R> predicate) {
        this.addValidatesIfFalse(key, (t) -> {
            R value = this.function.apply((P) t);
            if (value == null) {
                return true;
            }
            return predicate.test(value);
        });
        return this;
    }

    /**
     * Evaluates all given validation rules against the &lt;P&gt; bean.
     *
     * @param toValidate the &lt;P&gt; bean to validate
     * @return ValidationResult as container for validation errors.
     */
    public ValidationResult validate(P toValidate) {
        ValidationResult result = new ValidationResult();
        result.setPropertySeparator(this.propertySeparator);

        if (this.property == null) {
            this.property = PropertyExtractor.getPropertyName(this.function);
        }

        for (Map.Entry<Predicate, String> eachEntry : this.validationsMustBeTrue.entrySet()) {
            Predicate predicate = eachEntry.getKey();
            if (!predicate.test(toValidate)) {
                result.addError(this.property, eachEntry.getValue());
            }
        }

        for (Map.Entry<Predicate, String> eachEntry : this.validationsMustBeFalse.entrySet()) {
            Predicate predicate = eachEntry.getKey();
            if (predicate.test(toValidate)) {
                result.addError(this.property, eachEntry.getValue());
            }
        }

        for (BeanValidator beanValidator : beanValidators) {
            result.addErrors(this.property, beanValidator.validateAll(this.function.apply((P) toValidate)));
        }

        return result;
    }

    /**
     * Adds another {@link BeanValidator} to validate nested objects.
     *
     * @param beanValidator for the nested object.
     */
    public PropertyValidator<P, ?> withBeanValidator(BeanValidator<?> beanValidator) {
        this.beanValidators.add(beanValidator);
        return this;
    }
}
