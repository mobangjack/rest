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
 * OracleDialect for oracle data base server.
 * @author 帮杰
 *
 */
public class OracleDialect extends DefaultDialect {

	@Override
	public void forList(Node node, int offset, int size, Sql sql) {
		forFind(node, sql);
		StringBuilder selectPart = sql.getSql();
		sql.setSql(new StringBuilder());
		int start = offset * size + 1;
		int end = (offset+1) * size;
		sql.append("select * from ( select row_.*, rownum rownum_ from (  ");
		sql.append(selectPart);
		sql.append(" ) row_ where rownum <= ").append(end).append(") table_alias");
		sql.append(" where table_alias.rownum_ >= ").append(start);
	}
	
}
