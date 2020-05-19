package in.codeshuffle.scratchcardlayoutexample.ui.fragment.demoscreen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;
import in.codeshuffle.scratchcardlayout.util.ScratchCardUtils;
import in.codeshuffle.scratchcardlayoutexample.util.AppUtils;
import in.codeshuffle.scratchcardviewexample.R;
import in.codeshuffle.scratchcardviewexample.databinding.FragmentDemoBinding;

public class DemoFragment extends DialogFragment implements ScratchListener {

    private static final String TAG = DemoFragment.class.getSimpleName();

    private Context context;

    private FragmentDemoBinding binding;

    public static DemoFragment getInstance() {
        Bundle bundle = new Bundle();
        DemoFragment demoFragment = new DemoFragment();
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDemoBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetLibraryView();
        setControlPanelListeners();
    }

    private void resetLibraryView() {
        binding.reset.setOnClickListener(v -> {
            //Java reset
            binding.scratchCard.setScratchDrawable(getResources().getDrawable(R.drawable.scratch));
            binding.scratchCard.setScratchListener(DemoFragment.this);
            binding.scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(context, 40));
            binding.scratchCard.setRevealFullAtPercent(40);
            binding.scratchCard.setScratchEnabled(true);
            binding.scratchCard.resetScratch();

            //Xml Reset
            binding.brushSizeSeekBar.setProgress(40);
            binding.revealFullAtSeekBar.setProgress(40);
            binding.scratchEffectToggle.setChecked(true);
            binding.scratchEffectToggle.setText(getString(R.string.enabled));
        });
        binding.reset.performClick();
    }

    private void setControlPanelListeners() {
        //Scratch Brush size config
        binding.brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                binding.scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(context, seekBar.getProgress()));
            }
        });

        //Scratch effect config
        binding.scratchEffectToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.scratchCard.setScratchEnabled(isChecked);
            binding.scratchEffectToggle.setText(getString(isChecked ? R.string.enabled : R.string.disabled));
        });

        //Scratch reveal at percent
        binding.revealFullAtSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AppUtils.showShortToast(getActivity(), getString(R.string.resetting_view_since_reveal_percent_was_changed));
                binding.scratchCard.setRevealFullAtPercent(seekBar.getProgress());
                binding.scratchCard.resetScratch();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onScratchStarted() {
        Log.d(TAG, "Scratch started");
    }

    @Override
    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
        Log.d(TAG, "Progress = " + atLeastScratchedPercent);
    }

    @Override
    public void onScratchComplete() {
        Log.d(TAG, "Scratch ended");
    }
}
