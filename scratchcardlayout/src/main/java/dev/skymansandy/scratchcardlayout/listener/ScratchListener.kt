package dev.skymansandy.scratchcardlayout.listener

import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout

/**
 * Interface to receive scratch related callbacks
 */
interface ScratchListener {
    /**
     * Callback indicating Scratch started in the UI
     */
    fun onScratchStarted()

    /**
     * Callback indicating progress percentage of scratch
     * @param scratchCardLayout the scratch card layout itself
     * @param atLeastScratchedPercent percentage that's at least scratched at this point
     * (not providing accurate values in real-time due to performance reasons)
     */
    fun onScratchProgress(scratchCardLayout: ScratchCardLayout, atLeastScratchedPercent: Int)

    /**
     * Callback indicating Scratch stopped/interrupted in the UI
     */
    fun onScratchComplete()
}