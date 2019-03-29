package in.codeshuffle.scratchcardview.listener;

import in.codeshuffle.scratchcardview.ui.ScratchCardLayout;

public interface ScratchListener {
    void onScratchStarted();

    void onScratchProgress(ScratchCardLayout scratchCardLayout, int scratchProgress);

    void onScratchComplete();
}