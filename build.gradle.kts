import com.github.gradle.node.npm.task.NpxTask

plugins {
    java
    war
    
    id("com.github.node-gradle.node") version "7.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.mcp.sdk)
    implementation(libs.slf4j.api)

    runtimeOnly(libs.slf4j.runtime.log4j2)
    
    compileOnly(libs.servlet.api)
}

node {
    download = true
    version = "24.11.0"

    workDir = rootProject.layout.buildDirectory.get().dir("nodejs")
    nodeProjectDir = rootProject.rootDir
}

tasks.npmInstall {
    inputs.file(rootProject.rootDir.resolve("package.json"));

    val packageLockFile = rootProject.rootDir.resolve("package-lock.json");
    if (packageLockFile.exists()) {
        inputs.file(packageLockFile)
    }

    outputs.file(packageLockFile)
    outputs.dir(rootProject.rootDir.resolve("node_modules"))
}

tasks.register<NpxTask>("runMCPInspector") {
    dependsOn("npmInstall")
    
    command = "@modelcontextprotocol/inspector"
    
    workingDir = rootProject.rootDir
}
