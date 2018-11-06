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

load("//tools/include:jmh_repositories.bzl", "jmh_repositories")

jmh_repositories()

load("//tools/include:database_repositories.bzl", "database_repositories")

database_repositories()

load("//tools/include:pool_repositories.bzl", "pool_repositories")

pool_repositories()

load("//tools/include:spring_repositories.bzl", "spring_repositories")

spring_repositories()
