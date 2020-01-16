package com.softsquared.android.superchallange2020.src.seat_choice;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.softsquared.android.superchallange2020.R;


public class ChoiceDialog extends Dialog {

    private TextView mTextViewOk;
    private TextView mTextViewCancel;

    private CustomLIstener mCustomLIstener;

    public ChoiceDialog(Context context, CustomLIstener customLIstener) {
        super(context);
        setContentView(R.layout.custom_dialog_seat_choice);
        this.mCustomLIstener = customLIstener;

        mTextViewOk = findViewById(R.id.dialog_must_update_ok);
        mTextViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomLIstener.goUpdateClick();
                dismiss();
            }
        });

        mTextViewCancel = findViewById(R.id.dialog_must_update_cancel);
        mTextViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomLIstener.cancelClick();
                dismiss();

            }
        });

    }

    public interface CustomLIstener {
        void goUpdateClick();

        void cancelClick();
    }
}
