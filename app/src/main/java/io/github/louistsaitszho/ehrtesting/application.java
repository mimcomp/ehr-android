package io.github.louistsaitszho.ehrtesting;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

/**
 * Created by Louis on 1/9/15.
 */
public class application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "LavYuwYH6JGN7MdH26XC87atMcAGgmEeSjcBdiy5", "1C9v0QxgVdwvTwOH3DlC34P9TFcLzzOiYYmM6Ix2");
    }
}
