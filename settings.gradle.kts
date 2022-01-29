pluginManagement {
    repositories {
        mavenLocal()
        maven( "https://maven.aliyun.com/repository/releases")
        maven( "https://maven.aliyun.com/repository/public")
        mavenCentral()
        maven( "https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
//这里改生成的插件名称
rootProject.name = "linya-miraikt-qqbot"