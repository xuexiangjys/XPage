# XPage
[![](https://jitpack.io/v/xuexiangjys/XPage.svg)](https://jitpack.io/#xuexiangjys/XPage)
[![api](https://img.shields.io/badge/API-14+-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![I](https://img.shields.io/github/issues/xuexiangjys/XPage.svg)](https://github.com/xuexiangjys/XPage/issues)
[![Star](https://img.shields.io/github/stars/xuexiangjys/XPage.svg)](https://github.com/xuexiangjys/XPage)

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
* 支持自定义Fragment页面容器。
* 支持自定义Activity页面容器。
* 支持Fragment之间、activity和fragment之间的数据交互。
* 兼容kotlin和androidx。

## 如何使用

1.先在项目根目录的 `build.gradle` 的 `repositories` 添加:
```
allprojects {
     repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2.然后在dependencies添加:

以下是版本说明，选择一个即可。

* androidx版本：3.0.0及以上

```
dependencies {
  ...
  // XPage
  implementation 'com.github.xuexiangjys.XPage:xpage-lib:3.0.3'
  annotationProcessor 'com.github.xuexiangjys.XPage:xpage-compiler:3.0.3'
  // ButterKnife的sdk
  implementation 'com.jakewharton:butterknife:10.1.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'
}
```

* support版本：2.3.0及以下

```
dependencies {
  ...
  // XPage
  implementation 'com.github.xuexiangjys.XPage:xpage-lib:2.3.0'
  annotationProcessor 'com.github.xuexiangjys.XPage:xpage-compiler:2.3.0'
  // ButterKnife的sdk
  implementation 'com.jakewharton:butterknife:8.4.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
}
```

【注意】如果你使用的是kotlin，请使用如下配置:

```
apply plugin: 'kotlin-kapt'

dependencies {
  ...
  //XPage
  implementation 'com.github.xuexiangjys.XPage:xpage-lib:3.0.3'
  kapt 'com.github.xuexiangjys.XPage:xpage-compiler:3.0.3'
  //ButterKnife的sdk
  implementation 'com.jakewharton:butterknife:10.1.0'
  kapt 'com.jakewharton:butterknife-compiler:10.1.0'
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
