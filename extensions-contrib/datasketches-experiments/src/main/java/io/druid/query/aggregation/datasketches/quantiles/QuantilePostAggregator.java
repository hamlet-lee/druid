/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.aggregation.datasketches.quantiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.yahoo.sketches.quantiles.DoublesSketch;
import io.druid.query.aggregation.PostAggregator;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 */
public class QuantilePostAggregator implements PostAggregator
{
  private final String name;
  private final double fraction;
  private final String fieldName;

  @JsonCreator
  public QuantilePostAggregator(
      @JsonProperty("name") String name,
      @JsonProperty("fraction") double fraction,
      @JsonProperty("fieldName") String fieldName
  )
  {
    this.name = Preconditions.checkNotNull(name, "name is null");
    this.fraction = fraction;
    this.fieldName = Preconditions.checkNotNull(fieldName, "fieldName is null");
  }

  @Override
  public Set<String> getDependentFields()
  {
    return ImmutableSet.of(fieldName);
  }

  @Override
  public Comparator getComparator()
  {
    return Ordering.natural();
  }

  @Override
  public Object compute(Map<String, Object> combinedAggregators)
  {
    DoublesSketch sketch = ((DoublesSketchHolder) combinedAggregators.get(fieldName)).getSketch();
    return sketch.getQuantile(fraction);
  }

  @Override
  @JsonProperty
  public String getName()
  {
    return name;
  }

  @JsonProperty
  public String getFieldName()
  {
    return fieldName;
  }

  @JsonProperty
  public double getFraction()
  {
    return fraction;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    QuantilePostAggregator that = (QuantilePostAggregator) o;

    if (Double.compare(that.fraction, fraction) != 0) {
      return false;
    }
    if (!name.equals(that.name)) {
      return false;
    }
    return fieldName.equals(that.fieldName);

  }

  @Override
  public int hashCode()
  {
    int result;
    long temp;
    result = name.hashCode();
    temp = Double.doubleToLongBits(fraction);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + fieldName.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    return "QuantilePostAggregator{" +
           "name='" + name + '\'' +
           ", fraction=" + fraction +
           ", fieldName='" + fieldName + '\'' +
           '}';
  }
}