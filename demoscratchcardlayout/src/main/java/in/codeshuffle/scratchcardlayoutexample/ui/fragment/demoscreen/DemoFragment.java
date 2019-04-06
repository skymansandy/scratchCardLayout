package in.codeshuffle.scratchcardlayoutexample.ui.fragment.demoscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;
import in.codeshuffle.scratchcardviewexample.R;

public class DemoFragment extends DialogFragment implements ScratchListener {

    Unbinder unbinder;

    @BindView(R.id.scratchCard)
    ScratchCardLayout scratchCardLayout;

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
        scratchCardLayout.setScratchDrawable(getResources().getDrawable(R.drawable.scratch));
        scratchCardLayout.setScratchListener(this);
        scratchCardLayout.setScratchWidth(40);
        scratchCardLayout.setRevealFullAtPercent(40);
        scratchCardLayout.setScratchEnabled(true);
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
        Log.d("SCRATCH", "Scratch started");
    }

    @Override
    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
        Log.d("SCRATCH", "Progress = " + atLeastScratchedPercent);
    }

    @Override
    public void onScratchComplete() {
        Log.d("SCRATCH", "Scratch ended");
    }
}
