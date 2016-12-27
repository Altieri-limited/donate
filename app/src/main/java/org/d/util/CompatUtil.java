package org.d.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

public class CompatUtil {
    @Inject Context mContext;

    @Inject
    public CompatUtil(Context context) {
        mContext = context;
    }

    public void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public void setStatusBarColor(Activity activity, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int color = ContextCompat.getColor(activity, colorRes);
            window.setStatusBarColor(color);
        }
    }

    Spanned htmlWithLinkFromHtml(Context context, String htmlText) {
        if (htmlText == null) return null;
        Spanned html;
        html = htmlWithLinkFromHtml(htmlText);
        SpannableString result = new SpannableString(html);
        Object[] spans = html.getSpans(0, html.length(), URLSpan.class);
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < spans.length; i++) {
            URLSpan span = (URLSpan) spans[i];
            int start = html.getSpanStart(span);
            int end = html.getSpanEnd(span);
            String url = span.getURL();
            result.removeSpan(span);
            result.setSpan(new URLSpan(url) {
                @Override
                public void onClick(View widget) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getURL()));
                    context.startActivity(i);
                }
            }, start, end, 0);
        }
        return result;
    }

    private Spanned htmlWithLinkFromHtml(String htmlText) {
        Spanned html;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            html = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            html = Html.fromHtml(htmlText);
        }
        return html;
    }

    public int getColor(@ColorRes int color) {
        return ContextCompat.getColor(mContext, color);
    }
}