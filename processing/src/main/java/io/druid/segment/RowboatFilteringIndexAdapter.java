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

package io.druid.segment;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import io.druid.segment.column.BitmapIndexSeeker;
import io.druid.segment.column.ColumnCapabilities;
import io.druid.segment.data.Indexed;
import io.druid.segment.data.IndexedInts;
import org.joda.time.Interval;

/**
 */
public class RowboatFilteringIndexAdapter implements IndexableAdapter
{
  private final IndexableAdapter baseAdapter;
  private final Predicate<Rowboat> filter;

  public RowboatFilteringIndexAdapter(IndexableAdapter baseAdapter, Predicate<Rowboat> filter)
  {
    this.baseAdapter = baseAdapter;
    this.filter = filter;
  }

  @Override
  public Interval getDataInterval()
  {
    return baseAdapter.getDataInterval();
  }

  @Override
  public int getNumRows()
  {
    return baseAdapter.getNumRows();
  }

  @Override
  public Indexed<String> getDimensionNames()
  {
    return baseAdapter.getDimensionNames();
  }

  @Override
  public Indexed<String> getMetricNames()
  {
    return baseAdapter.getMetricNames();
  }

  @Override
  public Indexed<String> getDimValueLookup(String dimension)
  {
    return baseAdapter.getDimValueLookup(dimension);
  }

  @Override
  public Iterable<Rowboat> getRows()
  {
    return Iterables.filter(baseAdapter.getRows(), filter);
  }

  @Override
  public IndexedInts getBitmapIndex(String dimension, String value)
  {
    return baseAdapter.getBitmapIndex(dimension, value);
  }

  @Override
  public String getMetricType(String metric)
  {
    return baseAdapter.getMetricType(metric);
  }

  @Override
  public ColumnCapabilities getCapabilities(String column)
  {
    return baseAdapter.getCapabilities(column);
  }

  @Override
  public BitmapIndexSeeker getBitmapIndexSeeker(String dimension)
  {
    return baseAdapter.getBitmapIndexSeeker(dimension);
  }
}
