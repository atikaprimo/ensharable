package com.keensolution.andriod.sample.multithread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MultiThreadSampleActivity extends Activity {
    /** Called when the activity is first created. */
	Button simpleMTButt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        simpleMTButt = (Button) findViewById(R.id.button1);
        
        simpleMTButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, SimpleMultiThreadActivity.class);
				startActivity(intent);
			}
		});
    }
}