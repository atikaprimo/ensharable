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

package com.keensolution.andriod.sample.multithread.threadpool;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import com.keensolution.andriod.sample.multithread.tasks.SimpleTask;
import com.keensolution.andriod.sample.multithread.tasks.TaskProgressMessage;
import com.keensolution.andriod.sample.multithread.tasks.TaskProgressView;

public class ThreadPoolActivity extends Activity {
	
	private Hashtable<Integer, TaskProgressView> taskViewTable = new Hashtable<Integer, TaskProgressView>();
	private LinearLayout mainLayout;
	private ScrollView scrollableView;
	private LinearLayout taskProgressViewContainer;
	private int idCount=0;
	private ExecutorService executor = Executors.newFixedThreadPool(5);
	
	private Handler handler = new Handler(){	
		@Override
		public void handleMessage(Message e){
			TaskProgressMessage msg = (TaskProgressMessage)e.obj;
			TaskProgressView tpv = taskViewTable.get(msg.getId());
			tpv.setProgress(msg.getProgress());
			if(msg.getProgress()==100){
				taskProgressViewContainer.removeView(tpv);
				tpv.setTaskWorker(null);
				taskViewTable.remove(new Integer(msg.getId()));
			}
		}
	};
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//create a linear layout, set the orientation and layout parameters
		mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		//create a button and insert into the layout
		Button startNewThreadButt = new Button(this);
		startNewThreadButt.setText("Start New Task");
		startNewThreadButt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mainLayout.addView(startNewThreadButt);
		
		scrollableView = new ScrollView(this);
		scrollableView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
		mainLayout.addView(scrollableView);
		
		taskProgressViewContainer = new LinearLayout(this);
		taskProgressViewContainer.setOrientation(LinearLayout.VERTICAL);
		taskProgressViewContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		scrollableView.addView(taskProgressViewContainer);
		
		//set the view content for the activity
		setContentView(mainLayout);
		
		startNewThreadButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TaskProgressView tpv = new TaskProgressView(taskProgressViewContainer.getContext());
				taskProgressViewContainer.addView(tpv);
				idCount++;
				SimpleTask t = new SimpleTask(handler, idCount, "Task "+idCount);
				tpv.setTaskName(t.getName());
				tpv.setTaskWorker(t);
				taskViewTable.put(new Integer(idCount), tpv);
				executor.execute(t);
			}
		});
	}
	
}
