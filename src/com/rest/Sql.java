/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
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
package com.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Pack the SQL statement and parameters
 * @author 帮杰
 */
public class Sql implements Serializable {

	private static final long serialVersionUID = 2166638105789325223L;
	
	private StringBuilder sql = new StringBuilder();
	private List<Object> params = new ArrayList<Object>();

	public Sql() {
	}

	public Sql append(Object o) {
		this.sql.append(o);
		return this;
	}

	public Sql addParam(Object param) {
		this.params.add(param);
		return this;
	}

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return sql.toString();
	}
}
