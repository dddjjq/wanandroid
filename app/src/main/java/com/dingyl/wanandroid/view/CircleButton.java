package com.dingyl.wanandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CircleButton extends AppCompatImageView {


    public CircleButton(Context context) {
        super(context);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas){
        Drawable drawable = getDrawable();
        if (drawable == null){
            return;
        }
        if (getWidth() == 0 || getHeight() == 0){
            return;
        }
        BitmapDrawable bd = (BitmapDrawable)drawable;
        Bitmap b = bd.getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888,true);
        Bitmap roundBitmap = getRoundBitmap(bitmap,getWidth());
        canvas.drawBitmap(roundBitmap,0,0,null);
    }

    private Bitmap getRoundBitmap(Bitmap bitmap,int radius){
        Bitmap tempBitmap;
        if (bitmap.getWidth() != getWidth() || bitmap.getHeight() != getHeight()){
            tempBitmap = Bitmap.createScaledBitmap(bitmap,radius,radius,false);
        }else {
            tempBitmap = bitmap;
        }
        Bitmap out = Bitmap.createBitmap(tempBitmap.getWidth(),tempBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Rect rect = new Rect(0,0,tempBitmap.getWidth(),tempBitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#BAB399"));
        Canvas canvas = new Canvas(out);
        canvas.drawARGB(0,0,0,0);
        canvas.drawCircle(tempBitmap.getWidth()/2+0.7f,tempBitmap.getHeight()/2+0.7f,
                tempBitmap.getWidth()/2+0.1f,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(tempBitmap,rect,rect,paint);
        return out;
    }
}
