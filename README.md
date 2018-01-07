# XPage
一个非常方便的fragment页面框架

## 1、演示
![](https://github.com/xuexiangjys/XPage/blob/master/img/1.gif)

## 2、如何使用
目前支持主流开发工具AndtoidStudio的使用，直接配置build.gradle，增加依赖即可.

### 2.1、Android Studio导入方法，添加Gradle依赖

   先在项目根目录的 build.gradle 的 repositories 添加:
```
    allprojects {
         repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```

 然后在dependencies添加:

```
    dependencies {
      ...
      implementation 'com.github.xuexiangjys:XPage:1.0'
      implementation 'com.alibaba:fastjson:1.2.8'
      //butterknife的sdk
      implementation 'com.jakewharton:butterknife:8.4.0'
      annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
      //leak
      debugCompile 'com.squareup.leakcanary:leakcanary-android:1.5'
      releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'
      testCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'
      
    }
```

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
```
    PageConfig.getInstance().setPageConfiguration(new PageConfiguration() {
        @Override
        public List<PageInfo> registerPages(Context context) {
            List<PageInfo> pageInfos = new ArrayList<>();
            addPageInfoAndSubPages(pageInfos, MainFragment.class);
            pageInfos.add(PageConfig.getPageInfo(DateReceiveFragment.class));
            return pageInfos;
        }
    }).debug("PageLog").init(this);
```

### 2.3、页面跳转

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
