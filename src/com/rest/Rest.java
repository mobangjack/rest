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
import java.util.List;

import com.rest.dialect.Dialect;
import com.rest.dialect.MysqlDialect;

/**
 * Have a rest!
 * @author 帮杰
 */
public class Rest {

	private Source source;
	private Dialect dialect;
	
	private SqlReportor sqlReportor = new SqlReportor() {
		public void report(Sql sql) {
			System.out.println("sql:"+sql.getSql());
			System.out.println("var:"+sql.getParams());
		}
	};
	
	public Rest(Source source) {
		this.source = source;
		this.dialect = new MysqlDialect();
	}
	
	public Rest(Source source, Dialect dialect) {
		this.source = source;
		this.dialect = dialect;
	}
	
	public Rest(Source source, Dialect dialect, SqlReportor sqlReportor) {
		this.source = source;
		this.dialect = dialect;
		this.sqlReportor = sqlReportor;
	}
	
	public boolean save(String uri, Row row) throws RestException {
		Node node = Node.parse(uri);
		Sql sql = new Sql();
		dialect.forSave(node, row, sql);
		reportSql(sql);
		SqlSession sqlSession = null;
		try {
			sqlSession = new SqlSession(source.getConnection());
			return sqlSession.write(sql);
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			SqlSession.closeQuietly(sqlSession);
		}
	}

	public boolean update(String uri, Row row) throws RestException {
		Node node = Node.parse(uri);
		Sql sql = new Sql();
		dialect.forUpdate(node, row, sql);
		reportSql(sql);
		SqlSession sqlSession = null;
		try {
			sqlSession = new SqlSession(source.getConnection());
			return sqlSession.write(sql);
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			SqlSession.closeQuietly(sqlSession);
		}
	}

	public Row find(String uri) throws RestException {
		Node node = Node.parse(uri);
		Sql sql = new Sql();
		dialect.forFind(node, sql);
		reportSql(sql);
		SqlSession sqlSession = null;
		try {
			sqlSession = new SqlSession(source.getConnection());
			List<Row> rows = sqlSession.read(sql);
			if (rows.size() > 0) {
				return rows.get(0);
			}else {
				return null;
			}
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			SqlSession.closeQuietly(sqlSession);
		}
	}

	public List<Row> list(String uri, int offset, int size) throws RestException {
		Node node = Node.parse(uri);
		Sql sql = new Sql();
		dialect.forList(node, offset, size, sql);
		reportSql(sql);
		SqlSession sqlSession = null;
		try {
			sqlSession = new SqlSession(source.getConnection());
			List<Row> rows = sqlSession.read(sql);
			return rows;
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			SqlSession.closeQuietly(sqlSession);
		}
	}

	public boolean delete(String uri) throws RestException {
		Node node = Node.parse(uri);
		Sql sql = new Sql();
		dialect.forDelete(node, sql);
		reportSql(sql);
		SqlSession sqlSession = null;
		try {
			sqlSession = new SqlSession(source.getConnection());
			return sqlSession.write(sql);
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			SqlSession.closeQuietly(sqlSession);
		}
	}

	protected void reportSql(Sql sql) {
		if (sqlReportor != null) {
			sqlReportor.report(sql);
		}
	}
}
