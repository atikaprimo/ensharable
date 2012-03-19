/**
 * Copyright [2012] [Marcus Ou]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.keensolution.andriod.sample.multithread.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.keensolution.andriod.sample.multithread.R;
import com.keensolution.andriod.sample.multithread.tasks.ImageDownloadCallable;

/**
 * This sample is about how to use FutureTask.
 * First, you need to create a callable
 * Second, create thread to process the callable object
 * Third, call get() method to get your result back, remember don't use the main/UI thread
 * to call get(), because the method will block your thread. 
 * 
 * @author Marcus
 *
 */
public class FutureTaskActivity extends Activity {
	/** Called when the activity is first created. */
	Button downLoadButt;
	private String url = "http://ensharable.files.wordpress.com/2012/03/2010-11-14-11-34-22.jpg";
	private Bitmap imageBitmap;
	private ImageView iv;
	private Handler handler;
	private ProgressBar pb;

	/**
	 * Use future task to perform HTTP get request to download image
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.futuretasklayout);
		downLoadButt = (Button) findViewById(R.id.downLoadImageButt);
		iv = (ImageView) findViewById(R.id.imageView1);
		pb = (ProgressBar) findViewById(R.id.downloadImageProgressBar);
		pb.setVisibility(View.GONE);
		handler = new Handler();

		downLoadButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					public void run() {
						ImageDownloadCallable idownload = new ImageDownloadCallable(
								url);
						FutureTask<Bitmap> task = new FutureTask<Bitmap>(
								idownload);
						Thread t = new Thread(task);
						t.start();
						try {
							//this will block your current thread, and wait until the method
							//return result or throws exception
							imageBitmap = task.get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
						handler.post(new Runnable() {
							@Override
							public void run() {
								iv.setImageBitmap(imageBitmap);
								pb.setVisibility(View.GONE);
							}
						});
					}
				}).start();

			}
		});
	}
}