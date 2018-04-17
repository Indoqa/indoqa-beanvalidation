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

import java.util.List;
import java.util.Map;

public class SimpleBean {

    private String id;
    private List<String> messages;
    private boolean isSimple;
    private Boolean isComplicated;
    private Map<String, String> mappings;
    private Integer[] numbers;
    private int[] ints;

    private SimpleProperty property;

    private NestedSimpleProperty nested;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isSimple() {
        return isSimple;
    }

    public void setSimple(boolean simple) {
        isSimple = simple;
    }

    public Boolean getComplicated() {
        return isComplicated;
    }

    public void setComplicated(Boolean complicated) {
        isComplicated = complicated;
    }

    public SimpleProperty getProperty() {
        return property;
    }

    public void setProperty(SimpleProperty property) {
        this.property = property;
    }

    public NestedSimpleProperty getNested() {
        return nested;
    }

    public void setNested(NestedSimpleProperty nested) {
        this.nested = nested;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public Integer[] getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer[] numbers) {
        this.numbers = numbers;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }
}
