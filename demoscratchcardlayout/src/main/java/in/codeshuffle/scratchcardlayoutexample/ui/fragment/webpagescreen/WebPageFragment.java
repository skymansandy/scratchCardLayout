package in.codeshuffle.scratchcardlayoutexample.ui.fragment.webpagescreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.codeshuffle.scratchcardlayoutexample.util.AppConstants;
import in.codeshuffle.scratchcardviewexample.R;

public class WebPageFragment extends DialogFragment {

    private static final String CONTENT_TYPE = "contentType";

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (!isAdded()) return;
            progressBar.setProgress(newProgress);
        }
    };
    private WebViewClient mWebClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!isAdded()) return;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!isAdded()) return;
            progressBar.setVisibility(View.GONE);
        }
    };


    public static WebPageFragment getInstance(WebPageContent content) {
        Bundle bundle = new Bundle();
        WebPageFragment webPageFragment = new WebPageFragment();
        bundle.putSerializable(CONTENT_TYPE, content);
        webPageFragment.setArguments(bundle);
        return webPageFragment;
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
        View view = inflater.inflate(R.layout.fragment_webpage, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setupWebViewDefaults();
            Bundle bundle = getArguments();
            if (bundle != null) {
                WebPageContent content = (WebPageContent) bundle.getSerializable(CONTENT_TYPE);
                content = (content == null) ? WebPageContent.PAGE_VIEW_IN_GITHUB : content;
                switch (content) {
                    case PAGE_VIEW_ABOUT_LIBRARY:
                        webView.loadUrl(AppConstants.URLs.VIEW_READ_ME);
                        break;
                    case PAGE_VIEW_IN_GITHUB:
                        webView.loadUrl(AppConstants.URLs.GITHUB_REPO_URL);
                        break;
                    case PAGE_ISSUE_AND_FEEDBACK:
                        webView.loadUrl(AppConstants.URLs.ISSUE_FEEDBACK_URL);
                        break;
                    case PAGE_DONATE_BEER:
                        webView.loadUrl(AppConstants.URLs.DONATE_BEER_URL);
                        break;
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebViewDefaults() {
        webView.setWebChromeClient(mChromeClient);
        webView.setWebViewClient(mWebClient);
        //Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
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
}
