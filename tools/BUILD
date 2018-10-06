package(default_visibility = ["//visibility:public"])

java_library(
    name = "jmh",
    exported_plugins = [":jmh_plugin"],
    exports = [
        "@org_openjdk_jmh_jmh_core//jar",
    ],
)

java_plugin(
    name = "jmh_plugin",
    generates_api = True,
    processor_class = "org.openjdk.jmh.generators.BenchmarkProcessor",
    deps = [
        "@org_openjdk_jmh_jmh_core//jar",
        "@org_openjdk_jmh_jmh_generator_annprocess//jar",
    ],
)
