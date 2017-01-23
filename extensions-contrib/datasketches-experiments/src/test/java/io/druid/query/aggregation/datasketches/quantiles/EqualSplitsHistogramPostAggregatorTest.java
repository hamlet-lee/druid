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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 */
public class EqualSplitsHistogramPostAggregatorTest
{
  private final ObjectMapper mapper;

  public EqualSplitsHistogramPostAggregatorTest()
  {
    mapper = DoublesSketchAggregatorFactoryTest.buildObjectMapper();
  }

  @Test
  public void testSerde() throws Exception
  {
    String jsonStr = "{\n"
                     + "  \"type\": \"datasketchesEqualSplitsHistogram\",\n"
                     + "  \"name\": \"testName\",\n"
                     + "  \"numSplits\": 10,\n"
                     + "  \"fieldName\": \"testFieldName\""
                     + "}\n";

    QuantilesPostAggregatorTest.assertPostAggregatorSerde(
        mapper,
        jsonStr,
        new EqualSplitsHistogramPostAggregator(
            "testName",
            10,
            "testFieldName"
        )
    );
  }
}
