workspace(name = "io_machinecode_hexane")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

#local_repository(
#    name = "io_machinecode_tools",
#    path = "../tools",
#)

io_machinecode_tools_version = "cb15a98dab11b60895d2cf09dc86505b0ce555f6"

http_archive(
    name = "io_machinecode_tools",
    sha256 = "62d21f1ebb38426da3548a29f12ac680b2c29434eca5221d3d7979f19e39b71a",
    strip_prefix = "tools-" + io_machinecode_tools_version,
    urls = [
        "https://mirror.bazel.build/github.com/BrentDouglas/tools/archive/%s.tar.gz" % io_machinecode_tools_version,
        "https://github.com/BrentDouglas/tools/archive/%s.tar.gz" % io_machinecode_tools_version,
    ],
)

load("@io_machinecode_tools//imports:java_repositories.bzl", "java_repositories")

java_repositories()

load("@io_machinecode_tools//tools/java:devserver.bzl", "devserver")

devserver(
    name = "io_machinecode_devserver",
    hosts = [
        "localhost",
        "0.0.0.0",
    ],
)

load("@io_machinecode_tools//imports:stardoc_repositories.bzl", "stardoc_repositories")

stardoc_repositories()

load("@io_machinecode_tools//imports:nodejs_repositories.bzl", "nodejs_repositories")

nodejs_repositories()

load("@build_bazel_rules_nodejs//:repositories.bzl", "build_bazel_rules_nodejs_dependencies")

build_bazel_rules_nodejs_dependencies()

load("@io_machinecode_tools//imports:nodejs_binary_repositories.bzl", "nodejs_binary_repositories")

nodejs_binary_repositories()

load("@build_bazel_rules_nodejs//:index.bzl", "yarn_install")

yarn_install(
    name = "npm",
    package_json = "//:package.json",
    yarn_lock = "//:yarn.lock",
)

load("@io_bazel_rules_sass//:defs.bzl", "sass_repositories")

sass_repositories()

load("@io_bazel_stardoc//:setup.bzl", "stardoc_repositories")

stardoc_repositories()

load("@io_machinecode_tools//imports:build_repositories.bzl", "build_repositories")

build_repositories()

load("@io_machinecode_tools//imports:checkstyle_repositories.bzl", "checkstyle_repositories")

checkstyle_repositories()

load("@io_machinecode_tools//imports:format_repositories.bzl", "format_repositories")

format_repositories()

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

load("@io_machinecode_tools//imports:go_repositories.bzl", "go_repositories")

go_repositories()

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains(version = "1.20.2")

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies")

gazelle_dependencies()

load("@io_machinecode_tools//imports:devsrv_repositories.bzl", "devsrv_repositories")

devsrv_repositories()

load("@io_machinecode_tools//imports:jmh_repositories.bzl", "jmh_repositories")

jmh_repositories()

junit_version = "4.12"

mockito_version = "5.2.0"

slf4j_version = "1.7.25"

log4j_version = "2.11.1"

jboss_logging_version = "3.3.2.Final"

h2_version = "1.4.197"

oracle_version = "19.3.0.0"

mysql_version = "8.0.12"

hsqldb_version = "2.4.1"

derby_version = "10.14.2.0"

mariadb_version = "2.3.0"

postgresql_version = "42.2.5"

hikari_version = "3.2.0"

c3p0_version = "0.9.5.2"

mchange_commons_java_version = "0.2.15"

commons_dbcp2_version = "2.5.0"

commons_pool_version = "2.6.0"

commons_logging_version = "1.2"

agroal_version = "1.2"

tomcat_version = "9.0.12"

javax_inject_version = "1"

jms_api_version = "2.0.1"

spring_boot_version = "2.1.0.RELEASE"

spring_version = "5.1.2.RELEASE"

javax_transaction_version = "1.3"

bitronix_version = "2.1.4"

atomikos_version = "4.0.6"

load("@io_machinecode_tools//:defs.bzl", "maven_repositories")
load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    name = "provided_m2",
    artifacts = [
        maven.artifact(
            "org.slf4j",
            "slf4j-api",
            slf4j_version,
            neverlink = True,
        ),
        maven.artifact(
            "org.apache.logging.log4j",
            "log4j-api",
            log4j_version,
            neverlink = True,
        ),
        maven.artifact(
            "org.jboss.logging",
            "jboss-logging",
            jboss_logging_version,
            neverlink = True,
        ),
    ],
    fetch_sources = True,
    repositories = maven_repositories,
)

maven_install(
    artifacts = [
        "junit:junit:" + junit_version,
        "org.mockito:mockito-core:" + mockito_version,
        "org.slf4j:slf4j-api:" + slf4j_version,
        "org.apache.logging.log4j:log4j-api:" + log4j_version,
        "org.jboss.logging:jboss-logging:" + jboss_logging_version,
        "com.h2database:h2:" + h2_version,
        "com.oracle.ojdbc:ojdbc8:" + oracle_version,
        "mysql:mysql-connector-java:" + mysql_version,
        "org.apache.derby:derby:" + derby_version,
        "org.hsqldb:hsqldb:" + hsqldb_version,
        "org.mariadb.jdbc:mariadb-java-client:" + mariadb_version,
        "org.postgresql:postgresql:" + postgresql_version,
        "com.zaxxer:HikariCP:" + hikari_version,
        "com.mchange:c3p0:" + c3p0_version,
        "com.mchange:mchange-commons-java:" + mchange_commons_java_version,
        "org.apache.commons:commons-dbcp2:" + commons_dbcp2_version,
        "org.apache.commons:commons-pool2:" + commons_pool_version,
        "commons-logging:commons-logging:" + commons_logging_version,
        "io.agroal:agroal-api:" + agroal_version,
        "io.agroal:agroal-pool:" + agroal_version,
        "org.apache.tomcat:tomcat-jdbc:" + tomcat_version,
        "org.apache.tomcat:tomcat-juli:" + tomcat_version,
        "javax.inject:javax.inject:" + javax_inject_version,
        "javax.jms:javax.jms-api:" + jms_api_version,
        "org.springframework.boot:spring-boot:" + spring_boot_version,
        "org.springframework.boot:spring-boot-autoconfigure:" + spring_boot_version,
        "org.springframework.boot:spring-boot-test:" + spring_boot_version,
        "org.springframework:spring-aop:" + spring_version,
        "org.springframework:spring-context:" + spring_version,
        "org.springframework:spring-beans:" + spring_version,
        "org.springframework:spring-core:" + spring_version,
        "org.springframework:spring-expression:" + spring_version,
        "org.springframework:spring-jcl:" + spring_version,
        "org.springframework:spring-jdbc:" + spring_version,
        "org.springframework:spring-tx:" + spring_version,
        "org.springframework:spring-test:" + spring_version,
        "javax.transaction:javax.transaction-api:" + javax_transaction_version,
        "org.codehaus.btm:btm:" + bitronix_version,
        "com.atomikos:atomikos-util:" + atomikos_version,
        "com.atomikos:transactions:" + atomikos_version,
        "com.atomikos:transactions-api:" + atomikos_version,
        "com.atomikos:transactions-jms:" + atomikos_version,
        "com.atomikos:transactions-jta:" + atomikos_version,
        "com.atomikos:transactions-jdbc:" + atomikos_version,
    ],
    fetch_sources = True,
    repositories = maven_repositories,
)
