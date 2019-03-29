package in.codeshuffle.scratchcardlayoutexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;
import in.codeshuffle.scratchcardviewexample.R;

public class MainActivity extends AppCompatActivity implements ScratchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScratchCardLayout scratchCardLayout = findViewById(R.id.scratchCard);
        scratchCardLayout.setScratchDrawable(getResources().getDrawable(R.drawable.car));
        scratchCardLayout.setScratchListener(this);
        scratchCardLayout.setScratchWidth(30f);
    }

    @Override
    public void onScratchStarted() {
        Log.d("SCRATCH", "Scratch started");
    }

    @Override
    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int scratchProgress) {
        Log.d("SCRATCH", "Progress = " + scratchProgress);
        if (scratchProgress > 40) {
            Toast.makeText(this, "Better luck next time bro", Toast.LENGTH_SHORT).show();
            scratchCardLayout.stopScratching();
        }
    }

    @Override
    public void onScratchComplete() {
        Log.d("SCRATCH", "Scratch ended");
    }
}
