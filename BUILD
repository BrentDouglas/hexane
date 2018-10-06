load("//tools:coverage.bzl", "coverage_html")
load("//tools/gerrit:java_doc.bzl", "java_doc")
load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "coverage_data",
    srcs = glob([
        "bazel-out/**/coverage.dat",
    ]),
)

coverage_html(
    name = "coverage",
    srcs = [
        "//src/main/java/io/machinecode/hexane:srcs",
    ],
    data = [
        "//:coverage_data",
    ],
)

java_doc(
    name = "javadoc",
    dest = "javadoc",
    libs = [
        "//src/main/java/io/machinecode/hexane:hexane",
    ],
    pkgs = [
        "io.machinecode.hexane",
    ],
    title = "Hexane",
)

pkg_tar(
    name = "site",
    extension = "tar.gz",
    deps = [
        ":javadoc",
        "//src/main/adoc:html",
        "//src/main/adoc:pdf",
    ],
)
