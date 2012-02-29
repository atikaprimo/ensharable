/**
 * Copyright [2012] [Marcus(Yuehu) Ou]

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

import android.os.Handler;
import android.os.Message;

public class SimpleTask implements Runnable {
	Handler handler;
	int i=0;
	volatile Thread task;
	volatile boolean threadSuspended=false;
	
	public SimpleTask(Handler handler){
		this.handler = handler;
	}
	
	/**
	 * Start the thread
	 */
	public synchronized void start(){
		if(task!=null){
			this.stopWithInterrupt();
		}
		this.threadSuspended=false;
		task = new Thread(this);//only initial one thread for this object
		task.start();
	}
	
	/**
	 * Stop the thread if the thread can bring back to runnable state
	 * , and the program will go to next loop.
	 * Note: this may not work, in some case. For example, the thread is 
	 * block in some reason (Locking, blocking queue...), the thread is waked up
	 * , but it sleep or blocked again, and never reach the next loop in the 
	 * while loop.
	 */
	public synchronized void stop(){
		task=null; //set the flag and while loop in the run() will exit
		i=0;
		notify(); //try to wake up the thread which associate this object
	}
	
	/**
	 * Stop the thread, even it is blocked by others(Blocking Queue)
	 */
	public synchronized void stopWithInterrupt(){
		Thread tempThread = task;
		i=0;
		task = null;
		if(tempThread != null){
			tempThread.interrupt();
		}
	}
	
	/**
	 * Suspend and resume the thread which associate with this object
	 */
	public synchronized void pauseAndResume(){
		threadSuspended = !threadSuspended;
        if (!threadSuspended){
            notify();
        }
	}
	
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		//the thread keep running until the task is set to "null"
		while(task == thisThread){
			try {
				i++;
				Message msg = handler.obtainMessage(1, new Integer(i));
				handler.sendMessage(msg);
                Thread.sleep(500);

                //This is required by the language, and ensures that wait and notify 
                //are properly serialized. In practical terms, this eliminates 
                //race conditions that could cause the "suspended" thread to 
                //miss a notify and remain suspended indefinitely.
                if (threadSuspended) {
	                synchronized(this) {
	                    while (threadSuspended && task==thisThread){
	                    	//put the thread in wait (suspend/pause the thread)
	                        wait();
	                    }
	                }
                }
            } catch (InterruptedException e){
            	//do something to clean up here
            	//for example release locks or others
            	//thread will exit
            }
		}
	}

}
