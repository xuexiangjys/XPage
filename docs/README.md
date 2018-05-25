# XPage
[![xp][xpsvg]][xp]  [![api][apisvg]][api]

一个非常方便的fragment页面框架

## 特征

* 支持assets下“corepage.json”静态配置Fragment页面信息。
* 支持Application中动态配置Fragment页面信息。
* 支持通过注解@Page的方式动态自动配置页面信息。
* 支持自定义Fragment页面信息配置。
* 支持4种默认Fragment页面切换动画。
* 支持Fragment页面间参数传递。
* 支持Fragment页面属性保存。
* 支持Fragment页面的onKeyDown、onFragmentResult等生命周期
* 支持Fragment和Fragment页面自由跳转以及数据交互。
* 支持导航栏通过注解的方式自动添加及设置。
* 支持进行内存泄露监测。
* 支持自定义TitleBar全局主题属性。


## 如何使用

1.先在项目根目录的 build.gradle 的 repositories 添加:
```
allprojects {
     repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2.然后在dependencies添加:

```
dependencies {
  ...
  //XPage
  implementation 'com.github.xuexiangjys.XPage:xpage-lib:2.2.0'
  annotationProcessor 'com.github.xuexiangjys.XPage:xpage-compiler:2.2.0'
  //butterknife的sdk
  implementation 'com.jakewharton:butterknife:8.4.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
  //leak
  debugImplementation 'com.squareup.leakcanary:leakcanary-android:1.5.4'
  releaseImplementation 'com.squareup.leakcanary:leakcanary-android-no-op:1.5.4'
  testImplementation 'com.squareup.leakcanary:leakcanary-android-no-op:1.5.4'
}
```

3.进行moduleName注册

```
defaultConfig {
    ...

    javaCompileOptions {
        annotationProcessorOptions {
            arguments = [ moduleName : project.getName() ]
        }
    }
}
```
【注意】：如果不注册的话，默认ModuleName为`app`。

## 联系方式

 [![](https://img.shields.io/badge/点我一键加入QQ群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)


[xpsvg]: https://img.shields.io/badge/XPage-v2.2.0-brightgreen.svg
[xp]: https://github.com/xuexiangjys/XPage
[apisvg]: https://img.shields.io/badge/API-14+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=14