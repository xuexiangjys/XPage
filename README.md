# XPage
[![](https://jitpack.io/v/xuexiangjys/XPage.svg)](https://jitpack.io/#xuexiangjys/XPage)
[![api](https://img.shields.io/badge/API-14+-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![I](https://img.shields.io/github/issues/xuexiangjys/XPage.svg)](https://github.com/xuexiangjys/XPage/issues)
[![Star](https://img.shields.io/github/stars/xuexiangjys/XPage.svg)](https://github.com/xuexiangjys/XPage)

一个非常方便的Fragment页面框架！还不赶紧点击[使用说明文档](https://github.com/xuexiangjys/XPage/wiki)，体验一下吧！

另外，你还可以参见[XPage视频教程](https://space.bilibili.com/483850585/channel/detail?cid=150979)进行学习。

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)   [![简书](https://img.shields.io/badge/简书-xuexiangjys-red.svg)](https://www.jianshu.com/u/6bf605575337)   [![掘金](https://img.shields.io/badge/掘金-xuexiangjys-brightgreen.svg)](https://juejin.im/user/598feef55188257d592e56ed)   [![知乎](https://img.shields.io/badge/知乎-xuexiangjys-violet.svg)](https://www.zhihu.com/people/xuexiangjys)

## X系列库快速集成

为了方便大家快速集成X系列框架库，我提供了一个空壳模版供大家参考使用: https://github.com/xuexiangjys/TemplateAppProject

除此之外，我还特别制作了几期[X系列视频教程](https://space.bilibili.com/483850585/channel/detail?cid=104998)供大家学习参考.

---

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


## 1、演示（请star支持）

![xpage.gif](https://img.rruu.net/image/5f7d926d8f059)

### Demo下载

[![downloads][download-svg]][download-url]

![][download-img]

---

## 2、如何使用

> 目前支持主流开发工具AndroidStudio的使用，直接配置build.gradle，增加依赖即可.

### 2.1、Android Studio导入方法，添加Gradle依赖

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

### 2.2、页面注册

#### 2.2.1、assets中静态注册

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

#### 2.2.2、Application中动态注册【推荐】

1.自动进行页面注册【推荐】

使用apt编译时自动生成的页面注册配置类 "moduleName"+PageConfig 的getPages()进行注册。

```
PageConfig.getInstance()
        .setPageConfiguration(new PageConfiguration() { //页面注册
            @Override
            public List<PageInfo> registerPages(Context context) {
                //自动注册页面,是编译时自动生成的，build一下就出来了。如果你还没使用@Page的话，暂时是不会生成的。
                return AppPageConfig.getInstance().getPages(); //自动注册页面
            }
        })
        .debug("PageLog")       //开启调试
        .setContainActivityClazz(XPageActivity.class) //设置默认的容器Activity
        .enableWatcher(false)   //设置是否开启内存泄露监测
        .init(this);            //初始化页面配置
```

【注意】：如果你的项目中只是增加了依赖，还没有使用@Page注解XPageFragment页面的话，在编译时是不会自动生成注册页面的！！

2.手动动态进行页面注册

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

### 2.3、使用PageOption进行页面操作【推荐】

使用`PageOption.to`进行页面选项构建。

```
PageOption.to(TestFragment.class) //跳转的fragment
    .setAnim(CoreAnim.zoom) //页面跳转动画
    .setRequestCode(100) //请求码，用于返回结果
    .setAddToBackStack(true) //是否加入堆栈
    .setNewActivity(true, ContainActivity.class) //是否使用新的Activity打开
    .putBoolean(DateReceiveFragment.KEY_IS_NEED_BACK, true) //传递的参数
    .open(this); //打开页面进行跳转
```

### 2.4、页面跳转

> 使用XPage，Activity必须要继承`XPageActivity`,Fragment必须要继承`XPageFragment`，否则将无法调用页面跳转的`openPage`方法。

#### 2.4.1、携带数据

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

#### 2.4.2、页面切换动画

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

### 2.5、TitleBar样式自定义

可以设置`XPageTitleBarStyle`主题样式来自定义标题栏的默认样式。

```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/xpage_default_actionbar_color</item>
    <item name="colorPrimaryDark">@color/xpage_default_actionbar_color</item>
    <item name="colorAccent">@color/xpage_default_actionbar_color</item>

    <!--标题栏的背景图片，优先使用背景图片，没有背景图片才使用背景颜色，可选-->
    <item name="xpage_actionbar_background">@null</item>
    <!--标题栏的背景颜色-->
    <item name="xpage_actionbar_color">@color/xpage_default_actionbar_color</item>
    <!--是否支持沉浸式标题栏, 默认false-->
    <item name="xpage_actionbar_immersive">false</item>
    <!--标题栏返回箭头, 默认R.drawable.xpage_ic_navigation_back_white-->
    <item name="xpage_actionbar_navigation_back">@drawable/xpage_ic_navigation_back_white</item>
    <!--标题栏的高度，默认52dp-->
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
    <item name="xpage_actionbar_side_text_padding">16dp</item>
    
    <item name="XPageTitleBarStyle">@style/XPageTitleBar.Custom</item>
</style>

<style name="XPageTitleBar.Custom">
    <item name="tb_immersive">false</item>
    <item name="tb_centerGravity">center</item>
</style>
```

### 2.6、利用XPage来写程序的Tab主页

详细可参见[BottomNavigationViewFragment](https://github.com/xuexiangjys/XPage/blob/master/app/src/main/java/com/xuexiang/xpagedemo/fragment/BottomNavigationViewFragment.java)

就像正常使用ViewPager加载Fragment那样。但是这里需要注意的两点是：

* 由于使用ViewPager进行加载，而非XPage,因此Fragment的initTitleBar方法需要被覆盖。

```
@Override
protected TitleBar initTitleBar() {
    //不使用@Page标注的一定要注意覆盖这个方法
    return null;
}
```

* 由于为了新开页面不影响Tab主页当前容器的状态，需要在打开新页面的使用设置使用新容器。

```
PageOption.to(TestFragment.class)
        //新建一个容器，以不影响当前容器
        .setNewActivity(true)
        .open(this);
```

### 2.7、复杂Activity界面容器的自定义

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

---

## 特别感谢
https://github.com/lizhangqu/CorePage/

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://img.rruu.net/image/5f871d00045da)

## 联系方式

[![](https://img.shields.io/badge/点击一键加入QQ交流群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)

![gzh_weixin.jpg](https://img.rruu.net/image/5f871cfff3194)

[download-svg]: https://img.shields.io/badge/downloads-1M-blue.svg
[download-url]: https://github.com/xuexiangjys/XPage/blob/master/apk/xpage_demo.apk?raw=true
[download-img]: https://img.rruu.net/image/5f7d92e8c1943
