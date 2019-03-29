package in.codeshuffle.scratchcardview.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import in.codeshuffle.scratchcardview.R;
import in.codeshuffle.scratchcardview.listener.ScratchListener;
import in.codeshuffle.scratchcardview.util.Utils;

class ScratchCard extends View {

    private Drawable mScratchDrawable;
    private float mScratchWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private ScratchListener mListener;
    private float mLastTouchX;
    private float mLastTouchY;

    ScratchCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScratchCardLayout);
        mScratchDrawable = a.getDrawable(R.styleable.ScratchCardLayout_scratchDrawable);
        mScratchWidth = a.getDimension(R.styleable.ScratchCardLayout_scratchWidth, Utils.dipToPx(context, 20));
        a.recycle();
    }

    public void setScratchDrawable(Drawable mScratchDrawable) {
        this.mScratchDrawable = mScratchDrawable;
    }

    public void setScratchWidth(float mScratchWidth) {
        this.mScratchWidth = mScratchWidth;
    }

    public void setListener(ScratchListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);

        if (mBitmap != null)
            mBitmap.recycle();

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        if (mScratchDrawable != null) {
            mScratchDrawable.setBounds(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            mScratchDrawable.draw(mCanvas);
        } else {
            mCanvas.drawColor(0xFFC0C0C0);
        }

        if (mPath == null) {
            mPath = new Path();
        }

        if (mInnerPaint == null) {
            mInnerPaint = new Paint();
            mInnerPaint.setAntiAlias(true);
            mInnerPaint.setDither(true);
            mInnerPaint.setStyle(Paint.Style.STROKE);
            mInnerPaint.setFilterBitmap(true);
            mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
            mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
            mInnerPaint.setStrokeWidth(mScratchWidth);
            mInnerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        if (mOuterPaint == null) {
            mOuterPaint = new Paint();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentTouchX = event.getX();
        float currentTouchY = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
                mPath.lineTo(currentTouchX, currentTouchY);
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(currentTouchX - mLastTouchX);
                float dy = Math.abs(currentTouchY - mLastTouchY);
                if (dx >= 4 || dy >= 4) {
                    float x1 = mLastTouchX;
                    float y1 = mLastTouchY;
                    float x2 = (currentTouchX + mLastTouchX) / 2;
                    float y2 = (currentTouchY + mLastTouchY) / 2;
                    mPath.quadTo(x1, y1, x2, y2);
                }

                if (mListener != null) {
                    int width = mBitmap.getWidth();
                    int height = mBitmap.getHeight();
                    int total = width * height;
                    int count = 0;
                    for (int i = 0; i < width; i += 3) {
                        for (int j = 0; j < height; j += 3) {
                            if (mBitmap.getPixel(i, j) == 0x00000000)
                                count++;
                        }
                    }
                    float percentCompleted = (((float) count) / total * 9) * 100;
                    if (percentCompleted == 0) {
                        mListener.onScratchStarted();
                    } else if (percentCompleted == 100) {
                        mListener.onScratchComplete();
                    } else {
                        mListener.onScratchProgress((ScratchCardLayout) getParent(), (int) percentCompleted);
                    }
                }
                break;
        }

        mCanvas.drawPath(mPath, mInnerPaint);

        mLastTouchX = currentTouchX;
        mLastTouchY = currentTouchY;

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mOuterPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
