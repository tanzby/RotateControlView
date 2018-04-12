## RotateControlView

超级高仿 ios 相册编辑功能的旋转操作


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
  compile 'com.github.tanzby:RotateControlView:v0.99'
}
```

**Step 3.** In your style xml file

```xml

<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="pub.tanzby.rotatableview.MainActivity">

    <android.support.constraint.ConstraintLayout
        android:id="@+id/constraintLayout2"
        android:layout_width="match_parent"
        android:layout_height="320dp"
        android:background="#0ff">

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginStart="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:srcCompat="@drawable/ic_launcher_background" />

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginEnd="40dp"
            android:layout_marginTop="44dp"
            android:text="TextView"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </android.support.constraint.ConstraintLayout>

  <pub.tanzby.rotatecontrolview.RotateControlView
      android:id="@+id/rv"
      android:layout_width="0dp"
      android:layout_height="0dp"
      android:layout_marginBottom="8dp"
      android:layout_marginTop="8dp"
      android:background="#666"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintTop_toBottomOf="@+id/constraintLayout2" />

</android.support.constraint.ConstraintLayout>
```

**Step 3.** In your Java class
```java

public class MainActivity extends AppCompatActivity {

    RotateControlView rv;
    ImageView img;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        tv = findViewById(R.id.textView);
        rv = findViewById(R.id.rv);

        rv.addTargetView(img); // bind with your view wanted to control

        rv.setOnRotateOperatorListener(new RotateControlView.OnRotateOperatorListener() {
            @Override
            public void OnRotate(float angle) {
                tv.setText("angle: " + angle);
            }

            @Override
            public void OnClickRotate(float angle) {
                tv.setText("angle: " + angle);
            }
        });


    }
}
```
