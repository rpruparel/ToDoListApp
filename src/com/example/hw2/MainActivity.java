package com.example.hw2;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final int REQ_CODE_NEW_TASK = 1001;
	public static final int REQ_CODE_DELETE_TASK = 1002;
	public static final int REQ_CODE_DISPLAY = 1003;
	public static final int REQ_CODE_EDIT_TASK = 1004;

	public static final String KEY_EDIT = "edit";
	public static final String KEY_DELETE = "delete";

	public static final String KEY_TASK = "task";
	public static final String KEY_OLD_TASK = "old task";
	public static final String KEY_TASK_LAYOUT = "task layout";

	public static final int NO_PADDING = 0;

	public static int viewID = 0;

	LinearLayout taskLayoutContainer;
	Button createButton;
	TextView taskText;
	LinkedList < Task > taskList = new LinkedList < Task > ();
	Intent displayIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// create layout container
		taskLayoutContainer = (LinearLayout) findViewById(R.id.TaskLayoutContainer);

		// update task header text
		taskText = (TextView) findViewById(R.id.main_text_view);
		taskText.setTextColor(Color.BLACK);
		taskText.setText(String.format("%d %s", taskList.size(), getResources()
			.getString(R.string.main_text_view)));

		// set button view
		ImageButton b = (ImageButton) findViewById(R.id.main_create_button);
		taskText.setTextColor(Color.BLACK);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, CreateTask.class);
				startActivityForResult(intent, REQ_CODE_NEW_TASK);
			}
		});

	}

	private void updateTaskHeader() {
		taskText.setText(String.format("%d Tasks", taskList.size()));
	}

	private void createNewTaskLayout(Task t) {
		// TODO Create new task layout
		LinearLayout newTaskLayout = new LinearLayout(getBaseContext());

		// set layout attributes
		newTaskLayout.setOrientation(LinearLayout.VERTICAL);

		// pack task into layout
		newTaskLayout.setTag(t);

		// make new textviews
		TextView taskName = new TextView(getBaseContext());
		TextView taskDate = new TextView(getBaseContext());
		TextView taskTime = new TextView(getBaseContext());

		// populate textviewfrom task
		taskName.setText(t.getName());
		taskName.setTextColor(Color.BLACK);
		taskName.setTypeface(Typeface.DEFAULT_BOLD);
		taskName.setTextSize(getResources().getDimension(
		(R.dimen.large_task_font_size)));

		// smaller font for these
		taskDate.setText(t.getDate());
		taskDate.setTextColor(Color.BLACK);
		taskDate.setTextSize(getResources().getDimensionPixelSize(
		R.dimen.small_task_font_size));

		taskTime.setText(t.getTime());
		taskTime.setTextColor(Color.BLACK);
		taskTime.setTextSize(getResources().getDimensionPixelSize(
		R.dimen.small_task_font_size));

		// add padding to separate tasks
		taskTime.setPadding(NO_PADDING, NO_PADDING, NO_PADDING, getResources()
			.getDimensionPixelSize(R.dimen.padding));

		// add textview to layout
		newTaskLayout.addView(taskName);
		newTaskLayout.addView(taskDate);
		newTaskLayout.addView(taskTime);

		// create listener to send to DISPLAY ACTIVITY
		newTaskLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("click", "go to display view");
				displayIntent = new Intent(MainActivity.this,
				DisplayActivity.class);
				displayIntent.putExtra(KEY_TASK, (Task) v.getTag()); // send
				// Task
				// that
				// was
				// packed
				// in v
				Log.d("task id in main", String.format("%d", v.getId()));
				startActivityForResult(displayIntent, REQ_CODE_DISPLAY);
			}
		});

		// add new layout to container
		taskLayoutContainer.addView(newTaskLayout);

		// add task to linked list
		taskList.add(t);

		// update header counter
		updateTaskHeader();

		Log.d("create method",
		String.format("taskList length: %d", taskList.size()));
		Log.d("create method",
		String.format("taskContainer length: %d",
		taskLayoutContainer.getChildCount()));

	}

	private void deleteTaskLayout(Task t) {
		Log.d("delete method", "start");
		int taskLayoutIndex = taskList.indexOf(t);

		// remove from container and task list
		taskLayoutContainer.removeViewAt(taskLayoutIndex);
		taskList.remove(t);

		// update count in task header
		updateTaskHeader();

	}

	private void editTaskLayout(Task oldTask, Task newTask) {
		Log.d("edit method", "start");
		Log.d("edit-old task", oldTask.toString());

		LinearLayout taskLayout = getTaskLayout(oldTask);
		taskLayout.setTag(newTask);

		// update task list for changed task
		int index = taskList.indexOf(oldTask);
		taskList.set(index, newTask);
		taskList.remove(oldTask);

		// set name, date, time
		((TextView) taskLayout.getChildAt(0)).setText(newTask.getName());
		((TextView) taskLayout.getChildAt(1)).setText(newTask.getDate());
		((TextView) taskLayout.getChildAt(2)).setText(newTask.getTime());
	}

	private LinearLayout getTaskLayout(Task t) {
		// get index of layout from linked list
		int taskLayoutIndex = taskList.indexOf(t);

		// get layout from task container
		LinearLayout taskContainer = (LinearLayout) findViewById(R.id.TaskLayoutContainer);

		// return linear layout at child index
		return (LinearLayout) taskContainer.getChildAt(taskLayoutIndex);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		/*
		 * String s = data.getExtras().getSerializable(KEY_TASK).toString();
		 * 
		 * Log.d("onActivityResult", ""); Log.d("requestCode",
		 * String.format("%d", requestCode)); Log.d("resultsCode",
		 * String.format("%d", resultCode)); Log.d("intent", String.format("%s",
		 * s));
		 */

		Log.d("requestCode", String.format("%d", requestCode));
		Log.d("resultCode", String.format("%d", resultCode));

		if (requestCode == REQ_CODE_NEW_TASK) {
			if (resultCode == RESULT_OK) {
				Log.d("result", "new task");
				Task t = (Task) data.getExtras().getSerializable(KEY_TASK);
				createNewTaskLayout(t);
			}
		} else if (requestCode == REQ_CODE_DISPLAY) {
			if (resultCode == RESULT_OK) {
				if (data.getExtras().containsKey(KEY_DELETE)) {
					Log.d("result", "delete");
					Task t = (Task) data.getExtras().getSerializable(
					KEY_OLD_TASK);
					deleteTaskLayout(t);
				} else if (data.getExtras().containsKey(KEY_EDIT)) {
					Log.d("result", "edit");
					Log.d("old not nulL?", String.format("%s", data.getExtras()
						.getSerializable(KEY_OLD_TASK) == null));
					Log.d("new not nulL?", String.format("%s", data.getExtras()
						.getSerializable(KEY_TASK) == null));

					// new task
					Task newTask = (Task) data.getExtras().getSerializable(
					KEY_TASK);

					// old task
					Task oldTask = (Task) data.getExtras().getSerializable(
					KEY_OLD_TASK);

					editTaskLayout(oldTask, newTask);
				}

			}
		}
	}
}