load("@io_machinecode_tools//tools/java:maven_jar.bzl", "maven_jar")

def pool_repositories(
        hikari_version = "3.2.0",
        c3p0_version = "0.9.5.2",
        mchange_commons_java_version = "0.2.15",
        commons_dbcp2_version = "2.5.0",
        commons_pool_version = "2.6.0",
        commons_logging_version = "1.2",
        agroal_version = "1.2",
        tomcat_version = "9.0.12"):
    maven_jar(
        name = "com_zaxxer_hikaricp",
        artifact = "com.zaxxer:HikariCP:" + hikari_version,
    )

    maven_jar(
        name = "com_mchange_c3p0",
        artifact = "com.mchange:c3p0:" + c3p0_version,
    )
    maven_jar(
        name = "com_mchange_mchange_commons_java",
        artifact = "com.mchange:mchange-commons-java:" + mchange_commons_java_version,
    )

    maven_jar(
        name = "org_apache_commons_commons_dbcp2",
        artifact = "org.apache.commons:commons-dbcp2:" + commons_dbcp2_version,
    )
    maven_jar(
        name = "org_apache_commons_commons_pool2",
        artifact = "org.apache.commons:commons-pool2:" + commons_pool_version,
    )
    maven_jar(
        name = "commons_logging_commons_logging",
        artifact = "commons-logging:commons-logging:" + commons_logging_version,
    )

    maven_jar(
        name = "io_agroal_agroal_api",
        artifact = "io.agroal:agroal-api:" + agroal_version,
    )
    maven_jar(
        name = "io_agroal_agroal_pool",
        artifact = "io.agroal:agroal-pool:" + agroal_version,
    )

    maven_jar(
        name = "org_apache_tomcat_tomcat_jdbc",
        artifact = "org.apache.tomcat:tomcat-jdbc:" + tomcat_version,
    )
    maven_jar(
        name = "org_apache_tomcat_tomcat_juli",
        artifact = "org.apache.tomcat:tomcat-juli:" + tomcat_version,
    )
