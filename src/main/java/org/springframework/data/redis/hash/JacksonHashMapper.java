/*
 * Copyright 2011-2013 the original author or authors.
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
package org.springframework.data.redis.hash;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

/**
 * Mapper based on Jackson library. Supports nested properties (rich objects).
 * 
 * @author Costin Leau
 */
public class JacksonHashMapper<T> implements HashMapper<T, String, Object> {

	private final ObjectMapper mapper;
	private final JavaType userType;
	private final JavaType mapType = TypeFactory.mapType(Map.class, String.class, Object.class);

	public JacksonHashMapper(Class<T> type) {
		this(type, new ObjectMapper());
	}

	public JacksonHashMapper(Class<T> type, ObjectMapper mapper) {
		this.mapper = mapper;
		this.userType = TypeFactory.type(type);
	}

	@SuppressWarnings("unchecked")
	public T fromHash(Map<String, Object> hash) {
		return (T) mapper.convertValue(hash, userType);
	}

	public Map<String, Object> toHash(T object) {
		return mapper.convertValue(object, mapType);
	}
}
