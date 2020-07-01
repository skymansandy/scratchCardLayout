package dev.skymansandy.scratchcardlayout.ui

import dev.skymansandy.scratchcardlayout.R
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.util.ScratchCardUtils
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

internal class ScratchCard(context: Context, attrs: AttributeSet?, defStyle: Int) : View(context, attrs, defStyle) {

    private var mPath: Path? = null
    private var mInnerPaint: Paint? = null
    private var mOuterPaint: Paint? = null
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mScratchDrawable: Drawable? = null
    private var mListener: ScratchListener? = null
    private var mScratchWidth = 0f
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f
    private var mRevealFullAtPercent = 0
    private var mRevealListener: ScratchCardInterface? = null
    private var mEnableScratching = false

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        @SuppressLint("CustomViewStyleable") val a = context.obtainStyledAttributes(attrs, R.styleable.ScratchCardLayout)
        mScratchDrawable = a.getDrawable(R.styleable.ScratchCardLayout_scratchDrawable)
        mScratchWidth = a.getDimension(R.styleable.ScratchCardLayout_scratchWidth, ScratchCardUtils.dipToPx(context, 30f))
        mRevealFullAtPercent = a.getInteger(R.styleable.ScratchCardLayout_scratchRevealFullAtPercent, 100)
        mEnableScratching = a.getBoolean(R.styleable.ScratchCardLayout_scratchEnabled, true)
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        setupScratchDrawableOnView()
        if (mPath == null) {
            mPath = Path()
        }
        if (mInnerPaint == null) {
            mInnerPaint = Paint()
            mInnerPaint!!.isAntiAlias = true
            mInnerPaint!!.isDither = true
            mInnerPaint!!.style = Paint.Style.STROKE
            mInnerPaint!!.isFilterBitmap = true
            mInnerPaint!!.strokeJoin = Paint.Join.ROUND
            mInnerPaint!!.strokeCap = Paint.Cap.ROUND
            mInnerPaint!!.strokeWidth = mScratchWidth
            mInnerPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        if (mOuterPaint == null) {
            mOuterPaint = Paint()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mEnableScratching) return true
        val currentTouchX = event.x
        val currentTouchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath?.reset()
                mPath?.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> mPath?.lineTo(currentTouchX, currentTouchY)
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(currentTouchX - mLastTouchX)
                val dy = abs(currentTouchY - mLastTouchY)
                if (dx >= 4 || dy >= 4) {
                    val x1 = mLastTouchX
                    val y1 = mLastTouchY
                    val x2 = (currentTouchX + mLastTouchX) / 2
                    val y2 = (currentTouchY + mLastTouchY) / 2
                    mPath?.quadTo(x1, y1, x2, y2)
                }
                if (mListener != null) {
                    val width = mBitmap?.width
                    val height = mBitmap?.height
                    val total = width!! * height!!
                    var count = 0
                    var i = 0
                    while (i < width) {
                        var j = 0
                        while (j < height) {
                            if (mBitmap?.getPixel(i, j) == 0x00000000) count++
                            j += 3
                        }
                        i += 3
                    }
                    val percentCompleted = (count.toFloat() / total * 9 * 100).toInt()
                    when {
                        percentCompleted == 0 -> mListener?.onScratchStarted()
                        percentCompleted == 100 -> stopScratchingAndRevealFull()
                        percentCompleted >= mRevealFullAtPercent -> stopScratchingAndRevealFull()
                        else -> mListener?.onScratchProgress(parent as ScratchCardLayout, percentCompleted)
                    }
                }
            }
        }
        mCanvas?.drawPath(mPath!!, mInnerPaint!!)
        mLastTouchX = currentTouchX
        mLastTouchY = currentTouchY
        invalidate()
        return true
    }

    private fun stopScratchingAndRevealFull() {
        if (mListener != null) {
            if (mRevealListener != null) {
                mRevealListener?.onFullReveal()
            }
            mListener?.onScratchComplete()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(mBitmap!!, 0f, 0f, mOuterPaint)
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mBitmap != null) {
            mBitmap?.recycle()
            mBitmap = null
        }
    }

    fun setScratchDrawable(mScratchDrawable: Drawable?) {
        this.mScratchDrawable = mScratchDrawable
    }

    fun setScratchWidthDip(mScratchWidth: Float) {
        this.mScratchWidth = mScratchWidth
        if (mInnerPaint != null) {
            mInnerPaint?.strokeWidth = mScratchWidth
        }
    }

    fun setListener(mListener: ScratchListener?) {
        this.mListener = mListener
    }

    fun setRevealFullAtPercent(mRevealFullAtPercent: Int) {
        this.mRevealFullAtPercent = mRevealFullAtPercent
    }

    fun setRevealListener(scratchCardLayout: ScratchCardLayout?) {
        mRevealListener = scratchCardLayout
    }

    fun setScratchEnabled(enableScratching: Boolean) {
        mEnableScratching = enableScratching
    }

    fun resetScratch() {
        if (width != 0 && height != 0) {
            visibility = VISIBLE
            setupScratchDrawableOnView()
            invalidate()
        }
    }

    fun revealScratch() {
        stopScratchingAndRevealFull()
    }

    private fun setupScratchDrawableOnView() {
        if (mBitmap != null) mBitmap!!.recycle()
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
        if (mScratchDrawable != null) {
            mScratchDrawable?.setBounds(0, 0, mBitmap!!.width, mBitmap!!.height)
            mScratchDrawable?.draw(mCanvas!!)
        } else {
            mCanvas!!.drawColor(-0x3f3f40)
        }
    }

    internal interface ScratchCardInterface {
        fun onFullReveal()
    }
}