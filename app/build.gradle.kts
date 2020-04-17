plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Build.compileSdkVersion)
    buildToolsVersion(Build.buildToolsVersion)
    defaultConfig {
        applicationId = "com.yzy.myapplication"
        minSdkVersion(Build.minSdk)
        targetSdkVersion(Build.targetSdk)
        versionCode = Build.versionCode
        versionName = Build.versionName
        multiDexEnabled = true
        ndk {
            abiFilter("armeabi-v7a")
        }
        resConfigs("zh", "en")
    }
    androidExtensions {
        isExperimental = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    sourceSets["main"].assets.srcDir("src/assets")
    sourceSets["main"].jniLibs.srcDir("libs")
    signingConfigs {
        create("release") {
            storeFile = file("../yzy_mvrx.jks")
            storePassword = "yzy123"
            keyAlias = "yzy_com"
            keyPassword = "yzy123"
        }
        getByName("debug") {
            storeFile = file("../yzy_mvrx.jks")
            storePassword = "yzy123"
            keyAlias = "yzy_com"
            keyPassword = "yzy123"
        }
    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isZipAlignEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    android.applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                val appName = "demo"
                val path = projectDir.path + "/src/main/res/values/strings.xml"
                //正式版还是测试版
                var typeName = buildType.name
                typeName =
                    typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase()
                //build名称
                val buildVer = versionCode.toString()
                val buildName = "_build$buildVer"
                //编译日期
                val buildTime = "_" + org.apache.tools.ant.types.resources.selectors.Date()
                    .setDateTime("yyyyMMdd_HHmm")
                //后缀名
                val suffix = ".apk"
                //生成输出文件名称
                outputFileName = "${appName}${typeName}${buildName}${buildTime}${suffix}"
            }
        }
    }
}
dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":ybase"))
    //https://github.com/Justson/AgentWeb
    implementation("com.just.agentweb:agentweb:4.1.3")
    implementation(Deps.Kotlin.stdlib)
    kapt("com.github.bumptech.glide:compiler:4.11.0")
    implementation("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.2")
}


