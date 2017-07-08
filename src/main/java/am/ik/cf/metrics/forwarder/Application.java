
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

final public class Application {

	private final String id;

	private final List<Instance> instances;

	@JsonCreator
	Application(@JsonProperty("id") String id,
			@JsonProperty("instances") List<Instance> instances) {
		Assert.notNull(id, "id must not be null");
		Assert.notNull(instances, "instances must not be null");

		this.id = id;
		this.instances = instances;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Application that = (Application) o;
		return Objects.equals(id, that.id) && Objects.equals(instances, that.instances);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, instances);
	}

	@Override
	public String toString() {
		return "Application{" + "id='" + id + '\'' + ", instances=" + instances + '}';
	}

	@JsonProperty("id")
	String getId() {
		return this.id;
	}

	@JsonProperty("instances")
	List<Instance> getInstances() {
		return this.instances;
	}

}