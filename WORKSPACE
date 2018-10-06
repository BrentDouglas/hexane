workspace(name = "io_machinecode_hexane")

load("//tools/gerrit:maven_jar.bzl", "maven_jar")

h2_version = "1.4.197"

oracle_version = "12.1.0.2"

junit_version = "4.12"

mockito_version = "1.10.19"

mysql_version = "8.0.12"

hsqldb_version = "2.4.1"

derby_version = "10.14.2.0"

mariadb_version = "2.3.0"

postgresql_version = "42.2.5"

jmh_version = "1.21"

jopt_version = "4.6"

commons_math_version = "3.2"

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
    name = "junit_junit",
    artifact = "junit:junit:" + junit_version,
)

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
    name = "org_mockito_mockito_all",
    artifact = "org.mockito:mockito-all:" + mockito_version,
)

maven_jar(
    name = "org_postgresql_postgresql",
    artifact = "org.postgresql:postgresql:" + postgresql_version,
)

maven_jar(
    name = "org_openjdk_jmh_jmh_core",
    artifact = "org.openjdk.jmh:jmh-core:" + jmh_version,
)

maven_jar(
    name = "org_openjdk_jmh_jmh_generator_annprocess",
    artifact = "org.openjdk.jmh:jmh-generator-annprocess:" + jmh_version,
)

maven_jar(
    name = "net_sf_jopt_simple_jopt_simple",
    artifact = "net.sf.jopt-simple:jopt-simple:" + jopt_version,
)

maven_jar(
    name = "org_apache_commons_commons_math3",
    artifact = "org.apache.commons:commons-math3:" + commons_math_version,
)

slf4j_version = "1.7.25"

maven_jar(
    name = "org_slf4j_slf4j_api",
    artifact = "org.slf4j:slf4j-api:" + slf4j_version,
)

log4j_version = "2.11.1"

maven_jar(
    name = "org_apache_logging_log4j_log4j_api",
    artifact = "org.apache.logging.log4j:log4j-api:" + log4j_version,
)

jboss_logging_version = "3.3.2.Final"

maven_jar(
    name = "org_jboss_logging_jboss_logging",
    artifact = "org.jboss.logging:jboss-logging:" + jboss_logging_version,
)

hikari_version = "3.2.0"

maven_jar(
    name = "com_zaxxer_hikaricp",
    artifact = "com.zaxxer:HikariCP:" + hikari_version,
)

c3p0_version = "0.9.5.2"

maven_jar(
    name = "com_mchange_c3p0",
    artifact = "com.mchange:c3p0:" + c3p0_version,
)

mchange_commons_java_version = "0.2.15"

maven_jar(
    name = "com_mchange_mchange_commons_java",
    artifact = "com.mchange:mchange-commons-java:" + mchange_commons_java_version,
)

commons_dbcp2_version = "2.5.0"

maven_jar(
    name = "org_apache_commons_commons_dbcp2",
    artifact = "org.apache.commons:commons-dbcp2:" + commons_dbcp2_version,
)

commons_pool_version = "2.6.0"

maven_jar(
    name = "org_apache_commons_commons_pool2",
    artifact = "org.apache.commons:commons-pool2:" + commons_pool_version,
)

commons_logging_version = "1.2"

maven_jar(
    name = "commons_logging_commons_logging",
    artifact = "commons-logging:commons-logging:" + commons_logging_version,
)

agroal_version = "1.2"

maven_jar(
    name = "io_agroal_agroal_api",
    artifact = "io.agroal:agroal-api:" + agroal_version,
)
maven_jar(
    name = "io_agroal_agroal_pool",
    artifact = "io.agroal:agroal-pool:" + agroal_version,
)