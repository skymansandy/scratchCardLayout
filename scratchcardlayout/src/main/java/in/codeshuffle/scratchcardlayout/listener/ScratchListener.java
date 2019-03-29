package in.codeshuffle.scratchcardlayout.listener;

import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;

public interface ScratchListener {
    void onScratchStarted();

    void onScratchProgress(ScratchCardLayout scratchCardLayout, int scratchProgress);

    void onScratchComplete();
}