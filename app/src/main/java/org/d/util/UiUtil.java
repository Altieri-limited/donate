package org.d.util;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import javax.inject.Inject;

public class UiUtil {

    @Inject CompatUtil mCompatUtil;

    @Inject
    public UiUtil(CompatUtil compatUtil) {
        mCompatUtil = compatUtil;
    }

    /**
     * Show the soft keyboard
     * @param view The currently focused view, which would like to receive soft keyboard input.
     */
    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide the soft keyboard
     */
    public void dismissKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void htmlFromHtml(Context context, TextView textView, String html) {
        if (html != null) {
            textView.setText(mCompatUtil.htmlWithLinkFromHtml(context, html));
            textView.setVisibility(View.VISIBLE);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setClickable(true);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
