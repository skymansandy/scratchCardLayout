package in.codeshuffle.scratchcardlayoutexample.ui.fragment.demoscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;
import in.codeshuffle.scratchcardviewexample.R;

public class DemoFragment extends DialogFragment implements ScratchListener {

    private static final String TAG = DemoFragment.class.getSimpleName();
    @BindView(R.id.scratchCard)
    ScratchCardLayout scratchCardLayout;
    @BindView(R.id.scratchEffectToggle)
    SwitchCompat scratchEffectToggle;
    @BindView(R.id.brushSizeSeekBar)
    AppCompatSeekBar brushSizeSeekBar;
    @BindView(R.id.revealFullAtSeekBar)
    AppCompatSeekBar revealFullAtSeekBar;

    private Unbinder unbinder;

    public static DemoFragment getInstance() {
        Bundle bundle = new Bundle();
        DemoFragment demoFragment = new DemoFragment();
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetLibraryView();
        setControlPanelListeners();
    }

    @OnClick(R.id.reset)
    protected void resetLibraryView() {
        //Java reset
        scratchCardLayout.setScratchDrawable(getResources().getDrawable(R.drawable.scratch));
        scratchCardLayout.setScratchListener(this);
        scratchCardLayout.setScratchWidth(40);
        scratchCardLayout.setRevealFullAtPercent(40);
        scratchCardLayout.setScratchEnabled(true);
        scratchCardLayout.resetScratch();

        //Xml Reset
        scratchEffectToggle.setChecked(true);
        brushSizeSeekBar.setProgress(40);
        revealFullAtSeekBar.setProgress(40);
    }

    private void setControlPanelListeners() {
        //Scratch Brush size config
        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scratchCardLayout.setScratchWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Scratch effect config
        scratchEffectToggle.setOnCheckedChangeListener((buttonView, isChecked)
                -> scratchCardLayout.setScratchEnabled(isChecked));

        //Scratch reveal at percent
        revealFullAtSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scratchCardLayout.setRevealFullAtPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
