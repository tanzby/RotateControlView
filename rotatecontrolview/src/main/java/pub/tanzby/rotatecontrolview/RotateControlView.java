package pub.tanzby.rotatecontrolview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tan on 2018/4/10.
 */

public class RotateControlView extends RelativeLayout {

    RotateControlClockView rccv;
    ImageView mBotton;
    OnRotateOperatorListener mOnRotateOperatorListener;
    float extra_angle = 0, last_angle = 0;

    public RotateControlView(Context context) {
        this(context, null);
    }

    public RotateControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.rotatecontrollayout, this);


        rccv = new RotateControlClockView(context);
        mBotton = view.findViewById(R.id.imBut_rotationcontrolview);

        this.addView(rccv, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        setEven();
    }

    public void setOnRotateOperatorListener(OnRotateOperatorListener Listener) {
        this.mOnRotateOperatorListener = Listener;
        if (null != rccv) {
            setEven();
        }
    }

    public void setEven() {
        rccv.setOnRatateListener(new RotateControlClockView.OnRotateListener() {
            @Override
            public void OnRotate(float angle) {
                last_angle = angle;
                mOnRotateOperatorListener.OnRotate(angle);
            }
        });

        mBotton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                extra_angle = (90 + extra_angle) % 360;
                rccv.setTargetViewRotate(last_angle + extra_angle);
                mOnRotateOperatorListener.OnClickRotate(last_angle + extra_angle);
            }
        });
    }

    public void addTargetView(View view) {
        rccv.addTargetView(view);
    }

    public interface OnRotateOperatorListener {
        void OnRotate(float angle);

        void OnClickRotate(float angle);
    }


    static private class RotateControlClockView extends View {
        //region 构造器
        private View contentView;
        private Paint mPaint = new Paint();
        private OnRotateListener mListener;
        private float last_angle;
        //region 渲染与事件
        private Integer mLastX;
        private float angle;

        public RotateControlClockView(Context context) {
            super(context);
            angle = 0;
            mPaint.setTextSize(32);
            mPaint.setAlpha(200);
            mPaint.setColor(Color.parseColor("#d0eeeeee"));
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            setLongClickable(true); // enable move even

        }

        public RotateControlClockView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public void setOnRatateListener(OnRotateListener Listener) {
            this.mListener = Listener;
        }

        //endregion

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = widthSize / 5;

            int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
            super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
        }

        //region 操作
        public void addTargetView(View view) {
            this.contentView = view;
        }
        //endregion

        public void setTargetViewRotate(float angle) {
            boolean is_circle = false;
            if (angle < last_angle - 90) {
                angle = 360 + angle;
                is_circle = true;
            }
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(contentView,
                    "rotation", last_angle, angle);//添加旋转动画，旋转中心默认为控件中点
            last_angle = angle;
            objectAnimator.setDuration(128);//设置动画时间
            objectAnimator.setInterpolator(new AccelerateInterpolator());//动画时间线性渐变
            objectAnimator.start();
            if (is_circle) {
                last_angle -= 360;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            Integer x = (int) event.getRawX();
            Integer y = (int) event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    float dx = mLastX - x;
                    if (angle < 40.0f && dx > 0 || dx < 0 && angle > -40.0f) {
                        angle = dx / 10.0f + angle;

                        if (null != contentView) contentView.setRotation(angle);
                        if (null != mListener) mListener.OnRotate(angle);

                        postInvalidate();
                    }
                    break;
                }
            }
            mLastX = x;
            return super.onTouchEvent(event);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int radius = width / 2;

            canvas.translate(radius, height / 2 - radius);
            Rect r = new Rect();
            canvas.rotate(-90 + angle);
            int circle_radius = height / 28;
            for (int i = 90; i >= -90; i -= 2) {
                if (i % 10 == 0) {
                    String str = i + "";
                    mPaint.getTextBounds(str, 0, str.length(), r);
                    canvas.drawText(i + "", -r.width() / 2, radius - height / 8, mPaint);
                    canvas.drawCircle(0, radius - circle_radius, circle_radius, mPaint);
                } else {
                    canvas.drawCircle(0, radius - circle_radius, circle_radius / 2, mPaint);
                }
                canvas.rotate(2);
            }
            canvas.rotate(-92 - angle);


            Path path = new Path();
            path.moveTo(0, radius + circle_radius * 3);
            path.lineTo(circle_radius * 3, radius + circle_radius * 7);
            path.lineTo(-circle_radius * 3, radius + circle_radius * 7);
            path.close();
            canvas.drawPath(path, mPaint);
            canvas.rotate(90 + angle);

        }

        public interface OnRotateListener {
            void OnRotate(float angle);
        }

        //endregion
    }

}
