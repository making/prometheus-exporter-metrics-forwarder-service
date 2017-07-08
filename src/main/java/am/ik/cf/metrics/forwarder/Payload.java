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

final public class Payload {

	static final long VERSION = 1;

	private final List<Application> applications;

	@JsonCreator
	Payload(@JsonProperty("applications") List<Application> applications) {
		Assert.notNull(applications, "applications must not be null");
		this.applications = applications;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Payload payload = (Payload) o;
		return Objects.equals(applications, payload.applications);
	}

	@Override
	public int hashCode() {
		return Objects.hash(applications);
	}

	@Override
	public String toString() {
		return "Payload{" + "applications=" + applications + '}';
	}

	@JsonProperty("applications")
	List<Application> getApplications() {
		return this.applications;
	}

	@JsonProperty("version")
	long getVersion() {
		return VERSION;
	}

}