## ShadowFrameLayout继承至Framelayout

<img src="https://github.com/zhongruiAndroid/MyCardViewProject/blob/master/screenshots/shadow_image.png" alt="image"  width="540" height="auto">  
 

## [Demo.apk下载](https://raw.githubusercontent.com/zhongruiAndroid/MyCardViewProject/master/demo/demo.apk "apk文件")

| 属性                | 类型      | 说明                                         |
|---------------------|-----------|----------------------------------------------|
| bgColor             | color     | 内容区域背景,默认:白色                       |
| bgRadius            | dimension | 内容区域圆角,默认:0【bgColor如果为透明色设置radius无效】                          |
| bgRadiusLeftTop     | dimension | 内容区域_左上圆角,默认:0【bgColor如果为透明色设置radius无效】|
| bgRadiusRightTop    | dimension | 内容区域_右上圆角,默认:0【bgColor如果为透明色设置radius无效】|
| bgRadiusRightBottom | dimension | 内容区域_右下圆角,默认:0 【bgColor如果为透明色设置radius无效】|
| bgRadiusLeftBottom  | dimension | 内容区域_左下圆角,默认:0 【bgColor如果为透明色设置radius无效】|
| shadowAlpha         | float     | 阴影整体透明度0~1,默认值:1                   |
| shadowWidth         | dimension | 阴影宽度，默认:12dp                          |
| shadowOffsetLeft    | dimension | 内容区域向左偏移量,默认:0                    |
| shadowOffsetTop     | dimension | 内容区域向上偏移量,默认:0                    |
| shadowOffsetRight   | dimension | 内容区域向右偏移量,默认:0                    |
| shadowOffsetBottom  | dimension | 内容区域向下偏移量,默认:0                    |
| shadowStartColor    | color     | 阴影起始颜色,默认:#0A000000                  |
| shadowEndColor      | color     | 阴影结束颜色,默认:#00000000(透明色)          |
| shadowClipOutLength | dimension | 阴影向外偏移量,默认:0                        |
| shadowClipInLength  | dimension | 阴影向内偏移量,默认:0,【增加偏移量可增大阴影圆角】   |
| onlyLinear          | boolean   | 阴影是否使用线性渐变效果,默认:false          |
| controlPointFirstY  | fraction  | 控制阴影渐变效果的变量,取值范围:0~1,默认:0.8 |
| controlPointSecondY | fraction  | 控制阴影渐变效果的变量,取值范围:0~1,默认:1   |

```xml
 <com.github.mycardview.ShadowFrameLayout
     android:layout_width="120dp"
     android:layout_height="120dp"
     android:layout_gravity="center_horizontal"
     app:bgColor="@color/white"
     app:bgRadius="5dp"
     app:shadowClipInLength="4dp"
     app:shadowStartColor="#1C000000"
     app:shadowWidth="12dp">
         <TextView
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:layout_gravity="center"
             android:text="Android" />
</com.github.mycardview.ShadowFrameLayout>
```

| 最新版本号 | [ ![Download](https://api.bintray.com/packages/zhongrui/mylibrary/MyCardView/images/download.svg) ](https://bintray.com/zhongrui/mylibrary/MyCardView/_latestVersion) |
|--------|----|
```gradle
implementation 'com.github:MyCardView:版本号看上面'
```
