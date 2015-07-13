package com.example.hw2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	TextView textView;
	Task task;
	Task oldTask;
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		setTitle(R.string.display_activity_title);

		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(MainActivity.KEY_TASK)) {
				task = (Task) getIntent().getExtras().getSerializable(
				MainActivity.KEY_TASK);
				setTextViews(task);
			}
		}

		imageView = (ImageView) findViewById(R.id.imageButton1);
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), CreateTask.class);
				task = getTextViews();
				// task = (Task)
				// getIntent().getExtras().getSerializable(MainActivity.KEY_TASK);
				intent.putExtra(MainActivity.KEY_TASK, task);
				startActivityForResult(intent, MainActivity.REQ_CODE_EDIT_TASK);
			}
		});

		imageView = (ImageView) findViewById(R.id.imageButton2);
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				oldTask = (Task) getIntent().getExtras().getSerializable(
				MainActivity.KEY_TASK);
				task = (Task) getTextViews();
				intent.putExtra(MainActivity.KEY_TASK, task);
				intent.putExtra(MainActivity.KEY_OLD_TASK, oldTask);
				intent.putExtra(MainActivity.KEY_DELETE,
				MainActivity.REQ_CODE_DELETE_TASK);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MainActivity.REQ_CODE_EDIT_TASK) {
			Log.d("hello", task.toString());
			if (resultCode == RESULT_OK && data.getExtras().containsKey(MainActivity.KEY_TASK)) {
				Log.d("hello", task.toString());
				Task task = (Task) data.getExtras().getSerializable(
				MainActivity.KEY_TASK);
				Log.d("hello", task.toString());
				setTextViews(task);
			}
		}
	}

	public void setTextViews(Task task) {
		Log.d("hello", task.toString());
		textView = (TextView) findViewById(R.id.TextView01);
		textView.setText(task.getName());
		textView = (TextView) findViewById(R.id.TextView03);
		textView.setText(task.getDate());
		textView = (TextView) findViewById(R.id.TextView05);
		textView.setText(task.getTime());
		textView = (TextView) findViewById(R.id.TextView07);
		textView.setText(task.getPriority());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();

		task = (Task) getTextViews();
		oldTask = (Task) getIntent().getExtras().getSerializable(
		MainActivity.KEY_TASK);
		intent.putExtra(MainActivity.KEY_OLD_TASK, oldTask);
		intent.putExtra(MainActivity.KEY_TASK, task);
		intent.putExtra(MainActivity.KEY_EDIT,
		MainActivity.REQ_CODE_DELETE_TASK);
		Log.d("display", String.format("%d", RESULT_OK));
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}

	public Task getTextViews() {
		textView = (TextView) findViewById(R.id.TextView01);
		task.setName(textView.getText().toString());

		textView = (TextView) findViewById(R.id.TextView03);
		task.setDate(textView.getText().toString());

		textView = (TextView) findViewById(R.id.TextView05);
		task.setTime(textView.getText().toString());

		textView = (TextView) findViewById(R.id.TextView07);
		task.setPriority(textView.getText().toString());

		return task;
	}
}