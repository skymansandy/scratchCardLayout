package dev.skymansandy.scratchcardlayout.ui

import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class ScratchCardLayout : CardView, ScratchCard.ScratchCardInterface {
    private lateinit var scratchCard: ScratchCard

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        scratchCard = ScratchCard(context, attrs, defStyleAttr)
        scratchCard.setRevealListener(this)
        setupScratchView()
        resetScratch()
    }

    /**
     * Set the scratch brush width
     *
     * @param mScratchWidthDip width in dp (Use Utility method `ScratchCardUtils.dipToPx` for conversion)
     */
    fun setScratchWidthDip(mScratchWidthDip: Float) {
        scratchCard.setScratchWidthDip(mScratchWidthDip)
    }

    /**
     * Set the layout resource id of image to show as overlay for scratching
     * (`Drawable` image or `ColorDrawable`)
     *
     * @param mScratchDrawable layout resource id
     */
    fun setScratchDrawable(mScratchDrawable: Drawable?) {
        scratchCard.setScratchDrawable(mScratchDrawable)
    }

    /**
     * Scratch listener
     *
     * @param mListener listener object
     * (implement the interface in the type of the instance passed in which to listen to callbacks)
     */
    fun setScratchListener(mListener: ScratchListener) {
        scratchCard.setListener(mListener)
    }

    /**
     * Adding the scratch card to layout
     */
    private fun setupScratchView() {
        scratchCard.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        post { addView(scratchCard) }
    }

    /**
     * Stop or Interrupt scratch effect
     */
    private fun stopScratching() {
        scratchCard.visibility = View.GONE
    }

    /**
     * Reveal full layout when some threshold percent is scratched
     *
     * @param revealFullAtPercent threshold percent
     */
    fun setRevealFullAtPercent(revealFullAtPercent: Int) {
        scratchCard.setRevealFullAtPercent(revealFullAtPercent)
    }

    override fun onFullReveal() {
        stopScratching()
    }

    /**
     * Enable/Disable scratch effect
     * @param enableScratching true/false based on your choice
     */
    fun setScratchEnabled(enableScratching: Boolean) {
        scratchCard.setScratchEnabled(enableScratching)
    }

    /**
     * Reset scratch. (As if its a whole new scratch session
     */
    fun resetScratch() {
        setScratchEnabled(true)
        scratchCard.resetScratch()
    }

    /**
     * Reveal scratch. (Reveal whats underneath the scratch view)
     */
    fun revealScratch(){
        scratchCard.revealScratch()
    }
}