//apply plugin: 'com.android.application'
//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-android-extensions'
//apply plugin: 'kotlin-kapt'
//apply plugin: "androidx.navigation.safeargs.kotlin"
////打包时间
//static def releaseTime() {
//    return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
//}
//
//android {
//    compileSdkVersion rootProject.ext.android.compileSdkVersion
//    buildToolsVersion rootProject.ext.android.buildToolsVersion
//    defaultConfig {
//        applicationId rootProject.ext.android.applicationId
//        minSdkVersion rootProject.ext.android.minSdkVersion
//        targetSdkVersion rootProject.ext.android.targetSdkVersion
//        versionCode rootProject.ext.android.versionCode
//        versionName rootProject.ext.android.versionName
//        flavorDimensions "default"
//        multiDexEnabled true
//        resConfigs "zh", "en"//保留中文和英文资源
////        ndk {
////            abiFilters 'armeabi-v7a' //, 'arm64-v8a'
////        }
//    }
//    //使用Kotlin实验特性
//    androidExtensions {
//        experimental = true
//    }
//    kapt {
//        correctErrorTypes = true
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
////    dataBinding {
////        enabled = true
////    }
//    compileOptions {
//        sourceCompatibility rootProject.ext.android.javaSourceCompatibility
//        targetCompatibility rootProject.ext.android.javaTargetCompatibility
//    }
//    //配置不同版本的keystore
//    signingConfigs {
//        debug {
//            storeFile file(STORE_FILE_ABASE)
//            storePassword STORE_PASSWORD_ABASE
//            keyAlias KEY_ALIAS_ABASE
//            keyPassword KEY_PASSWORD_ABASE
//        }
//        release {
//            storeFile file(STORE_FILE_ABASE)
//            storePassword STORE_PASSWORD_ABASE
//            keyAlias KEY_ALIAS_ABASE
//            keyPassword KEY_PASSWORD_ABASE
//        }
//    }
//    buildTypes {
//        debug {
//            debuggable true
//            zipAlignEnabled false
//            shrinkResources false
//            minifyEnabled false
//            signingConfig signingConfigs.debug
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//        release {
//            debuggable false
//            zipAlignEnabled true
//            shrinkResources true
//            minifyEnabled true
//            signingConfig signingConfigs.release
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//
//    sourceSets {
//        main {
//            jniLibs.srcDirs = ['libs']
////            assets.srcDirs = ['src/assets', 'src/assets/']
//        }
//    }
//}
//
//android.applicationVariants.all { variant ->
//    variant.outputs.all {
//        //名称
//        String appName = ""
//        String path = getProjectDir().getPath() + "/src/main/res/values/strings.xml"
//        new XmlSlurper().parse(path)."string".find { name ->
//            if (name."@name" == "app_name") {
//                appName = name
//                return true
//            }
//        }
//        //正式版还是测试版
//        String typeName = buildType.name
//        typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase()
//        //build名称
//        String buildVer = String.valueOf(rootProject.ext.android.versionCode)
//        String buildName = "_build" + buildVer
//        //编译日期
//        String buildTime = "_" + String.valueOf(rootProject.ext.android.buildTime)
//        //打包的电脑
//        String computer = "_" + String.valueOf(rootProject.ext.android.computer)
//        //后缀名
//        String suffix = ".apk"
//        //生成输出文件名称
//        outputFileName = "${appName}${typeName}${buildName}${buildTime}${computer}${suffix}"
//    }
//}
//dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//    implementation rootProject.ext.dependencies.kotlin_jdk
//    implementation project(":ybase")
//    kapt rootProject.ext.dependencies.glide_compiler
//    implementation rootProject.ext.dependencies.bugly
//    implementation rootProject.ext.dependencies.bugly_native
//    implementation rootProject.ext.dependencies.viewpager2
//    implementation rootProject.ext.dependencies.lottie
//    implementation rootProject.ext.dependencies.live_event_bus
//    //web https://github.com/Justson/AgentWeb
//    implementation 'com.just.agentweb:agentweb:4.1.3'
//    implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.2'
//    //封面获取，视频压缩 https://github.com/microshow/RxFFmpeg
////    implementation 'com.github.microshow:RxFFmpeg:2.2.0'
//
//    //必选 https://github.com/dueeeke/DKVideoPlayer/wiki
////    implementation 'com.github.dueeeke.dkplayer:dkplayer-java:3.2.6'
////    //可选，包含StandardVideoController的实现
////    implementation 'com.github.dueeeke.dkplayer:dkplayer-ui:3.2.6'
////    //可选，使用exoplayer进行解码（推荐）
////    implementation 'com.github.dueeeke.dkplayer:player-exo:3.2.6'
//    //弹幕 https://github.com/bilibili/DanmakuFlameMaster
////    implementation 'com.github.ctiao:DanmakuFlameMaster:0.9.25'
////    implementation 'com.github.ctiao:ndkbitmap-armv7a:0.9.21'
////    api 'com.trello.rxlifecycle3:rxlifecycle-android-lifecycle:3.1.0'
//    //图片加载 https://github.com/panpf/sketch
////    api 'me.panpf:sketch:2.7.1'
//    //图片选择 https://github.com/LuckSiege/PictureSelector
////    api 'com.github.LuckSiege.PictureSelector:picture_library:v2.5.1'
//    //封面加载 https://github.com/wseemann/FFmpegMediaMetadataRetriever
//
////    implementation 'com.github.wseemann:FFmpegMediaMetadataRetriever-native-armeabi-v7a:1.0.15'
////    implementation 'com.github.xiaohaibin:XBanner:androidx_v1.0.2'
////    api 'com.github.wseemann:FFmpegMediaMetadataRetriever-armeabi-v7a:1.0.15'
//    // Dynamic Feature Module Support
////    implementation "androidx.navigation:navigation-dynamic-features-fragment:2.3.0-alpha04"
//}
////放在这省的忘
////@SuppressLint("ParcelCreator")
////@Parcelize
