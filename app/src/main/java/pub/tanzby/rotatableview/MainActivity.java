package pub.tanzby.rotatableview;
import pub.tanzby.rotatecontrolview.RotateControlView;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
        rv.addContentView(img);
        rv.setOnRatateListener(new RotateControlView.OnRotateListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void OnRotate(float angle) {
                tv.setText(String.format("rotate angle: %f",angle));
            }
        });

    }
}
