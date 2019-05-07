package in.codeshuffle.scratchcardlayout.listener;

import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;

/**
 * Interface to receive scratch related callbacks
 */
public interface ScratchListener {
    /**
     * Callback indicating Scratch started in the UI
     */
    void onScratchStarted();

    /**
     * Callback indicating progress percentage of scratch
     * @param scratchCardLayout the scratch card layout itself
     * @param atLeastScratchedPercent percentage that's at least scratched at this point
     *                                (not providing accurate values in real-time due to performance reasons)
     */
    void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent);

    /**
     * Callback indicating Scratch stopped/interrupted in the UI
     */
    void onScratchComplete();
}