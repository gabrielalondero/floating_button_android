package br.com.gabrielalondero.floating_button_android;


import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.Screen;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "floating_button";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        MethodChannel channel = new  MethodChannel(getFlutterEngine()
                .getDartExecutor().getBinaryMessenger(), CHANNEL);

        channel.setMethodCallHandler(
                (call, result)->{
                    //verificando qual método foi chamado
                    switch (call.method){
                        case "create":
                            ImageView imageView = new ImageView(getApplicationContext());
                            imageView.setImageResource(R.drawable.plus);
                            FloatWindow.with(getApplicationContext()).setView(imageView)
                                    .setWidth(Screen.width,0.15f)
                                    .setHeight(Screen.height,0.15f)
                                    .setX(Screen.width, 0.8f)
                                    .setY(Screen.height, 0.3f)
                                    .setDesktopShow(true) //para mostrar fora do app
                                    .build();
                            //enviar para o fluttter os cliques
                            imageView.setOnClickListener(
                                    v -> channel.invokeMethod("touch", null)
                            );

                            break;
                        case "show":
                            FloatWindow.get().show();
                            break;
                        case "hide":
                            FloatWindow.get().hide();
                            break;
                        case "isShowing":
                            //retorna se o botão está aparecendo ou não
                            result.success(FloatWindow.get().isShowing());
                            break;
                        default:
                            //caso não seja nenhum dos métodos acima
                            //mostra que não tem nada implementado
                            result.notImplemented();
                            break;
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        //destruir o botão ao fechar o app
        FloatWindow.destroy();
        super.onDestroy();
    }
}
