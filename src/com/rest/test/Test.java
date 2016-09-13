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
package com.rest.test;

import java.util.Date;

import com.rest.Node;
import com.rest.Rest;
import com.rest.RestException;
import com.rest.Row;
import com.rest.Sql;
import com.rest.dialect.DefaultDialect;
import com.rest.dialect.Dialect;
import com.rest.dialect.MysqlDialect;
import com.twogen.json.JsonWriter;

public class Test {
	
	public static void main(String[] args) {
		Jdbc jdbc = getSource();
		Rest rest = new Rest(jdbc, new DefaultDialect());
		String uri = "topic/fffd9d8e04d64edb8606f6acb06d3096/story/ae44a93cd20144ca9f593def10e45dca/story_review/11522";
		try {
			
			println("save:");
			Row row = new Row();
			row.put("author_id", "233");
			row.put("quote_id", "233");
			row.put("content", "233");
			row.put("in_time", new Date());
			println(rest.save(uri, row));
			
			println("find:");
			Row _row = rest.find(uri);
			println(JsonWriter.write(_row));
			
			
			println("update:");
			_row.put("content", "244");
			rest.update(uri, _row);
			println(JsonWriter.write(rest.find(uri)));
			
			
			println("list:");
			println(JsonWriter.write(rest.list(uri, 0, 2)));
			
			println("delete:");
			println(rest.delete(uri));
			
		} catch (RestException e) {
			System.err.println(e.getCause().getMessage());
		}
		
		
	}

	/**
	 * @return
	 */
	protected static Jdbc getSource() {
		Jdbc jdbc = new Jdbc();
		jdbc.setUrl("jdbc:mysql://localhost/twogen");
		jdbc.setUsername("root");
		jdbc.setPassword("");
		jdbc.setDriverClass("com.mysql.jdbc.Driver");
		return jdbc;
	}
	
	public static void println(Object o) {
		System.out.println(o);
	}
	
	public static void test() {
		String uri = "topic/fffd9d8e04d64edb8606f6acb06d3096/story/ae44a93cd20144ca9f593def10e45dca/story_review/1222225566";
		Node node = Node.parse(uri);
		
		Row row = new Row();
		row.put("author_id", "233");
		row.put("quote_id", "233");
		row.put("content", "233");
		row.put("in_time", new Date());
		
		Sql sql = new Sql();
		Dialect dialect = new MysqlDialect();
		dialect.forSave(node, row, sql);
		
		System.out.println(sql.getSql());
		System.out.println(sql.getParams());
	}
}
