plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "${GradleConfig.BuildConfig.applicationId}.ui"
    compileSdk = GradleConfig.BuildConfig.compileSdk

    defaultConfig {
        minSdk = GradleConfig.BuildConfig.minSdk
        testInstrumentationRunner = "${GradleConfig.BuildConfig.applicationId}.ui.core.runner.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = GradleConfig.BuildConfig.isMinifyEnabledDebug
        }
        release {
            isMinifyEnabled = GradleConfig.BuildConfig.isMinifyEnabledRelease
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            consumerProguardFiles("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = GradleConfig.javaVersion
        targetCompatibility = GradleConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = GradleConfig.javaVersionAsString
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }
    sourceSets {
        this[GradleConfig.SourceSet.Main.name].java.srcDirs(*GradleConfig.SourceSet.Main.sourceSets)
        this[GradleConfig.SourceSet.Test.name].java.srcDirs(*GradleConfig.SourceSet.Test.sourceSets)
        this[GradleConfig.SourceSet.AndroidTest.name].java.srcDirs(*GradleConfig.SourceSet.AndroidTest.sourceSets)
    }
}

dependencies {
    apiDependencies()

    implementationDependencies()

    kaptDependencies()

    kaptTest(libs.hilt.kapt)
    kaptAndroidTest(libs.hilt.kapt)

    testImplementationDependencies()

    androidTestImplementationDependencies()
}

fun DependencyHandlerScope.apiDependencies() {
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.viewmodel.compose)

    api(project(":ui-core"))
}

fun DependencyHandlerScope.implementationDependencies() {
    implementation(libs.hilt.android)

    implementation(project(":feature:home:ui"))
    implementation(project(":feature:movie:details:ui"))
}

fun DependencyHandlerScope.kaptDependencies() {
    kapt(libs.hilt.kapt)
    kapt(libs.hilt.work.kapt)
}

fun DependencyHandlerScope.testImplementationDependencies() {
    testImplementation(libs.junit)

    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)

    testImplementation(libs.kotlin.coroutines.test)

    testImplementation(libs.hilt.test)
}

fun DependencyHandlerScope.androidTestImplementationDependencies() {
    androidTestImplementation(libs.junit.android.ext)

    androidTestImplementation(libs.espresso)

    androidTestImplementation(libs.kotlin.coroutines.test)

    androidTestImplementation(libs.hilt.test)
    androidTestImplementation(libs.hilt.kapt)

    androidTestImplementation(libs.compose.ui.test.manifest.debug)
    androidTestImplementation(libs.compose.ui.test.junit)
}
