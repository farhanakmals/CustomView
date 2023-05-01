package com.example.edittextwithclear;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditextWithClear extends AppCompatEditText {

    Drawable nClearButtonImage;

    public void init(){
        nClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (getCompoundDrawablesRelative()[2] != null) {

                    float clearButtonStartPosition = (getWidth() - getPaddingEnd() - nClearButtonImage.getIntrinsicWidth());
                    boolean isButtonClicked = false;

                    if (getLayoutDirection() != LAYOUT_DIRECTION_RTL) {
                        if (motionEvent.getX() > clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    } else {
                        if (motionEvent.getX() < clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    }

                    if (isButtonClicked) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            nClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24, null);
                            showClearButton();
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            nClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24,null);
                            getText().clear();
                            hideClearButton();
//                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public EditextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, nClearButtonImage, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
