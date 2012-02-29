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

package com.keensolution.andriod.sample.multithread;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SimpleMultiThreadActivity extends Activity {
	
	private TextView resultText;
	private ProgressBar progressBar;
	
	Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message e){
			Integer returnValue = (Integer)e.obj;
			resultText.setText("Thread return value "+returnValue.toString());
			progressBar.setProgress(returnValue.intValue() % 100);
		}
	};
	SimpleTask backgroundThread = new SimpleTask(handler);
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//create a linear layout, set the orientation and layout parameters
		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);
		l.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		//create a button and insert into the layout
		Button startNewThreadButt = new Button(this);
		startNewThreadButt.setText("Start New Thread");
		startNewThreadButt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		l.addView(startNewThreadButt);
		
		//create a button and insert into the layout
		Button stopThreadButt = new Button(this);
		stopThreadButt.setText("Stop Thread");
		stopThreadButt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		l.addView(stopThreadButt);
		
		//create a button and insert into the layout
		Button pauseThreadButt = new Button(this);
		pauseThreadButt.setText("Pause/Resume Thread");
		pauseThreadButt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		l.addView(pauseThreadButt);
		
		progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setMax(100);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		l.addView(progressBar);
		
		resultText = new TextView(this);
		resultText.setText("Empty");
		resultText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		l.addView(resultText);
		
		//set the view content for the activity
		setContentView(l);
		
		
		startNewThreadButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backgroundThread.start();
			}
		});
		
		stopThreadButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backgroundThread.stopWithInterrupt();
			}
		});
		
		pauseThreadButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backgroundThread.pauseAndResume();
			}
		});
	}
	
	
	
}
