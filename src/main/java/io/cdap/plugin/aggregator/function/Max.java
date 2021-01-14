/*
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.aggregator.function;

import io.cdap.cdap.api.data.schema.Schema;

/**
 * Calculates max values of a field in a group.
 */
public class Max extends CompareFunction<Object, Max> {

  public Max(String fieldName, Schema fieldSchema) {
    super(fieldName, fieldSchema);
  }

  @Override
  public Object compare(Object value, Object otherValue) {
    if (otherValue == null) {
      return value;
    }

    if (value == null) {
      return otherValue;
    }

    switch (fieldType) {
      case INT:
        return Math.max((Integer) otherValue, (Integer) value);
      case LONG:
        return Math.max((Long) otherValue, (Long) value);
      case FLOAT:
        return Math.max((Float) otherValue, (Float) value);
      case DOUBLE:
        return Math.max((Double) otherValue, (Double) value);
      case STRING:
        return ((String) value).compareToIgnoreCase((String) otherValue) > 0 ? value : otherValue;
      default:
        throw new IllegalArgumentException(String.format("Field '%s' is of unsupported non-numeric type '%s'. ",
                                                         fieldName, fieldType));
    }
  }
}
