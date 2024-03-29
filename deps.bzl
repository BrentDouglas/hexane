build_deps = [
    "@provided_m2//:org_slf4j_slf4j_api",
    "@provided_m2//:org_apache_logging_log4j_log4j_api",
    "@provided_m2//:org_jboss_logging_jboss_logging",
]

test_deps = [
    "@m2//:junit_junit",
    "@m2//:org_mockito_mockito_core",
]

jmh_deps = [
    "@jmh_m2//:org_openjdk_jmh_jmh_core",
    "@jmh_m2//:net_sf_jopt_simple_jopt_simple",
    "@jmh_m2//:org_apache_commons_commons_math3",
]
