load("//:deps.bzl", "jmh_deps", "test_deps")

hexane_test_deps = test_deps + [
    "@com_h2database_h2//jar",
    #"@com_oracle_ojdbc7//jar",
    "@mysql_mysql_connector_java//jar",
    "@org_apache_derby_derby//jar",
    "@org_hsqldb_hsqldb//jar",
    "@org_mariadb_jdbc_mariadb_java_client//jar",
    "@org_postgresql_postgresql//jar",
]

hexane_bench_deps = jmh_deps + [
    "@com_h2database_h2//jar",
    #"@com_oracle_ojdbc7//jar",
    "@mysql_mysql_connector_java//jar",
    "@org_apache_derby_derby//jar",
    "@org_hsqldb_hsqldb//jar",
    "@org_mariadb_jdbc_mariadb_java_client//jar",
    "@org_postgresql_postgresql//jar",
]

database_properties = [
  "-Dtest.db.derby.databaseName=memory:hexane",
  "-Dtest.db.derby.connectionAttributes=create=true",
  "-Dtest.db.h2.url=jdbc:h2:mem:hexane",
  "-Dtest.db.hsqldb.url=jdbc:hsqldb:mem:hexane",
  "-Dtest.db.mariadb_5_5.url=jdbc:mariadb://127.0.0.1:40055/hexane",
  "-Dtest.db.mariadb_10_0.url=jdbc:mariadb://127.0.0.1:40100/hexane",
  "-Dtest.db.mariadb_10_1.url=jdbc:mariadb://127.0.0.1:40101/hexane",
  "-Dtest.db.mariadb_10_2.url=jdbc:mariadb://127.0.0.1:40102/hexane",
  "-Dtest.db.mariadb_10_3.url=jdbc:mariadb://127.0.0.1:40103/hexane",
  "-Dtest.db.mariadb.user=hexane",
  "-Dtest.db.mariadb.password=hexane",
  "-Dtest.db.mysql_5_5.url=jdbc:mysql://127.0.0.1:30055/hexane?allowPublicKeyRetrieval=true&useSSL=false",
  "-Dtest.db.mysql_5_6.url=jdbc:mysql://127.0.0.1:30056/hexane?allowPublicKeyRetrieval=true&useSSL=false",
  "-Dtest.db.mysql_5_7.url=jdbc:mysql://127.0.0.1:30057/hexane?allowPublicKeyRetrieval=true&useSSL=false",
  "-Dtest.db.mysql_8_0.url=jdbc:mysql://127.0.0.1:30080/hexane?allowPublicKeyRetrieval=true&useSSL=false",
  "-Dtest.db.mysql.user=hexane",
  "-Dtest.db.mysql.password=hexane",
  "-Dtest.db.pg_9_3.url=jdbc:postgresql://127.0.0.1:20093/hexane",
  "-Dtest.db.pg_9_4.url=jdbc:postgresql://127.0.0.1:20094/hexane",
  "-Dtest.db.pg_9_5.url=jdbc:postgresql://127.0.0.1:20095/hexane",
  "-Dtest.db.pg_9_6.url=jdbc:postgresql://127.0.0.1:20096/hexane",
  "-Dtest.db.pg_10_5.url=jdbc:postgresql://127.0.0.1:20105/hexane",
  "-Dtest.db.pg_11_0.url=jdbc:postgresql://127.0.0.1:20110/hexane",
  "-Dtest.db.pg.user=hexane",
  "-Dtest.db.pg.password=hexane",
]