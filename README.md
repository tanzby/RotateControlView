## RotateControlView

超级高仿 ios 相册编辑功能的旋转操作！有空再好好调UI，现在

* 高度在ConstraintLayout下不能 match_constraint,必须要设置一个值，要不然你就自己调吧
* 在其他Layout 的时候，高度根据宽度自己调整

![sreenShot](screenshot/screenShow.gif)

## usage

[![](https://jitpack.io/v/tanzby/RotateControlView.svg)](https://jitpack.io/#tanzby/RotateControlView)

**Step 1.** Add the JitPack repository to your build file

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency

```
dependencies {
  implementation 'com.github.tanzby:RotateControlView:5720ceb4cb'
}
```
