build_deps = [
    "@maven//:org_slf4j_slf4j_api",
    "@maven//:org_apache_logging_log4j_log4j_api",
    "@maven//:org_jboss_logging_jboss_logging",
]

test_deps = [
    "@maven//:junit_junit",
    "@maven//:org_mockito_mockito_all",
]

jmh_deps = [
    "@jmh_m2//:org_openjdk_jmh_jmh_core",
    "@jmh_m2//:net_sf_jopt_simple_jopt_simple",
    "@jmh_m2//:org_apache_commons_commons_math3",
]
