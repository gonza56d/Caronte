package com.caronte;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

public class Utils {

    public static boolean hasData(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.length() < 1;
    }

    public static boolean hasData(String... string) {
        for (String s : string) {
            if (s == null || s.length() < 1) {
                return false;
            }
        }
        return true;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(
                Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

}
