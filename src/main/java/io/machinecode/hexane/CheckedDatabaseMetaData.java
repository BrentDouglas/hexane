/*
 * Copyright (C) 2018 Brent Douglas and other contributors
 * as indicated by the @author tags. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.machinecode.hexane;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class CheckedDatabaseMetaData implements DatabaseMetaData {
  private final Terminal xa;
  private final Connection conn;
  private final DatabaseMetaData delegate;

  CheckedDatabaseMetaData(
      final Terminal xa, final Connection conn, final DatabaseMetaData delegate) {
    this.xa = xa;
    this.conn = conn;
    this.delegate = delegate;
  }

  @Override
  public boolean allProceduresAreCallable() throws SQLException {
    try {
      return delegate.allProceduresAreCallable();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean allTablesAreSelectable() throws SQLException {
    try {
      return delegate.allTablesAreSelectable();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getURL() throws SQLException {
    try {
      return delegate.getURL();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getUserName() throws SQLException {
    try {
      return delegate.getUserName();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    try {
      return delegate.isReadOnly();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean nullsAreSortedHigh() throws SQLException {
    try {
      return delegate.nullsAreSortedHigh();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean nullsAreSortedLow() throws SQLException {
    try {
      return delegate.nullsAreSortedLow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean nullsAreSortedAtStart() throws SQLException {
    try {
      return delegate.nullsAreSortedAtStart();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean nullsAreSortedAtEnd() throws SQLException {
    try {
      return delegate.nullsAreSortedAtEnd();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getDatabaseProductName() throws SQLException {
    try {
      return delegate.getDatabaseProductName();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getDatabaseProductVersion() throws SQLException {
    try {
      return delegate.getDatabaseProductVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getDriverName() throws SQLException {
    try {
      return delegate.getDriverName();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getDriverVersion() throws SQLException {
    try {
      return delegate.getDriverVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getDriverMajorVersion() {
    return delegate.getDriverMajorVersion();
  }

  @Override
  public int getDriverMinorVersion() {
    return delegate.getDriverMinorVersion();
  }

  @Override
  public boolean usesLocalFiles() throws SQLException {
    try {
      return delegate.usesLocalFiles();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean usesLocalFilePerTable() throws SQLException {
    try {
      return delegate.usesLocalFilePerTable();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMixedCaseIdentifiers() throws SQLException {
    try {
      return delegate.supportsMixedCaseIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesUpperCaseIdentifiers() throws SQLException {
    try {
      return delegate.storesUpperCaseIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesLowerCaseIdentifiers() throws SQLException {
    try {
      return delegate.storesLowerCaseIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesMixedCaseIdentifiers() throws SQLException {
    try {
      return delegate.storesMixedCaseIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
    try {
      return delegate.supportsMixedCaseQuotedIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
    try {
      return delegate.storesUpperCaseQuotedIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
    try {
      return delegate.storesLowerCaseQuotedIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
    try {
      return delegate.storesMixedCaseQuotedIdentifiers();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getIdentifierQuoteString() throws SQLException {
    try {
      return delegate.getIdentifierQuoteString();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSQLKeywords() throws SQLException {
    try {
      return delegate.getSQLKeywords();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getNumericFunctions() throws SQLException {
    try {
      return delegate.getNumericFunctions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getStringFunctions() throws SQLException {
    try {
      return delegate.getStringFunctions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSystemFunctions() throws SQLException {
    try {
      return delegate.getSystemFunctions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getTimeDateFunctions() throws SQLException {
    try {
      return delegate.getTimeDateFunctions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSearchStringEscape() throws SQLException {
    try {
      return delegate.getSearchStringEscape();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getExtraNameCharacters() throws SQLException {
    try {
      return delegate.getExtraNameCharacters();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsAlterTableWithAddColumn() throws SQLException {
    try {
      return delegate.supportsAlterTableWithAddColumn();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsAlterTableWithDropColumn() throws SQLException {
    try {
      return delegate.supportsAlterTableWithDropColumn();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsColumnAliasing() throws SQLException {
    try {
      return delegate.supportsColumnAliasing();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean nullPlusNonNullIsNull() throws SQLException {
    try {
      return delegate.nullPlusNonNullIsNull();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsConvert() throws SQLException {
    try {
      return delegate.supportsConvert();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsConvert(final int fromType, final int toType) throws SQLException {
    try {
      return delegate.supportsConvert(fromType, toType);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsTableCorrelationNames() throws SQLException {
    try {
      return delegate.supportsTableCorrelationNames();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsDifferentTableCorrelationNames() throws SQLException {
    try {
      return delegate.supportsDifferentTableCorrelationNames();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsExpressionsInOrderBy() throws SQLException {
    try {
      return delegate.supportsExpressionsInOrderBy();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOrderByUnrelated() throws SQLException {
    try {
      return delegate.supportsOrderByUnrelated();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsGroupBy() throws SQLException {
    try {
      return delegate.supportsGroupBy();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsGroupByUnrelated() throws SQLException {
    try {
      return delegate.supportsGroupByUnrelated();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsGroupByBeyondSelect() throws SQLException {
    try {
      return delegate.supportsGroupByBeyondSelect();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsLikeEscapeClause() throws SQLException {
    try {
      return delegate.supportsLikeEscapeClause();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMultipleResultSets() throws SQLException {
    try {
      return delegate.supportsMultipleResultSets();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMultipleTransactions() throws SQLException {
    try {
      return delegate.supportsMultipleTransactions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsNonNullableColumns() throws SQLException {
    try {
      return delegate.supportsNonNullableColumns();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMinimumSQLGrammar() throws SQLException {
    try {
      return delegate.supportsMinimumSQLGrammar();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCoreSQLGrammar() throws SQLException {
    try {
      return delegate.supportsCoreSQLGrammar();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsExtendedSQLGrammar() throws SQLException {
    try {
      return delegate.supportsExtendedSQLGrammar();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsANSI92EntryLevelSQL() throws SQLException {
    try {
      return delegate.supportsANSI92EntryLevelSQL();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsANSI92IntermediateSQL() throws SQLException {
    try {
      return delegate.supportsANSI92IntermediateSQL();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsANSI92FullSQL() throws SQLException {
    try {
      return delegate.supportsANSI92FullSQL();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsIntegrityEnhancementFacility() throws SQLException {
    try {
      return delegate.supportsIntegrityEnhancementFacility();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOuterJoins() throws SQLException {
    try {
      return delegate.supportsOuterJoins();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsFullOuterJoins() throws SQLException {
    try {
      return delegate.supportsFullOuterJoins();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsLimitedOuterJoins() throws SQLException {
    try {
      return delegate.supportsLimitedOuterJoins();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSchemaTerm() throws SQLException {
    try {
      return delegate.getSchemaTerm();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getProcedureTerm() throws SQLException {
    try {
      return delegate.getProcedureTerm();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getCatalogTerm() throws SQLException {
    try {
      return delegate.getCatalogTerm();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isCatalogAtStart() throws SQLException {
    try {
      return delegate.isCatalogAtStart();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getCatalogSeparator() throws SQLException {
    try {
      return delegate.getCatalogSeparator();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSchemasInDataManipulation() throws SQLException {
    try {
      return delegate.supportsSchemasInDataManipulation();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSchemasInProcedureCalls() throws SQLException {
    try {
      return delegate.supportsSchemasInProcedureCalls();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSchemasInTableDefinitions() throws SQLException {
    try {
      return delegate.supportsSchemasInTableDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSchemasInIndexDefinitions() throws SQLException {
    try {
      return delegate.supportsSchemasInIndexDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
    try {
      return delegate.supportsSchemasInPrivilegeDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCatalogsInDataManipulation() throws SQLException {
    try {
      return delegate.supportsCatalogsInDataManipulation();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCatalogsInProcedureCalls() throws SQLException {
    try {
      return delegate.supportsCatalogsInProcedureCalls();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCatalogsInTableDefinitions() throws SQLException {
    try {
      return delegate.supportsCatalogsInTableDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
    try {
      return delegate.supportsCatalogsInIndexDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
    try {
      return delegate.supportsCatalogsInPrivilegeDefinitions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsPositionedDelete() throws SQLException {
    try {
      return delegate.supportsPositionedDelete();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsPositionedUpdate() throws SQLException {
    try {
      return delegate.supportsPositionedUpdate();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSelectForUpdate() throws SQLException {
    try {
      return delegate.supportsSelectForUpdate();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsStoredProcedures() throws SQLException {
    try {
      return delegate.supportsStoredProcedures();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSubqueriesInComparisons() throws SQLException {
    try {
      return delegate.supportsSubqueriesInComparisons();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSubqueriesInExists() throws SQLException {
    try {
      return delegate.supportsSubqueriesInExists();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSubqueriesInIns() throws SQLException {
    try {
      return delegate.supportsSubqueriesInIns();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSubqueriesInQuantifieds() throws SQLException {
    try {
      return delegate.supportsSubqueriesInQuantifieds();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsCorrelatedSubqueries() throws SQLException {
    try {
      return delegate.supportsCorrelatedSubqueries();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsUnion() throws SQLException {
    try {
      return delegate.supportsUnion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsUnionAll() throws SQLException {
    try {
      return delegate.supportsUnionAll();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
    try {
      return delegate.supportsOpenCursorsAcrossCommit();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
    try {
      return delegate.supportsOpenCursorsAcrossRollback();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
    try {
      return delegate.supportsOpenStatementsAcrossCommit();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
    try {
      return delegate.supportsOpenStatementsAcrossRollback();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxBinaryLiteralLength() throws SQLException {
    try {
      return delegate.getMaxBinaryLiteralLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxCharLiteralLength() throws SQLException {
    try {
      return delegate.getMaxCharLiteralLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnNameLength() throws SQLException {
    try {
      return delegate.getMaxColumnNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnsInGroupBy() throws SQLException {
    try {
      return delegate.getMaxColumnsInGroupBy();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnsInIndex() throws SQLException {
    try {
      return delegate.getMaxColumnsInIndex();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnsInOrderBy() throws SQLException {
    try {
      return delegate.getMaxColumnsInOrderBy();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnsInSelect() throws SQLException {
    try {
      return delegate.getMaxColumnsInSelect();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxColumnsInTable() throws SQLException {
    try {
      return delegate.getMaxColumnsInTable();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxConnections() throws SQLException {
    try {
      return delegate.getMaxConnections();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxCursorNameLength() throws SQLException {
    try {
      return delegate.getMaxCursorNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxIndexLength() throws SQLException {
    try {
      return delegate.getMaxIndexLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxSchemaNameLength() throws SQLException {
    try {
      return delegate.getMaxSchemaNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxProcedureNameLength() throws SQLException {
    try {
      return delegate.getMaxProcedureNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxCatalogNameLength() throws SQLException {
    try {
      return delegate.getMaxCatalogNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxRowSize() throws SQLException {
    try {
      return delegate.getMaxRowSize();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
    try {
      return delegate.doesMaxRowSizeIncludeBlobs();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxStatementLength() throws SQLException {
    try {
      return delegate.getMaxStatementLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxStatements() throws SQLException {
    try {
      return delegate.getMaxStatements();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxTableNameLength() throws SQLException {
    try {
      return delegate.getMaxTableNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxTablesInSelect() throws SQLException {
    try {
      return delegate.getMaxTablesInSelect();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxUserNameLength() throws SQLException {
    try {
      return delegate.getMaxUserNameLength();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getDefaultTransactionIsolation() throws SQLException {
    try {
      return delegate.getDefaultTransactionIsolation();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsTransactions() throws SQLException {
    try {
      return delegate.supportsTransactions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsTransactionIsolationLevel(final int level) throws SQLException {
    try {
      return delegate.supportsTransactionIsolationLevel(level);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
    try {
      return delegate.supportsDataDefinitionAndDataManipulationTransactions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
    try {
      return delegate.supportsDataManipulationTransactionsOnly();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
    try {
      return delegate.dataDefinitionCausesTransactionCommit();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
    try {
      return delegate.dataDefinitionIgnoredInTransactions();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getProcedures(
      final String catalog, final String schemaPattern, final String procedureNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getProcedures(catalog, schemaPattern, procedureNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getProcedureColumns(
      final String catalog,
      final String schemaPattern,
      final String procedureNamePattern,
      final String columnNamePattern)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getProcedureColumns(
              catalog, schemaPattern, procedureNamePattern, columnNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getTables(
      final String catalog,
      final String schemaPattern,
      final String tableNamePattern,
      final String[] types)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getTables(catalog, schemaPattern, tableNamePattern, types);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getSchemas() throws SQLException {
    try {
      final ResultSet ret = delegate.getSchemas();
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getCatalogs() throws SQLException {
    try {
      final ResultSet ret = delegate.getCatalogs();
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getTableTypes() throws SQLException {
    try {
      final ResultSet ret = delegate.getTableTypes();
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getColumns(
      final String catalog,
      final String schemaPattern,
      final String tableNamePattern,
      final String columnNamePattern)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getColumnPrivileges(
      final String catalog, final String schema, final String table, final String columnNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getColumnPrivileges(catalog, schema, table, columnNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getTablePrivileges(
      final String catalog, final String schemaPattern, final String tableNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getTablePrivileges(catalog, schemaPattern, tableNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getBestRowIdentifier(
      final String catalog,
      final String schema,
      final String table,
      final int scope,
      final boolean nullable)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getBestRowIdentifier(catalog, schema, table, scope, nullable);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getVersionColumns(final String catalog, final String schema, final String table)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getVersionColumns(catalog, schema, table);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getPrimaryKeys(final String catalog, final String schema, final String table)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getPrimaryKeys(catalog, schema, table);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getImportedKeys(final String catalog, final String schema, final String table)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getImportedKeys(catalog, schema, table);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getExportedKeys(final String catalog, final String schema, final String table)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getExportedKeys(catalog, schema, table);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getCrossReference(
      final String parentCatalog,
      final String parentSchema,
      final String parentTable,
      final String foreignCatalog,
      final String foreignSchema,
      final String foreignTable)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getCrossReference(
              parentCatalog,
              parentSchema,
              parentTable,
              foreignCatalog,
              foreignSchema,
              foreignTable);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getTypeInfo() throws SQLException {
    try {
      final ResultSet ret = delegate.getTypeInfo();
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getIndexInfo(
      final String catalog,
      final String schema,
      final String table,
      final boolean unique,
      final boolean approximate)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getIndexInfo(catalog, schema, table, unique, approximate);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsResultSetType(final int type) throws SQLException {
    try {
      return delegate.supportsResultSetType(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsResultSetConcurrency(final int type, final int concurrency)
      throws SQLException {
    try {
      return delegate.supportsResultSetConcurrency(type, concurrency);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean ownUpdatesAreVisible(final int type) throws SQLException {
    try {
      return delegate.ownUpdatesAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean ownDeletesAreVisible(final int type) throws SQLException {
    try {
      return delegate.ownDeletesAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean ownInsertsAreVisible(final int type) throws SQLException {
    try {
      return delegate.ownInsertsAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean othersUpdatesAreVisible(final int type) throws SQLException {
    try {
      return delegate.othersUpdatesAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean othersDeletesAreVisible(final int type) throws SQLException {
    try {
      return delegate.othersDeletesAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean othersInsertsAreVisible(final int type) throws SQLException {
    try {
      return delegate.othersInsertsAreVisible(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean updatesAreDetected(final int type) throws SQLException {
    try {
      return delegate.updatesAreDetected(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean deletesAreDetected(final int type) throws SQLException {
    try {
      return delegate.deletesAreDetected(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean insertsAreDetected(final int type) throws SQLException {
    try {
      return delegate.insertsAreDetected(type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsBatchUpdates() throws SQLException {
    try {
      return delegate.supportsBatchUpdates();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getUDTs(
      final String catalog,
      final String schemaPattern,
      final String typeNamePattern,
      final int[] types)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getUDTs(catalog, schemaPattern, typeNamePattern, types);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    try {
      return delegate.getConnection();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsSavepoints() throws SQLException {
    try {
      return delegate.supportsSavepoints();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsNamedParameters() throws SQLException {
    try {
      return delegate.supportsNamedParameters();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsMultipleOpenResults() throws SQLException {
    try {
      return delegate.supportsMultipleOpenResults();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsGetGeneratedKeys() throws SQLException {
    try {
      return delegate.supportsGetGeneratedKeys();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getSuperTypes(
      final String catalog, final String schemaPattern, final String typeNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getSuperTypes(catalog, schemaPattern, typeNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getSuperTables(
      final String catalog, final String schemaPattern, final String tableNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getSuperTables(catalog, schemaPattern, tableNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getAttributes(
      final String catalog,
      final String schemaPattern,
      final String typeNamePattern,
      final String attributeNamePattern)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsResultSetHoldability(final int holdability) throws SQLException {
    try {
      return delegate.supportsResultSetHoldability(holdability);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    try {
      return delegate.getResultSetHoldability();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getDatabaseMajorVersion() throws SQLException {
    try {
      return delegate.getDatabaseMajorVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getDatabaseMinorVersion() throws SQLException {
    try {
      return delegate.getDatabaseMinorVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getJDBCMajorVersion() throws SQLException {
    try {
      return delegate.getJDBCMajorVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getJDBCMinorVersion() throws SQLException {
    try {
      return delegate.getJDBCMinorVersion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getSQLStateType() throws SQLException {
    try {
      return delegate.getSQLStateType();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean locatorsUpdateCopy() throws SQLException {
    try {
      return delegate.locatorsUpdateCopy();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsStatementPooling() throws SQLException {
    try {
      return delegate.supportsStatementPooling();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public RowIdLifetime getRowIdLifetime() throws SQLException {
    try {
      return delegate.getRowIdLifetime();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getSchemas(final String catalog, final String schemaPattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getSchemas(catalog, schemaPattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
    try {
      return delegate.supportsStoredFunctionsUsingCallSyntax();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
    try {
      return delegate.autoCommitFailureClosesAllResultSets();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getClientInfoProperties() throws SQLException {
    try {
      final ResultSet ret = delegate.getClientInfoProperties();
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getFunctions(
      final String catalog, final String schemaPattern, final String functionNamePattern)
      throws SQLException {
    try {
      final ResultSet ret = delegate.getFunctions(catalog, schemaPattern, functionNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getFunctionColumns(
      final String catalog,
      final String schemaPattern,
      final String functionNamePattern,
      final String columnNamePattern)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getFunctionColumns(
              catalog, schemaPattern, functionNamePattern, columnNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getPseudoColumns(
      final String catalog,
      final String schemaPattern,
      final String tableNamePattern,
      final String columnNamePattern)
      throws SQLException {
    try {
      final ResultSet ret =
          delegate.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
      return new CheckedResultSet(xa, new CheckedStatement<>(xa, conn, ret.getStatement()), ret);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean generatedKeyAlwaysReturned() throws SQLException {
    try {
      return delegate.generatedKeyAlwaysReturned();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long getMaxLogicalLobSize() throws SQLException {
    try {
      return delegate.getMaxLogicalLobSize();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean supportsRefCursors() throws SQLException {
    try {
      return delegate.supportsRefCursors();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    try {
      return Util.unwrap(iface, this, delegate);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    try {
      return Util.isWrapperFor(iface, this, delegate);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }
}
