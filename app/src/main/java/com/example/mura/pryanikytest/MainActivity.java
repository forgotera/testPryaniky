package com.example.mura.pryanikytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.mura.pryanikytest.POJO.Variant;
import com.example.mura.pryanikytest.presenter.NetworkPresenter;
import com.example.mura.pryanikytest.view.GetView;

import java.util.List;


public class MainActivity extends MvpAppCompatActivity implements GetView {
    @InjectPresenter
    NetworkPresenter mNetworkPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //установка изображения
    @Override
    public void showImage(Bitmap image, String text) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(image);
        imageView.setContentDescription(text);
        ((ViewGroup) findViewById(R.id.activity_main)).addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),mNetworkPresenter.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    //установка текстовых ресурсов
    @Override
    public void showText(final String message) {
        //FIXME исчезает первая запись при повороте
        Log.d("answer",message);
        final TextView messageText = new TextView(this);
        messageText.setText(message);
        messageText.setGravity(Gravity.CENTER_HORIZONTAL);
        messageText.setTextSize(30);
        ((ViewGroup) findViewById(R.id.activity_main)).addView(messageText);
        messageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),mNetworkPresenter.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showRadioGroup(int selectedId, List<Variant> variants) {
        //создаем radioGroup
        RadioGroup group =  new RadioGroup(this);
        LinearLayout linearLayout = findViewById(R.id.activity_main);
        linearLayout.addView(group);

        //вывод radioButton
        for(int i = 0; i < variants.size();i++){
            RadioButton rb = new RadioButton(this);
            rb.setId(variants.get(i).getId());
            rb.setText(variants.get(i).getText());
            group.addView(rb);
            if(rb.getId() == selectedId){
                rb.toggle();
            }
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(),mNetworkPresenter.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showError(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

}
