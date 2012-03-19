package com.keensolution.andriod.sample.multithread.tasks;

import java.io.InputStream;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class ImageDownloadCallable implements Callable<Bitmap>{
	private String url;
	final int IO_BUFFER_SIZE = 4 * 1024;
	final AndroidHttpClient client = AndroidHttpClient.newInstance("MultiThreadSample");
	
	public ImageDownloadCallable(String url){
		this.url = url;
	}
	
	
	@Override
	public Bitmap call() throws Exception {
		HttpGet getReq = new HttpGet(url);
		try{
			HttpResponse response = client.execute(getReq);
			final int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK){
				 Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
		         return null;
			}
			final HttpEntity entry = response.getEntity();
			if(entry!=null){
				InputStream is = null;
				try{
					is = entry.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(is);
					return bitmap;
				}finally {
					if(is!=null){
						is.close();
					}
					entry.consumeContent();
				}
			}
		}catch (Exception e) {
	        // Could provide a more explicit error message for IOException or IllegalStateException
	        getReq.abort();
	        Log.w("ImageDownloader", "Error while retrieving bitmap from " + url + e.toString());
	    } finally {
	        if (client != null) {
	            client.close();
	        }
	    }
		return null;
	}
	

}
