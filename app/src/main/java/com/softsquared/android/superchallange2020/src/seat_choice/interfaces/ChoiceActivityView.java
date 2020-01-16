package com.softsquared.android.superchallange2020.src.seat_choice.interfaces;

import com.softsquared.android.superchallange2020.src.seat_choice.model.Result;

import java.util.List;

public interface ChoiceActivityView {

    void getSeatSuccess(List<Result> results);

    void getSeatFailure(String message);
}
