/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package am.ik.cf.metrics.forwarder;

import java.util.Objects;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final public class Metric {

	private final String name;

	private final Long timestamp;

	private final Type type;

	private final String unit;

	private final Number value;

	Metric(org.springframework.boot.actuate.metrics.Metric<?> metric) {
		this(metric.getName(), metric.getTimestamp().getTime(), Type.GAUGE, null,
				metric.getValue());
	}

	@JsonCreator
	public Metric(@JsonProperty("name") String name,
			@JsonProperty("timestamp") Long timestamp, @JsonProperty("type") Type type,
			@JsonProperty("unit") String unit, @JsonProperty("value") Number value) {
		Assert.notNull(name, "name must not be null");
		Assert.notNull(timestamp, "timestamp must not be null");
		Assert.notNull(type, "type must not be null");
		Assert.notNull(value, "value must not be null");

		this.name = name;
		this.timestamp = timestamp;
		this.type = type;
		this.unit = unit;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Metric metric = (Metric) o;
		return Objects.equals(name, metric.name)
				&& Objects.equals(timestamp, metric.timestamp) && type == metric.type
				&& Objects.equals(unit, metric.unit)
				&& Objects.equals(value, metric.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, timestamp, type, unit, value);
	}

	@Override
	public String toString() {
		return "Metric{" + "name='" + name + '\'' + ", timestamp=" + timestamp + ", type="
				+ type + ", unit='" + unit + '\'' + ", value=" + value + '}';
	}

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	@JsonProperty("timestamp")
	public Long getTimestamp() {
		return this.timestamp;
	}

	@JsonProperty("type")
	public Type getType() {
		return this.type;
	}

	@JsonProperty("unit")
	public String getUnit() {
		return this.unit;
	}

	@JsonProperty("value")
	public Number getValue() {
		return this.value;
	}

	public String metricName() {
		return "spring_" + this.name.replace("-", "_").replace(".", "_");
	}
}