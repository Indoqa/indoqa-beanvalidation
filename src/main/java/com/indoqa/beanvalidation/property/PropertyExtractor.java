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
package com.indoqa.beanvalidation.property;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * Utility class for extracting the name of a property for a
 * <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html">method reference</a>.
 * <br />Internally it uses a {@link SerializedLambda} to get the method name via {@link SerializedLambda#getImplMethodName()}.
 */
public final class PropertyExtractor {

    private static final String METHOD_WRITE_REPLACE = "writeReplace";

    private static final String IS_PREFIX = "is";
    private static final String HAS_PREFIX = "has";
    private static final String GET_PREFIX = "get";

    private PropertyExtractor() {
        // hide utility class constructor
    }

    public static String getPropertyName(PropertyFunction<?, ?> propertyFunction) {
        try {
            Class<?> propertyFunctionClass = propertyFunction.getClass();
            Method method = propertyFunctionClass.getDeclaredMethod(METHOD_WRITE_REPLACE);
            method.setAccessible(true);
            Object serializedFunction = method.invoke(propertyFunction);
            if ((serializedFunction instanceof SerializedLambda)) {
                SerializedLambda l = (SerializedLambda) serializedFunction;
                return extractPropertyName(l.getImplMethodName());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error extracting property name for function.", e);
        }
        return propertyFunction.toString();
    }

    private static String extractPropertyName(String methodName) {
        if (methodName == null || methodName.isEmpty()) {
            return methodName;
        }
        String propertyName = methodName;

        if (propertyName.indexOf(IS_PREFIX) != -1) {
            propertyName = propertyName.substring(IS_PREFIX.length());
        }

        if (propertyName.indexOf(HAS_PREFIX) != -1) {
            propertyName = propertyName.substring(HAS_PREFIX.length());
        }

        if (propertyName.indexOf(GET_PREFIX) != -1) {
            propertyName = propertyName.substring(GET_PREFIX.length());
        }

        return Introspector.decapitalize(propertyName);
    }
}
