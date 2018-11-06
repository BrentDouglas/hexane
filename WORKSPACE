workspace(name = "io_machinecode_hexane")

load("//tools/java:maven_jar.bzl", "maven_jar")

bazel_version = "0.17.1"

rules_skylib_version = "8cecf885c8bf4c51e82fd6b50b9dd68d2c98f757"

rules_skydoc_version = "77e5399258f6d91417d23634fce97d73b40cf337"

rules_nodejs_version = "0.15.1"

rules_sass_version = "1.14.1"

http_archive(
    name = "bazel_skylib",
    sha256 = "68ef2998919a92c2c9553f7a6b00a1d0615b57720a13239c0e51d0ded5aa452a",
    strip_prefix = "bazel-skylib-" + rules_skylib_version,
    urls = ["https://github.com/bazelbuild/bazel-skylib/archive/%s.tar.gz" % rules_skylib_version],
)

http_archive(
    name = "io_bazel_skydoc",
    sha256 = "1088233aa190d79ebaff712eae28adeb21bdc71d6378ae4ead2471405964ad14",
    strip_prefix = "skydoc-" + rules_skydoc_version,
    urls = ["https://github.com/bazelbuild/skydoc/archive/%s.tar.gz" % rules_skydoc_version],
)

http_archive(
    name = "build_bazel_rules_nodejs",
    sha256 = "a0a91a2e0cee32e9304f1aeea9e6c1b611afba548058c5980217d44ee11e3dd7",
    strip_prefix = "rules_nodejs-%s" % rules_nodejs_version,
    urls = ["https://github.com/bazelbuild/rules_nodejs/archive/%s.zip" % rules_nodejs_version],
)

http_archive(
    name = "io_bazel_rules_sass",
    sha256 = "d8b89e47b05092a6eed3fa199f2de7cf671a4b9165d0bf38f12a0363dda928d3",
    strip_prefix = "rules_sass-%s" % rules_sass_version,
    url = "https://github.com/bazelbuild/rules_sass/archive/%s.zip" % rules_sass_version,
)

load("@build_bazel_rules_nodejs//:defs.bzl", "check_bazel_version", "node_repositories", "yarn_install")

check_bazel_version(bazel_version)

node_repositories()

load("@io_bazel_rules_sass//sass:sass_repositories.bzl", "sass_repositories")

sass_repositories()

load("@io_bazel_skydoc//skylark:skylark.bzl", "skydoc_repositories")

skydoc_repositories()

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
