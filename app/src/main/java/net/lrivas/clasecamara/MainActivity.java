package net.lrivas.clasecamara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button gallery;
    Button whatsappButton;
    private static final int REQUEST_CODIGO_CAMARA=200;
    Uri selectedImage;

    @SuppressLint({"WrongViewCast", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_CODIGO_CAMARA);
            }
        }

        whatsappButton = findViewById(R.id.compartirWhatsapp);
        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentW = new Intent(Intent.ACTION_SEND);
                intentW.setType("image/");
                intentW.setPackage("com.whatsapp");
                if (selectedImage != null){
                    intentW.putExtra(Intent.EXTRA_STREAM, selectedImage);
                    try {
                        startActivity(intentW);
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, "Error al Enviar\n"+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No se Selecciono una Imagen", Toast.LENGTH_LONG).show();
                }
            }
        });

        gallery = findViewById(R.id.Gallery);
        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode  == RESULT_OK && data != null){
             selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImage);
        }
    }
}