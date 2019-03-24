package com.example.mura.pryanikytest.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.mura.pryanikytest.POJO.Datum;
import com.example.mura.pryanikytest.POJO.Example;
import com.example.mura.pryanikytest.model.BlobApi;
import com.example.mura.pryanikytest.model.NetworkService;
import com.example.mura.pryanikytest.view.GetView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//благодаря аннотации создается класс ViewState
@InjectViewState
public class NetworkPresenter extends MvpPresenter<GetView> {
    private NetworkService retrofit;
    private List<String> viewList;
    private List<Datum> dateList;
    private static final String hz = "hz";
    private static final String picture = "picture";
    private static final String selector = "selector";
    private  Bitmap bMap2;

    public NetworkPresenter() {
        retrofit = new NetworkService();
        //используем реализацию интерфейса
        //и посылаем get запрос
        BlobApi blobApi = retrofit.getBlobApi();
        Call<Example> messages = blobApi.message();
        messages.enqueue(new Callback<Example>() {
            //при удачном ответе
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    //загружаем данные из json в листы
                    viewList = response.body().getView();
                    dateList = response.body().getData();
                    //вывод информации на экран
                    showData(viewList, dateList);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            //если коннект не удался
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                getViewState().showError("Ошибка подключения");
            }
        });
    }

    //вывод информации на mainActivity
    private void showData(List<String> viewList, List<Datum> dateList){
        //в каком порядке выкладывать элементы
        for(String buff : viewList){
            for(int i = 0; i < dateList.size();i++) {
                if (buff.equals(dateList.get(i).getName())){
                    //определяем тип данных и выводим их на экран
                    whatIsType(buff,i);
                }
            }
        }
    }

    //узнаем имя элемента
    private void whatIsType(String buff,int pos) {
        switch (buff) {
            case hz:
                getViewState().showText(dateList.get(pos).getData().getText());
                break;
            case picture:
                //загрузить картинку
                downloadImage(pos);
                String textImage = dateList.get(pos).getData().getText();
                //bMap2 декодированное изображение получаемое из фукнции imageDecod
                getViewState().showImage(bMap2,textImage);
                break;
            case selector:
                //работа с селектором
                getViewState().showRadioGroup(dateList.get(pos).getData().getSelectedId(),dateList.get(pos).getData().getVariants());
                break;
        }
    }

    //загрузка изображения
    private void downloadImage(int pos){
        BlobApi blobApi = retrofit.getBlobApi();
        //ретрофит позволяет динамически переопределить url поэтому передаем его в интерфейс
        Call<ResponseBody> image = blobApi.image(dateList.get(pos).getData().getUrl());
        image.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //выгружаем изображение и оптравляем его на декодирование
                try {
                    //Log.d("List", String.valueOf(response.body().byteStream()));
                      imageDecode(response.body());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getViewState().showError("Ошибка подключения");
            }
        });
    }

    private void imageDecode(ResponseBody body) throws IOException {

        ByteArrayOutputStream bytes = null;
        InputStream in = null;
        try {
            in = body.byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //путь и имя файла
            //FIXME PERMISSION DENIED
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "myBitmap.jpg");
            file.createNewFile();
            //запись
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        bMap2 = BitmapFactory.decodeByteArray(bytes.toByteArray(),0,bytes.size());

    }

}
