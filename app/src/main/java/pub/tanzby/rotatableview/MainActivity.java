package pub.tanzby.rotatableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import pub.tanzby.rotatecontrolview.RotateControlView;

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

        rv.addTargetView(img);

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
