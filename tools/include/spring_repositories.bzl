load("//tools/java:maven_jar.bzl", "maven_jar")

def spring_repositories(
        javax_inject_version = "1",
        jms_api_version = "2.0.1",
        spring_boot_version = "2.1.0.RELEASE",
        spring_version = "5.1.2.RELEASE",
        javax_transaction_version = "1.3",
        bitronix_version = "2.1.4",
        atomikos_version = "4.0.6"):
    maven_jar(
        name = "javax_inject_javax_inject",
        artifact = "javax.inject:javax.inject:" + javax_inject_version,
    )
    maven_jar(
        name = "javax_jms_javax_jms_api",
        artifact = "javax.jms:javax.jms-api:" + jms_api_version,
    )
    maven_jar(
        name = "org_springframework_boot_spring_boot",
        artifact = "org.springframework.boot:spring-boot:" + spring_boot_version,
    )
    maven_jar(
        name = "org_springframework_boot_spring_boot_autoconfigure",
        artifact = "org.springframework.boot:spring-boot-autoconfigure:" + spring_boot_version,
    )
    maven_jar(
        name = "org_springframework_boot_spring_boot_test",
        artifact = "org.springframework.boot:spring-boot-test:" + spring_boot_version,
    )
    maven_jar(
        name = "org_springframework_spring_aop",
        artifact = "org.springframework:spring-aop:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_context",
        artifact = "org.springframework:spring-context:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_beans",
        artifact = "org.springframework:spring-beans:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_core",
        artifact = "org.springframework:spring-core:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_expression",
        artifact = "org.springframework:spring-expression:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_jcl",
        artifact = "org.springframework:spring-jcl:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_jdbc",
        artifact = "org.springframework:spring-jdbc:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_tx",
        artifact = "org.springframework:spring-tx:" + spring_version,
    )
    maven_jar(
        name = "org_springframework_spring_test",
        artifact = "org.springframework:spring-test:" + spring_version,
    )
    maven_jar(
        name = "javax_transaction_javax_transaction_api",
        artifact = "javax.transaction:javax.transaction-api:" + javax_transaction_version,
    )
    maven_jar(
        name = "org_codehaus_btm_btm",
        artifact = "org.codehaus.btm:btm:" + bitronix_version,
    )
    maven_jar(
        name = "com_atomikos_atomikos_util",
        artifact = "com.atomikos:atomikos-util:" + atomikos_version,
    )
    maven_jar(
        name = "com_atomikos_transactions",
        artifact = "com.atomikos:transactions:" + atomikos_version,
    )
    maven_jar(
        name = "com_atomikos_transactions_api",
        artifact = "com.atomikos:transactions-api:" + atomikos_version,
    )
    maven_jar(
        name = "com_atomikos_transactions_jms",
        artifact = "com.atomikos:transactions-jms:" + atomikos_version,
    )
    maven_jar(
        name = "com_atomikos_transactions_jta",
        artifact = "com.atomikos:transactions-jta:" + atomikos_version,
    )
    maven_jar(
        name = "com_atomikos_transactions_jdbc",
        artifact = "com.atomikos:transactions-jdbc:" + atomikos_version,
    )
