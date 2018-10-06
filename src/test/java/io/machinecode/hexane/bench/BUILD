load("//src/test/java/io/machinecode/hexane:deps.bzl", "database_properties", "hexane_bench_deps", "hexane_test_deps")

filegroup(
    name = "srcs",
    srcs = glob(
        include = ["*.java"],
        exclude = ["*Bench.java"],
    ),
)

java_library(
    name = "bench",
    srcs = [":srcs"],
    deps = hexane_bench_deps + [
        "//src/main/java/io/machinecode/hexane",
    ],
)

[
    java_binary(
        name = test[:-5],
        srcs = [test],
        jvm_flags = ["'%s'" % it for it in database_properties],
        main_class = "org.openjdk.jmh.Main",
        deps = hexane_bench_deps + [
            ":bench",
            "//src/main/java/io/machinecode/hexane",
            "//src/test/java/io/machinecode/hexane",
            "//tools:jmh",
            "@com_zaxxer_hikaricp//jar",
            "@com_mchange_mchange_commons_java//jar",
            "@com_mchange_c3p0//jar",
            "@commons_logging_commons_logging//jar",
            "@org_apache_commons_commons_pool2//jar",
            "@org_apache_commons_commons_dbcp2//jar",
            "@io_agroal_agroal_api//jar",
            "@io_agroal_agroal_pool//jar",
        ],
    )
    for test in glob(["*Bench.java"])
]
