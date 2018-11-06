load("//tools/java:maven_jar.bzl", "maven_jar")

def database_repositories(
        h2_version = "1.4.197",
        oracle_version = "12.1.0.2",
        mysql_version = "8.0.12",
        hsqldb_version = "2.4.1",
        derby_version = "10.14.2.0",
        mariadb_version = "2.3.0",
        postgresql_version = "42.2.5"):
    maven_jar(
        name = "com_h2database_h2",
        artifact = "com.h2database:h2:" + h2_version,
    )

    #maven_jar(
    #    name = "com_oracle_ojdbc7",
    #    artifact = "com.oracle:ojdbc7:" + oracle_version,
    #    attach_source = False,
    #)

    maven_jar(
        name = "mysql_mysql_connector_java",
        artifact = "mysql:mysql-connector-java:" + mysql_version,
    )

    maven_jar(
        name = "org_apache_derby_derby",
        artifact = "org.apache.derby:derby:" + derby_version,
        attach_source = False,
    )

    maven_jar(
        name = "org_hsqldb_hsqldb",
        artifact = "org.hsqldb:hsqldb:" + hsqldb_version,
    )

    maven_jar(
        name = "org_mariadb_jdbc_mariadb_java_client",
        artifact = "org.mariadb.jdbc:mariadb-java-client:" + mariadb_version,
    )

    maven_jar(
        name = "org_postgresql_postgresql",
        artifact = "org.postgresql:postgresql:" + postgresql_version,
    )
