package com.astr.hc.celebrations.font;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that take an instance of TextView/EditText/Button and applies a specified TTF font on it.
 * It assumes that TTF fonts are in the directory '/assets/'.
 */
public class OpenSansFontUtils {

    public static final String TAG = OpenSansFontUtils.class.getName();

    private final static FontFace _defaultFontface = FontFace.REGULAR;

    /* cache for loaded typefaces*/
    private static Map<FontFace, Typeface> typefaceCache;

    public OpenSansFontUtils(@NonNull Context context
    ) {

        String regularFontPath = "fonts/OpenSans-Regular.ttf";
        String italicFontPath = "fonts/OpenSans-Italic.ttf";
        String lightFontPath = "fonts/OpenSans-Light.ttf";
        String lightItalicFontPath = "fonts/OpenSans-LightItalic.ttf";
        String boldFontPath = "fonts/OpenSans-Bold.ttf";
        String boldItalicFontPath = "fonts/OpenSans-BoldItalic.ttf";
        String semiboldFontPath = "fonts/OpenSans-SemiBold.ttf";
        String semiboldItalicFontPath = "fonts/OpenSans-SemiBoldItalic.ttf";
        String extraboldFontPath = "fonts/OpenSans-ExtraBold.ttf";
        String extraboldItalicFontPath = "fonts/OpenSans-ExtraBoldItalic.ttf";

        typefaceCache = new HashMap<>();
        typefaceCache.put(FontFace.REGULAR, Typeface.createFromAsset(context.getAssets(), regularFontPath));
        if (italicFontPath != null) {
            typefaceCache.put(FontFace.ITALIC, Typeface.createFromAsset(context.getAssets(), italicFontPath));
        }
        if (lightFontPath != null) {
            typefaceCache.put(FontFace.LIGHT, Typeface.createFromAsset(context.getAssets(), lightFontPath));
        }
        if (lightItalicFontPath != null) {
            typefaceCache.put(FontFace.LIGHT_ITALIC, Typeface.createFromAsset(context.getAssets(), lightItalicFontPath));
        }
        if (boldFontPath != null) {
            typefaceCache.put(FontFace.BOLD, Typeface.createFromAsset(context.getAssets(), boldFontPath));
        }
        if (boldItalicFontPath != null) {
            typefaceCache.put(FontFace.BOLD_ITALIC, Typeface.createFromAsset(context.getAssets(), boldItalicFontPath));
        }

        if (semiboldFontPath != null) {
            typefaceCache.put(FontFace.SEMIBOLD, Typeface.createFromAsset(context.getAssets(), semiboldFontPath));
        }

        if (semiboldItalicFontPath != null) {
            typefaceCache.put(FontFace.SEMIBOLD_ITALIC, Typeface.createFromAsset(context.getAssets(), semiboldItalicFontPath));
        }

        if (extraboldFontPath != null) {
            typefaceCache.put(FontFace.EXTRABOLD, Typeface.createFromAsset(context.getAssets(), extraboldFontPath));
        }

        if (extraboldItalicFontPath != null) {
            typefaceCache.put(FontFace.EXTRABOLD_ITALIC, Typeface.createFromAsset(context.getAssets(), extraboldItalicFontPath));
        }
    }

    /**
     * Sets the default font to the given textview.
     *
     * @param textView the textview to which default font should be applied.
     * @return the result true or not
     */
    public boolean setTextView(TextView textView) {
        return setTextView(textView, _defaultFontface);
    }

    /**
     * Sets the given font to the given textview.
     *
     * @param textView the textview to which the font should be applied.
     * @param fontFace the font type.
     * @return the result true or not
     */
    public boolean setTextView(TextView textView, FontFace fontFace) {
        Typeface typeFace = getTypeface(fontFace);
        if (typeFace != null && textView != null) {
            textView.setTypeface(typeFace);
            return true;
        }
        return false;
    }

    /**
     * Sets the default font to the given edittext view.
     *
     * @param editText the edittext to which default font should be applied.
     * @return the result true or not
     */
    public boolean setEditTextView(EditText editText) {
        return setEditTextView(editText, _defaultFontface);
    }

    /**
     * Sets the given font to the given edittext view.
     *
     * @param editText the edittext to which the font should be applied.
     * @param fontFace the font type.
     * @return the result true or not
     */
    public boolean setEditTextView(EditText editText, FontFace fontFace) {
        Typeface typeFace = getTypeface(fontFace);
        if (typeFace != null && editText != null) {
            editText.setTypeface(typeFace);
            return true;
        }
        return false;
    }

    public boolean setTextInputView(TextInputLayout TextLyt, FontFace fontFace) {
        Typeface typeFace = getTypeface(fontFace);
        if (typeFace != null && TextLyt != null) {
            TextLyt.setTypeface(typeFace);
            return true;
        }
        return false;
    }

    /**
     * Sets the default font to the given button.
     *
     * @param button the button view to which default font should be applied.
     * @return the result true or not
     */
    public boolean setButton(Button button) {
        return setButton(button, _defaultFontface);
    }

    /**
     * Sets the given font to the given button view.
     *
     * @param button   the button to which the font should be applied.
     * @param fontFace the font type.
     * @return the result true or not
     */
    public boolean setButton(Button button, FontFace fontFace) {
        Typeface typeFace = getTypeface(fontFace);
        if (typeFace != null && button != null) {
            button.setTypeface(typeFace);
            return true;
        }
        return false;
    }

    /**
     * Gets the typeface based on the given font type.
     *
     * @param fontFace {@link FontFace}
     * @return the obtained typeface
     */
    public Typeface getTypeface(FontFace fontFace) {
        if (!typefaceCache.containsKey(fontFace)) {
            typefaceCache.get(_defaultFontface);
        }
        return typefaceCache.get(fontFace);
    }
}
