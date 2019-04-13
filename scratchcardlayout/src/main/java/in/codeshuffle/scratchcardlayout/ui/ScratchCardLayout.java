package in.codeshuffle.scratchcardlayout.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.util.Utils;

public class ScratchCardLayout extends CardView implements ScratchCard.ScratchCardInterface {

    private ScratchCard scratchCard;
    private Context mContext;
    private boolean revealWithAnimation;

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
     * @param mScratchWidth width in dp (will be converted to px automatically in library side)
     */
    public void setScratchWidth(float mScratchWidth) {
        scratchCard.setScratchWidth(Utils.dipToPx(mContext, mScratchWidth));
    }

    /**
     * Set the layout res of view to show for scratching (drawable image or a color drawable)
     *
     * @param mScratchDrawable layout res
     */
    public void setScratchDrawable(Drawable mScratchDrawable) {
        scratchCard.setScratchDrawable(mScratchDrawable);
    }

    /**
     * Scratch listener
     *
     * @param mListener listener object (implement the class in which to listen)
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
     * Stop scratch effect
     */
    public void stopScratching() {
        if (revealWithAnimation) {
            scratchCard.animate()
                    .translationY(scratchCard.getHeight())
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            scratchCard.setVisibility(View.GONE);
                            scratchCard.setAlpha(1.0f);
                        }
                    });
        } else {
            scratchCard.setVisibility(GONE);
        }
    }

    /**
     * Reveal full layout when a percent is scratched
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

    public void setScratchEnabled(boolean enableScratching) {
        scratchCard.setScratchEnabled(enableScratching);
    }

    /**
     * Reset scratch and show full scratch drawable
     */
    public void resetScratch() {
        scratchCard.resetScratch();
    }

    /**
     * Fully reveal the
     *
     * @param revealWithAnimation, boolean
     */
    public void setFullRevealWithFadeAnimation(boolean revealWithAnimation) {
        this.revealWithAnimation = revealWithAnimation;
    }
}
