# XPage
[![xp][xpsvg]][xp]  [![api][apisvg]][api]

一个非常方便的fragment页面框架

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)

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
* 支持Fragment之间、activity和fragment之间的数据交互。

## 1、演示（请star支持）
![](https://github.com/xuexiangjys/XPage/blob/master/img/1.gif)

### Demo下载

[![downloads][download-svg]][download-url]

![][download-img]

## 2、如何使用
目前支持主流开发工具AndroidStudio的使用，直接配置build.gradle，增加依赖即可.

### 2.1、Android Studio导入方法，添加Gradle依赖

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
  implementation 'com.github.xuexiangjys.XPage:xpage-lib:2.2.4'
  annotationProcessor 'com.github.xuexiangjys.XPage:xpage-compiler:2.2.4'
  //ButterKnife的sdk
  implementation 'com.jakewharton:butterknife:8.4.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
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

### 2.2、页面注册

#### 2.2.1、assets中注册

在assets文件夹中新建“corepage.json“，然后进行如下配置：
```
[
  {
    "name": "测试页面1",
    "classPath": "com.xuexiang.xpagedemo.fragment.TestFragment1",
    "params": ""
  },
  {
    "name": "测试页面2",
    "classPath": "com.xuexiang.xpagedemo.fragment.TestFragment2",
    "params": {
      "key1":"这是参数1的值",
      "key2":"这是参数2的值"
    }
  }，
]
```

#### 2.2.2、Application中注册

1.手动动态进行页面注册

```
PageConfig.getInstance()
        .setPageConfiguration(new PageConfiguration() { //页面注册
            @Override
            public List<PageInfo> registerPages(Context context) {
                List<PageInfo> pageInfos = new ArrayList<>();
                addPageInfoAndSubPages(pageInfos, MainFragment.class);
                pageInfos.add(PageConfig.getPageInfo(DateReceiveFragment.class));
                return pageInfos;        //手动注册页面
            }
        })
        .debug("PageLog")       //开启调试
        .enableWatcher(false)   //设置是否开启内存泄露监测
        .init(this);            //初始化页面配置
```

2.自动进行页面注册

使用apt自动生成的页面注册配置类 "moduleName"+PageConfig 的getPages()进行注册。

```
PageConfig.getInstance()
        .setPageConfiguration(new PageConfiguration() { //页面注册
            @Override
            public List<PageInfo> registerPages(Context context) {
                return AppPageConfig.getInstance().getPages(); //自动注册页面
            }
        })
        .debug("PageLog")       //开启调试
        .enableWatcher(false)   //设置是否开启内存泄露监测
        .init(this);            //初始化页面配置
```

### 2.3、页面跳转

> 使用XPage，Activity必须要继承`XPageActivity`,Fragment必须要继承`XPageFragment`，否则将无法调用页面跳转的`openPage`方法。

#### 2.3.1、携带数据

```
Bundle params = new Bundle();
switch(position) {
    case 0:
        params.putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, false);
        int id = (int) (Math.random() * 100);
        params.putString(DateReceiveFragment.KEY_EVENT_NAME, "事件" + id);
        params.putString(DateReceiveFragment.KEY_EVENT_DATA, "事件" + id + "携带的数据");
        openPage(DateReceiveFragment.class, params);
        break;
    case 1:
        params.putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, true);
        openPageForResult(DateReceiveFragment.class, params, 100);
        break;
    default:
        break;
}
```

#### 2.3.2、页面切换动画

```
switch(position) {
    case 0:
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.none);//没有动画
        break;
    case 1:
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.present);//由下到上动画
        break;
    case 2:
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.slide);//从左到右动画
        break;
    case 3:
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.fade);//渐变
        break;
    case 4:
        openPage(TestFragment.PAGE_NAME, null, CoreAnim.zoom);//放大
        break;
    default:
        break;
}
```

### 2.4、TitleBar样式自定义

```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/xpage_default_actionbar_color</item>
    <item name="colorPrimaryDark">@color/xpage_default_actionbar_color</item>
    <item name="colorAccent">@color/xpage_default_actionbar_color</item>

    <!--标题栏的背景颜色-->
    <item name="xpage_actionbar_color">@color/bar_color</item>
    <!--标题栏的高度, 默认52dp-->
    <item name="xpage_actionbar_height">60dp</item>
    <!--标题栏标题文字的大小，默认18sp-->
    <item name="xpage_actionbar_title_text_size">21sp</item>
    <!--标题栏副标题文字的大小，默认12sp-->
    <item name="xpage_actionbar_sub_text_size">14sp</item>
    <!--标题栏动作文字的大小，默认15sp-->
    <item name="xpage_actionbar_action_text_size">18sp</item>
    <!--标题栏动作图片的padding，默认5dp-->
    <item name="xpage_actionbar_action_padding">6dp</item>
    <!--标题栏两侧文字的padding，默认14dp-->
    <item name="xpage_actionbar_sidetext_padding">16dp</item>
</style>
```

### 2.5、复杂Activity界面容器的自定义

详细可参见[ComplexActivity](https://github.com/xuexiangjys/XPage/blob/master/app/src/main/java/com/xuexiang/xpagedemo/ComplexActivity.java)

1.自定义页面容器的布局，在布局中一定要包含id`fragment_container`。

```
<FrameLayout
    android:id="@id/fragment_container"
    android:layout_width="match_parent"
    android:layout_height="400dp">
</FrameLayout>
```

2.在XPageActivity中设置页面容器的布局ID
```
@Override
protected int getLayoutId() {
    return R.layout.activity_complex;
}
```

3.使用`changePage`方法切换Fragment。

```
changePage(TestFragment.PAGE_NAME, null, CoreAnim.none);
```

【注意】在切换Fragment的时候，fragment并不会走onResume和onPause生命周期，建议使用onHiddenChanged代替。

4.使用`getPage`方法获取指定的Fragment，就可以获取该fragment页面中的数据。

```
TabAFragment tabAFragment = getPage(TabAFragment.class);
if (tabAFragment != null) {
    ToastUtils.toast(tabAFragment.getData());
} else {
    ToastUtils.toast("页面还未加载！");
}
```


## 混淆配置

```
# fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature

# xpage
-keep class com.xuexiang.xpage.annotation.** { *; }
```

## 特别感谢
https://github.com/lizhangqu/CorePage/

## 联系方式

 [![](https://img.shields.io/badge/点击一键加入QQ交流群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)

![](https://github.com/xuexiangjys/XPage/blob/master/img/qq_group.jpg)

[xpsvg]: https://img.shields.io/badge/XPage-v2.2.4-brightgreen.svg
[xp]: https://github.com/xuexiangjys/XPage
[apisvg]: https://img.shields.io/badge/API-14+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=14

[download-svg]: https://img.shields.io/badge/downloads-1M-blue.svg
[download-url]: https://github.com/xuexiangjys/XPage/blob/master/apk/xpage_demo.apk?raw=true
[download-img]: https://github.com/xuexiangjys/XPage/blob/master/img/download.png