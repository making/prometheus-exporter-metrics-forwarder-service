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

import java.util.List;
import java.util.Objects;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final public class Instance {

	private final String id;

	private final String index;

	private final List<Metric> metrics;

	@JsonCreator
	Instance(@JsonProperty("id") String id, @JsonProperty("index") String index,
			@JsonProperty("metrics") List<Metric> metrics) {
		Assert.notNull(id, "id must not be null");
		Assert.notNull(index, "index must not be null");
		Assert.notNull(metrics, "metrics must not be null");

		this.id = id;
		this.index = index;
		this.metrics = metrics;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Instance instance = (Instance) o;
		return Objects.equals(id, instance.id) && Objects.equals(index, instance.index)
				&& Objects.equals(metrics, instance.metrics);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, index, metrics);
	}

	@Override
	public String toString() {
		return "Instance{" + "id='" + id + '\'' + ", index='" + index + '\''
				+ ", metrics=" + metrics + '}';
	}

	@JsonProperty("id")
	String getId() {
		return this.id;
	}

	@JsonProperty("index")
	String getIndex() {
		return this.index;
	}

	@JsonProperty("metrics")
	List<Metric> getMetrics() {
		return this.metrics;
	}

}