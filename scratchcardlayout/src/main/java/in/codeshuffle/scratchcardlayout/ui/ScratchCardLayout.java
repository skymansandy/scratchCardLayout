package in.codeshuffle.scratchcardlayout.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;

public class ScratchCardLayout extends CardView implements ScratchCard.ScratchCardInterface {

    private ScratchCard scratchCard;
    private Context mContext;

    public ScratchCardLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScratchCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ScratchCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Set the scratch brush width
     *
     * @param mScratchWidthDip width in dp (Use Utility method {@code ScratchCardUtils.dipToPx} for conversion)
     */
    public void setScratchWidthDip(float mScratchWidthDip) {
        scratchCard.setScratchWidthDip(mScratchWidthDip);
    }

    /**
     * Set the layout resource id of image to show as overlay for scratching
     * ({@code Drawable} image or {@code ColorDrawable})
     *
     * @param mScratchDrawable layout resource id
     */
    public void setScratchDrawable(Drawable mScratchDrawable) {
        scratchCard.setScratchDrawable(mScratchDrawable);
    }

    /**
     * Scratch listener
     *
     * @param mListener listener object
     *                  (implement the interface in the type of the instance passed in which to listen to callbacks)
     */
    public void setScratchListener(ScratchListener mListener) {
        scratchCard.setListener(mListener);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        scratchCard = new ScratchCard(context, attrs, defStyleAttr);
        scratchCard.setRevealListener(this);
        setupScratchView();
    }

    /**
     * Adding the scratch card to layout
     */
    private void setupScratchView() {
        scratchCard.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        post(new Runnable() {
            @Override
            public void run() {
                addView(scratchCard);
            }
        });
    }

    /**
     * Stop or Interrupt scratch effect
     */
    public void stopScratching() {
        scratchCard.setVisibility(GONE);
    }

    /**
     * Reveal full layout when some threshold percent is scratched
     *
     * @param revealFullAtPercent threshold percent
     */
    public void setRevealFullAtPercent(int revealFullAtPercent) {
        scratchCard.setRevealFullAtPercent(revealFullAtPercent);
    }

    @Override
    public void onFullReveal() {
        stopScratching();
    }

    /**
     * Enable/Disable scratch effect
     * @param enableScratching true/false based on your choice
     */
    public void setScratchEnabled(boolean enableScratching) {
        scratchCard.setScratchEnabled(enableScratching);
    }

    /**
     * Reset scratch. (As if its a whole new scratch session
     */
    public void resetScratch() {
        scratchCard.resetScratch();
    }
}
