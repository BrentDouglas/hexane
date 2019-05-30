workspace(name = "io_machinecode_hexane")

#local_repository(
#    name = "io_machinecode_tools",
#    path = "../tools",
#)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

io_machinecode_tools_version = "5ccba3d8f0f85b583cff6ec6d5ffa282281483f8"

http_archive(
    name = "io_machinecode_tools",
    sha256 = "ef39d200ac04b97dee47c0ff5d4affc521c6fa2538969f44df89b61507b2ce82",
    strip_prefix = "tools-" + io_machinecode_tools_version,
    urls = ["https://github.com/BrentDouglas/tools/archive/%s.tar.gz" % io_machinecode_tools_version],
)

load("@io_machinecode_tools//tools/java:devserver.bzl", "devserver_certificates")

devserver_certificates(
    name = "io_machinecode_devserver_certificates",
    hosts = [
        "localhost",
        "0.0.0.0",
    ],
)

load("@io_machinecode_tools//tools/java:maven_jar.bzl", "maven_jar")

load("@io_machinecode_tools//imports:skydoc_repositories.bzl", "skydoc_repositories")

skydoc_repositories()

load("@io_machinecode_tools//imports:nodejs_repositories.bzl", "nodejs_repositories")

nodejs_repositories()

load("@io_machinecode_tools//imports:nodejs_binary_repositories.bzl", "nodejs_binary_repositories")

nodejs_binary_repositories()

load("@build_bazel_rules_nodejs//:defs.bzl", "yarn_install")

yarn_install(
    name = "npm",
    package_json = "//:package.json",
    yarn_lock = "//:yarn.lock",
)

load("@io_bazel_rules_sass//:defs.bzl", "sass_repositories")

sass_repositories()

load("@io_bazel_skydoc//skylark:skylark.bzl", "skydoc_repositories")

skydoc_repositories()

junit_version = "4.12"

mockito_version = "1.10.19"

maven_jar(
    name = "junit_junit",
    artifact = "junit:junit:" + junit_version,
)

maven_jar(
    name = "org_mockito_mockito_all",
    artifact = "org.mockito:mockito-all:" + mockito_version,
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

load("@io_machinecode_tools//imports:build_repositories.bzl", "build_repositories")

build_repositories()

load("@io_machinecode_tools//imports:checkstyle_repositories.bzl", "checkstyle_repositories")

checkstyle_repositories()

load("@io_machinecode_tools//imports:format_repositories.bzl", "format_repositories")

format_repositories()

load("@io_machinecode_tools//imports:go_repositories.bzl", "go_repositories")

go_repositories()

load("@io_bazel_rules_go//go:def.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains()

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies")

gazelle_dependencies()

load("@io_machinecode_tools//imports:devsrv_repositories.bzl", "devsrv_repositories")

devsrv_repositories()

load("@io_machinecode_tools//imports:undertow_repositories.bzl", "undertow_repositories")

undertow_repositories()

load("@io_machinecode_tools//imports:jmh_repositories.bzl", "jmh_repositories")

jmh_repositories()

load("//tools/include:database_repositories.bzl", "database_repositories")

database_repositories()

load("//tools/include:pool_repositories.bzl", "pool_repositories")

pool_repositories()

load("//tools/include:spring_repositories.bzl", "spring_repositories")

spring_repositories()
