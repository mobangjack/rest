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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * SqlSession connects to the data source and execute the SQL statement.
 * @author 帮杰
 *
 */
public class SqlSession {

	private Connection connection;

	public SqlSession(Connection connection) {
		this.connection = connection;
	}

	public void beginTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

	public boolean isAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public List<Row> read(Sql sql) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = ps.getMetaData();
			int columnCounts = rsmd.getColumnCount();
			String[] columnNames = new String[columnCounts];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			List<Row> rows = new ArrayList<Row>(rs.getRow());
			while (rs.next()) {
				Row row = new Row(columnCounts);
				for (String columnName : columnNames) {
					row.put(columnName, rs.getObject(columnName));
				}
				rows.add(row);
			}
			return rows;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs!=null) {rs.close();rs = null;}
				if (ps!=null) {ps.close();ps = null;}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean write(Sql sql) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatement(sql);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (ps!=null) {ps.close();ps = null;}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void commit() throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	public void rollback() throws SQLException {
		if (connection != null) {
			connection.rollback();
		}
	}
	
	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	private PreparedStatement prepareStatement(Sql sql)
	throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql.toString());
		if (sql.getParams() != null) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				ps.setObject(i+1, sql.getParams().get(i));
			}
		}
		return ps;
	}

	static void rollbackQuietly(SqlSession sqlSession) {
		if (sqlSession != null) {
			try {
				sqlSession.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void closeQuietly(SqlSession sqlSession) {
		if (sqlSession != null) {
			try {
				sqlSession.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
