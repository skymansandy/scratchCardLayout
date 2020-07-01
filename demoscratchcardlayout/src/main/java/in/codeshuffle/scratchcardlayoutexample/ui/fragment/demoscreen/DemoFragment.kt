package `in`.codeshuffle.scratchcardlayoutexample.ui.fragment.demoscreen

import `in`.codeshuffle.scratchcardlayout.listener.ScratchListener
import `in`.codeshuffle.scratchcardlayout.ui.ScratchCardLayout
import `in`.codeshuffle.scratchcardlayout.util.ScratchCardUtils
import `in`.codeshuffle.scratchcardlayoutexample.util.AppUtils
import `in`.codeshuffle.scratchcardviewexample.R
import `in`.codeshuffle.scratchcardviewexample.databinding.FragmentDemoBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_demo.view.*

class DemoFragment : DialogFragment(), ScratchListener {

    companion object {
        private val TAG: String? = DemoFragment::class.java.simpleName

        fun getInstance(): DemoFragment {
            val bundle = Bundle()
            val demoFragment = DemoFragment()
            demoFragment.arguments = bundle
            return demoFragment
        }
    }

    private lateinit var binding: FragmentDemoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDemoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetLibraryView()
        setControlPanelListeners()
    }

    private fun resetLibraryView() {
        binding.scratchCard.setScratchListener(this)

        binding.reveal.setOnClickListener {
            binding.scratchCard.revealScratch()
        }

        binding.reset.setOnClickListener {
            //Props reset
            binding.scratchCard.resetScratch()

            //Xml Reset
            binding.brushSizeSeekBar.progress = 40
            binding.revealFullAtSeekBar.progress = 40
            binding.scratchEffectToggle.isChecked = true
            binding.scratchEffectToggle.text = getString(R.string.enabled)
        }
    }

    private fun setControlPanelListeners() {
        //Scratch Brush size config
        binding.brushSizeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                context?.let {
                    binding.scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(it, seekBar!!.progress.toFloat()))
                }
            }
        })

        //Scratch effect config
        binding.scratchEffectToggle.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            binding.scratchCard.setScratchEnabled(isChecked)
            binding.scratchEffectToggle.text = getString(if (isChecked) R.string.enabled else R.string.disabled)
        }

        //Scratch reveal at percent
        binding.revealFullAtSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                AppUtils.showShortToast(activity, getString(R.string.resetting_view_since_reveal_percent_was_changed))
                binding.scratchCard.setRevealFullAtPercent(seekBar!!.progress)
                binding.scratchCard.resetScratch()
            }
        })
    }

    override fun onScratchStarted() {
        Log.d(TAG, "Scratch started")
    }

    override fun onScratchProgress(scratchCardLayout: ScratchCardLayout, atLeastScratchedPercent: Int) {
        Log.d(TAG, "Progress = $atLeastScratchedPercent")
    }

    override fun onScratchComplete() {
        Log.d(TAG, "Scratch ended")
    }
}