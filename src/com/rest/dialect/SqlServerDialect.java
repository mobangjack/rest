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
package com.rest.dialect;

import com.rest.Node;
import com.rest.Sql;

/**
 * SqlServerDialect for Microsoft SqlServer.
 * @author 帮杰
 *
 */
public class SqlServerDialect extends DefaultDialect {

	@Override
	public void forList(Node node, int offset, int size, Sql sql) {
		forFind(node, sql);
		StringBuilder selectPart = sql.getSql();
		sql.setSql(new StringBuilder());
		int end = (offset+1) * size;
		if (end <= 0) {
			end = size;
		}
		int begin = offset * size;
		if (begin < 0) {
			begin = 0;
		}
		sql.append("select * from ( select row_number() over (order by tempcolumn) temprownumber, * from ( select top ")
		   .append(end)
		   .append(" tempcolumn=0,")
		   .append(selectPart)
		   .append(")vip)mvp where temprownumber>")
		   .append(begin);
	}
	
}
