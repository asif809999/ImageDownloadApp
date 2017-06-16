package com.example.android.imagedownloadapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button DownloadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_view);
        DownloadButton = (Button) findViewById(R.id.download_image_button);

        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadImageTask downloadImageTask = new DownloadImageTask(MainActivity.this);
                downloadImageTask.execute("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/2000px-Android_robot.svg.png");
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{

        ProgressDialog progressDialog;
        Context context;

        public DownloadImageTask(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Downloading image please wait");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String stringUrl = strings[0];
            Bitmap bitmap = null;


            try {
                URL url = new URL(stringUrl);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
