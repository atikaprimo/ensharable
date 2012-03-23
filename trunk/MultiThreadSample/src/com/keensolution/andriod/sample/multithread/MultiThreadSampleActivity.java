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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.keensolution.andriod.sample.multithread.asynctask.AsyncTaskActivity;
import com.keensolution.andriod.sample.multithread.futuretask.FutureTaskActivity;
import com.keensolution.andriod.sample.multithread.threadpool.ThreadPoolActivity;

public class MultiThreadSampleActivity extends Activity {
    /** Called when the activity is first created. */
	Button simpleMTButt;
	Button threadPoolButt;
	Button futureTaskButt;
	Button asyncTaskButt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        simpleMTButt = (Button) findViewById(R.id.button1);
        threadPoolButt = (Button) findViewById(R.id.button2);
        futureTaskButt = (Button) findViewById(R.id.button3);
        asyncTaskButt = (Button) findViewById(R.id.button4);
        
        
        simpleMTButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, SimpleMultiThreadActivity.class);
				startActivity(intent);
			}
		});
        
        threadPoolButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, ThreadPoolActivity.class);
				startActivity(intent);
			}
		});
        
        futureTaskButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, FutureTaskActivity.class);
				startActivity(intent);
			}
		});
        
        asyncTaskButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, AsyncTaskActivity.class);
				startActivity(intent);
			}
		});
    }
}