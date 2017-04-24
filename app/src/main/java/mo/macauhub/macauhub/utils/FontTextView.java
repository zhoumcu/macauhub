package mo.macauhub.macauhub.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * author：Administrator on 2017/4/24 10:00
 * company: xxxx
 * email：1032324589@qq.com
 */

public class FontTextView extends TextView{
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(),"fonts/Hind-Medium.ttf"), style);
    }
}
