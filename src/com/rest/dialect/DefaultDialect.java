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

import java.util.Map;

import com.rest.Node;
import com.rest.Row;
import com.rest.Sql;
import com.rest.dialect.syntax.KeyValuePairs;
import com.rest.dialect.syntax.Values;

/**
 * DefaultDialect.Paginate is not supported due to the diversity of database manufacturers.
 * @author 帮杰
 *
 */
public class DefaultDialect implements Dialect {

	protected String quote = "";
	
	public void forSave(Node node, Row row, Sql sql) {
		Values keys = new Values().setQuote(quote);
		Values vals = new Values();
		if (node.getId() != null) {
			keys.add("id");
			vals.add("?");
			sql.addParam(node.getId());
			row.remove("id");
		}
		for (Map.Entry<String, Object> e : row.entrySet()) {
			keys.add(e.getKey());
			vals.add("?");
			sql.addParam(e.getValue());
		}
		Node parentNode = node.getParentNode();
		if (parentNode != null) {
			keys.add(parentNode.getTable()+"_id");
			Sql select = new Sql();
			forFind(parentNode, "id", select);
			vals.add("("+select.getSql()+")");
			sql.getParams().addAll(select.getParams());
		}
		sql.append("insert into ")
		   .append(quote)
		   .append(node.getTable())
		   .append(quote)
		   .append("(")
		   .append(keys)
		   .append(")")
		   .append(" values(")
		   .append(vals)
		   .append(")");
	}

	public void forUpdate(Node node, Row row, Sql sql) {
		KeyValuePairs keyValuePairs = new KeyValuePairs();
		for (Map.Entry<String, Object> e : row.entrySet()) {
			keyValuePairs.add(quote+node.getTable()+quote+"."+e.getKey(), "?");
			sql.addParam(e.getValue());
		}
		Node parentNode = node.getParentNode();
		if (parentNode != null) {
			keyValuePairs.add(parentNode.getTable()+"_id", parentNode.getTable()+".id");
		}
		sql.append("update ")
		   .append(quote)
		   .append(node.getTable())
		   .append(quote);
		forInnerJoin(node, sql);
		sql.append(" set ")
		   .append(keyValuePairs);
		if (node.getId() != null) {
			sql.append(" where ")
			   .append(quote)
			   .append(node.getTable())
			   .append(quote)
			   .append(".id=?")
			   .addParam(node.getId());
		}else {
			sql.append(" where 1=1");
		}
	}

	public void forFind(Node node, Sql sql) {
		forFind(node, "*", sql);
		
	}

	protected void forFind(Node node, String cols, Sql sql) {
		String table = node.getTable();
		sql.append("select ");
		String[] columns = cols.split(",");
		for (String col : columns) {
			sql.append(quote)
			   .append(table)
			   .append(quote)
			   .append(".")
			   .append(col);
		}
		sql.append(" from ")
		   .append(quote)
		   .append(table)
		   .append(quote);
		forInnerJoin(node, sql);
		if (node.getId() != null) {
			sql.append(" where ")
			   .append(quote)
			   .append(node.getTable())
			   .append(quote)
			   .append(".id=?")
			   .addParam(node.getId());
		}else {
			sql.append(" where 1=1");
		}
		sql.append(" group by ")
		   .append(quote)
		   .append(node.getTable())
		   .append(quote)
		   .append(".id");
	}
	
	public void forList(Node node, int offset, int size, Sql sql) {
		forFind(node, sql);
		sql.append(" limit ").append(offset).append(",").append(size);
	}
	
	public void forDelete(Node node, Sql sql) {
		sql.append("delete ")
		   .append(quote)
		   .append(node.getTable())
		   .append(quote)
		   .append(" from ")
		   .append(quote)
		   .append(node.getTable())
		   .append(quote);
		forInnerJoin(node, sql);
		if (node.getId() != null) {
			sql.append(" where ")
			   .append(quote)
			   .append(node.getTable())
			   .append(quote)
			   .append(".id=?")
			   .addParam(node.getId());
		}else {
			sql.append(" where 1=1");
		}
	}

	protected void forInnerJoin(Node node, Sql sql) {
		String table = node.getTable();
		Node parentNode = node.getParentNode();
		while (parentNode != null) {
			sql.append(" inner join ")
			   .append(quote)
			   .append(parentNode.getTable())
			   .append(quote)
			   .append(" on ")
			   .append(quote)
			   .append(table)
			   .append(quote)
			   .append(".")
			   .append(parentNode.getTable())
			   .append("_id=?")
			   .addParam(parentNode.getId());
			table = parentNode.getTable();
			parentNode = parentNode.getParentNode();
		}
	}
	
}
