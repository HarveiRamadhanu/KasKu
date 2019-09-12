package org.d3ifcool.kasku.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bitmapImage;

    public DownloadImageTask(ImageView imageView){
        this.bitmapImage=imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay =urls[0];
        Bitmap mImage1=null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mImage1 = BitmapFactory.decodeStream(in);
        }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mImage1;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            bitmapImage.setImageBitmap(result);

        }
    }
}
