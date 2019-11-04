/*
 * Copyright (c) 2012-2019 Snowflake Computing Inc. All right reserved.
 */
package net.snowflake.client.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Prepared statement integration tests
 */
public class PreparedStatement0IT extends BaseJDBCTest
{
  private final String queryResultFormat;

  Connection init()
  throws SQLException
  {
    Connection conn = BaseJDBCTest.getConnection();
    conn.createStatement().execute(
        "alter session set query_result_format = '" + queryResultFormat + "'");
    return conn;
  }

  final String insertSQL = "insert into TEST_PREPST values(?, ?, ?, ?, ?, ?)";
  final String selectAllSQL = "select * from TEST_PREPST";
  final String updateSQL = "update TEST_PREPST set COLC = 'newString' where ID = ?";
  final String deleteSQL = "delete from TEST_PREPST where ID = ?";
  final String selectSQL = "select * from TEST_PREPST where ID = ?";
  final String createTableSQL = "create or replace table test_prepst(id INTEGER, "
                                + "colA DOUBLE, colB FLOAT, colC String,  "
                                + "colD NUMBER, col INTEGER)";
  final String deleteTableSQL = "drop table if exists TEST_PREPST";
  final String enableCacheReuse = "alter session set USE_CACHED_RESULT=true";
  final String tableFuncSQL = "select 1 from table(generator(rowCount => ?))";

  @Rule
  public final ExpectedException exception = ExpectedException.none();


  @Before
  public void setUp() throws SQLException
  {
    try (Connection con = init())
    {
      con.createStatement().execute(createTableSQL);
    }
  }

  @After
  public void tearDown() throws SQLException
  {
    try (Connection con = init())
    {
      con.createStatement().execute(deleteTableSQL);
    }
  }

  PreparedStatement0IT(String queryResultFormat)
  {
    this.queryResultFormat = queryResultFormat;
  }
}

