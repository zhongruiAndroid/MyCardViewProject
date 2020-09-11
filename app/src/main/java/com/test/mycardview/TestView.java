package com.test.mycardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TestView extends View {
    public TestView(Context context) {
        super(context);
        init(null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private Paint paint;
    private void init(AttributeSet attrs) {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLUE);

    }
    public PointF test(float fraction, PointF start, PointF control1, PointF control2, PointF endPoint){
        PointF pointF = new PointF();
        pointF.x = (float) (Math.pow((1 - fraction),3)*start.x +3 * control1.x*fraction*Math.pow((1-fraction),2)+3*control2.x*Math.pow(fraction,2)*(1-fraction)+endPoint.x*Math.pow(fraction,3));
        pointF.y = (float) (Math.pow((1 - fraction),3)*start.y +3 * control1.y*fraction*Math.pow((1-fraction),2)+3*control2.y*Math.pow(fraction,2)*(1-fraction)+endPoint.y*Math.pow(fraction,3));
        return pointF;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < getWidth() ; i+=10) {

            PointF test = test(i * 1f / getWidth(), new PointF(0, getHeight()), new PointF(0.6f * getWidth(), getHeight()), new PointF(getWidth(), 0.6f * getHeight()), new PointF(getWidth(), 0));
            canvas.drawPoint(test.x,test.y,paint);
        }
    }
}
