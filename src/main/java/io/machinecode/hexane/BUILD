load("//:deps.bzl", "build_deps")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "srcs",
    srcs = glob(["*.java"]),
)

java_library(
    name = "hexane",
    srcs = [":srcs"],
    deps = build_deps,
)
