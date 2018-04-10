package pub.tanzby.rotatecontrolview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tan on 2018/4/9.
 */

public class RotateControlView extends View {

    //region 构造器
    private View contentView;
    private Paint mPaint = new Paint();
    private OnRotateListener mListener;

    public RotateControlView(Context context) {
        super(context);
    }

    public RotateControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        angle = 0;

        mPaint.setTextSize(32);
        mPaint.setAlpha(200);
        mPaint.setColor(Color.parseColor("#d0eeeeee"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        setLongClickable(true); // enable move even
    }

    public void setOnRatateListener(OnRotateListener Listener){
        this.mListener = Listener;
    }

    public interface OnRotateListener{
        void OnRotate(float angle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = widthSize/5;

        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    //endregion

    //region 操作
    public void addContentView(View view){
        this.contentView = view;
    }
    //endregion

    //region 渲染与事件
    private Integer mLastX;
    private float angle;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Integer x = (int)event.getRawX();
        Integer y = (int)event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:{
                float dx = mLastX-x;
                if (angle<40.0f && dx >0 || dx <0 && angle>-40.0f){
                    angle = dx/10.0f+angle;

                    if (null!=contentView) contentView.setRotation(angle);
                    if (null!=mListener) mListener.OnRotate(angle);

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
        int radius = width/2;
        canvas.translate(radius, height/2-radius);
        Rect r =  new Rect();
        canvas.rotate(-90+angle);
        int circle_radius = height/28;
        for (int i = 90; i>=-90; i-=2){
            if (i%10==0){
                String str = i+"";
                mPaint.getTextBounds(str,0,str.length(),r);
                canvas.drawText(i+"",-r.width()/2,radius-height/8,mPaint);
                canvas.drawCircle(0,radius-circle_radius,circle_radius,mPaint);
            }
            else {
                canvas.drawCircle(0, radius-circle_radius, circle_radius/2, mPaint);
            }
            canvas.rotate(2);
        }
        canvas.rotate(-92-angle);
        Path path = new Path();
        path.moveTo(0,radius+circle_radius*3 );
        path.lineTo(circle_radius*3,radius+circle_radius*7);
        path.lineTo(-circle_radius*3, radius+circle_radius*7 );
        path.close();
        canvas.drawPath(path, mPaint);
        canvas.rotate(90+angle);
    }

    //endregion
}
