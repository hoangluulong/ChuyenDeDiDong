package java.android.quanlybanhang.Common;

import java.util.Locale;

public class FormatDouble {

    public FormatDouble() {
    }

    public String formatStr(double val) {
        return String.format(Locale.US, "%,.2f", val);
    }
}
