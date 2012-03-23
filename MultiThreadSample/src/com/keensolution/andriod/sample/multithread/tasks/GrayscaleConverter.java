package com.keensolution.andriod.sample.multithread.tasks;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GrayscaleConverter extends AsyncTask<Bitmap, Integer, Bitmap> {
	private ProgressBar pb;
	private ImageView iv;
	private Button butt;
	
	public GrayscaleConverter(ProgressBar pb, ImageView iv, Button butt){
		this.pb = pb;
		this.iv = iv;
		this.butt = butt;
	}
	
	@Override
	protected Bitmap doInBackground(Bitmap... bitmaps) {

		if(bitmaps.length>0){
			Bitmap img = bitmaps[0];
			Bitmap copy = img.copy(img.getConfig(), true);
			int width = img.getWidth();
			int hight = img.getHeight();
			for(int i=0; i<width; i++){
				for(int j=0; j<hight; j++){
					int pixel = img.getPixel(i, j);
					int a = Color.alpha(pixel);
					int r = Color.red(pixel);
					int g = Color.green(pixel);
					int b = Color.blue(pixel);
					r=g=b=(int)(0.21 * r + 0.71 * g + 0.07 * b);
					copy.setPixel(i, j, Color.argb(a, r, g, b));
				}
				publishProgress((int) ((i / (float) width) * 100));
			}
			return copy;
		}
		return null;
	}
	
	protected void onProgressUpdate(Integer... progress) {
        pb.setProgress(progress[0]);
    }
	
	protected void onPostExecute(Bitmap result){
		iv.setImageBitmap(result);
		pb.setVisibility(View.GONE);
		butt.setEnabled(true);
		butt.setText("Reload original image");
	}
}
