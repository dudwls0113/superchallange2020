package com.softsquared.android.superchallange2020.src.main;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.softsquared.android.superchallange2020.R;


public class SeatCheckDialog extends Dialog {

    private TextView mTextViewOk;
    private TextView mTextViewCancel;

    private CustomLIstener mCustomLIstener;

    public SeatCheckDialog(Context context, CustomLIstener customLIstener) {
        super(context);
        setContentView(R.layout.custom_dialog_seat_check);
        this.mCustomLIstener = customLIstener;

        mTextViewOk = findViewById(R.id.dialog_must_update_ok);
        mTextViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomLIstener.yesClick();
                dismiss();
            }
        });

        mTextViewCancel = findViewById(R.id.dialog_must_update_cancel);
        mTextViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomLIstener.noClick();
                dismiss();

            }
        });

    }

    public interface CustomLIstener {
        void yesClick();

        void noClick();
    }
}
