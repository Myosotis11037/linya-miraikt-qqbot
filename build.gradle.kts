plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.8.0"
}

group = "com.linya"
version = "0.1.5"

val ktorm = "3.3.0"
val ktorVersion = "1.5.1"
val yamlKtVersion = "0.10.2"
val httpcomponents = "4.5.4"


repositories {
    mavenLocal()

    // °¢ÀïÔÆÔÆÐ§²Ö¿â£ºhttps://maven.aliyun.com/mvn/guide
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    // »ªÎª¿ªÔ´¾µÏñ£ºhttps://mirrors.huaweicloud.com
    maven("https://repo.huaweicloud.com/repository/maven")
    // JitPack Ô¶³Ì²Ö¿â£ºhttps://jitpack.io
    maven("https://jitpack.io")

    // MavenCentral Ô¶³Ì²Ö¿â£ºhttps://mvnrepository.com
    mavenCentral()
    gradlePluginPortal()
    google()
}
dependencies {
    compileOnly("net.mamoe.yamlkt:yamlkt-jvm:$yamlKtVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.30-M1")

    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("org.ktorm:ktorm-core:$ktorm")
    implementation("com.zaxxer:HikariCP:4.0.2")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("org.slf4j:slf4j-log4j12:2.0.0-alpha1")
    implementation("net.mamoe.yamlkt:yamlkt-jvm:$yamlKtVersion")


    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("org.apache.httpcomponents:httpclient:$httpcomponents")
    implementation("org.apache.httpcomponents:fluent-hc:$httpcomponents")
    implementation("org.apache.httpcomponents:httpmime:$httpcomponents")

    implementation("org.ktorm:ktorm-core:$ktorm")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("com.zaxxer:HikariCP:4.0.2")
}
