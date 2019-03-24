package com.example.mura.pryanikytest.view;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.mura.pryanikytest.POJO.Variant;

import java.util.List;

@StateStrategyType(AddToEndStrategy.class)
//что должна уметь вью?
public interface GetView extends MvpView {
    void showImage(Bitmap image, String text);
    void showText(String message);
    void showRadioGroup(int selectedId, List<Variant> variants);
    void showError(String text);
}
