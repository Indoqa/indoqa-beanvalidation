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

import com.indoqa.beanvalidation.PropertyValidator;
import com.indoqa.beanvalidation.SimpleBean;

public class ArrayValidation extends AbstractCollectionsValidations {

    @Override
    protected String getProperty() {
        return "numbers";
    }

    @Override
    protected PropertyValidator getPropertyValidator() {
        return PropertyValidator.forMethod(SimpleBean::getNumbers);
    }

    @Override
    protected void setNonEmptyValue(SimpleBean simpleBean) {
        simpleBean.setNumbers(new Integer[1]);
    }

    @Override
    protected void setNullValue(SimpleBean simpleBean) {
        simpleBean.setNumbers(null);
    }

    @Override
    protected void setEmptyValue(SimpleBean simpleBean) {
        simpleBean.setNumbers(new Integer[0]);
    }
}
