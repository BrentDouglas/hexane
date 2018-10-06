load(":deps.bzl", "database_properties", "hexane_bench_deps", "hexane_test_deps")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "srcs",
    srcs = glob(
        include = ["*.java"],
        exclude = ["*Test.java"],
    ),
)

java_library(
    name = "hexane",
    srcs = [":srcs"],
    deps = hexane_test_deps + [
        "//src/main/java/io/machinecode/hexane",
    ],
)

[
    java_test(
        name = test[:-5],
        srcs = [test],
        jvm_flags = ["'%s'" % it for it in database_properties] + [
            "'-Dcom.google.testing.junit.runner.shouldInstallTestSecurityManager=false'"
        ],
        deps = hexane_test_deps + [
            ":hexane",
            "//src/main/java/io/machinecode/hexane",
        ],
    )
    for test in glob(["*Test.java"])
]