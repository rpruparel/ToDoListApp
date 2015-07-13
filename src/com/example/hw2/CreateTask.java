package com.example.hw2;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTask extends Activity {

	EditText task, date, time;
	RadioGroup radioButtonGroup;
	RadioButton buttonSelected;
	boolean allUpdated = true;
	Calendar calendar = Calendar.getInstance();
	DateFormat dateFormat = DateFormat.getDateInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.create_title);

		setContentView(R.layout.activity_create_task);

		if (getIntent().getExtras() != null) {
			setTitle(R.string.edit_title);
			if (getIntent().getExtras().containsKey(MainActivity.KEY_TASK)) {
				Task editTask = (Task) getIntent().getExtras().getSerializable(
				MainActivity.KEY_TASK);
				task = (EditText) findViewById(R.id.editText1);
				task.setText(editTask.getName());
				date = (EditText) findViewById(R.id.editText2);
				date.setText(editTask.getDate());
				time = (EditText) findViewById(R.id.editText3);
				time.setText(editTask.getTime());
				radioButtonGroup = (RadioGroup) findViewById(R.id.radioGroup1);
				buttonSelected = (RadioButton) radioButtonGroup.getChildAt(editTask.getPriorityInt());
				buttonSelected.setChecked(true);
			}
		} else {
			setTitle(R.string.create_title);
			date = (EditText) findViewById(R.id.editText2);
			time = (EditText) findViewById(R.id.editText3);
		}
		date.setKeyListener(null);
		date.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					DialogFragment newFragment = new DatePickerFragment();
					newFragment.setCancelable(false);
					newFragment.show(getFragmentManager(), "datePicker");
				}

			}
		});

		/*
		date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		*/

		time.setKeyListener(null);
		time.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					DialogFragment newFragment = new TimePickerFragment();
					newFragment.setCancelable(false);
					newFragment.show(getFragmentManager(), "timePicker");
				}

			}
		});

		/*
		time.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
				newFragment.show(getFragmentManager(), "timePicker");
			}
		});
		 */

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				allUpdated = true;
				// TODO Auto-generated method stub
				Log.d("createactivity", "on button click");
				task = (EditText) findViewById(R.id.editText1);
				date = (EditText) findViewById(R.id.editText2);
				time = (EditText) findViewById(R.id.editText3);

				if (task.getText().toString() == null || task.getText().toString().equals("") || date.getText().toString() == null || date.getText().toString().equals("") || time.getText().toString() == null || time.getText().toString().equals("")) {
					Toast.makeText(getBaseContext(),
						"Enter data for all the fields", Toast.LENGTH_SHORT)
						.show();
					allUpdated = false;
				}

				/**/

				if (allUpdated == true) {
					radioButtonGroup = (RadioGroup) findViewById(R.id.radioGroup1);
					buttonSelected = (RadioButton) findViewById(radioButtonGroup.getCheckedRadioButtonId());
					buttonSelected.getText();

					Task createTask = new Task(task.getText().toString(), date.getText().toString(), time.getText().toString(),
					buttonSelected.getText().toString());
					Log.d("hello1", createTask.toString());
					Intent intent = new Intent();
					intent.putExtra(MainActivity.KEY_TASK, createTask);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});

	}

	/*
	 * DatePickerDialog.OnDateSetListener d = new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * @Override public void onDateSet(DatePicker view, int year, int
	 * monthOfYear, int dayOfMonth) { // TODO Auto-generated method stub
	 * calendar.set(Calendar.YEAR, year); calendar.set(Calendar.MONTH,
	 * monthOfYear); calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	 * date.setText(dateFormat.format(calendar.getTime()));
	 * 
	 * } };
	 * 
	 * protected void setTime() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * protected void setDate() { // TODO Auto-generated method stub new
	 * DatePickerDialog(getBaseContext(), d, calendar.get(Calendar.YEAR),
	 * calendar.get(Calendar.MONTH),
	 * calendar.get(Calendar.DAY_OF_MONTH)).show();
	 * 
	 * }
	 */
	public class DatePickerFragment extends DialogFragment implements
	DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			if ((month + 1) < 10) {
				date.setText("0" + (month + 1));
			} else {
				date.setText("" + (month + 1));
			}
			date.append("/" + day + "/" + year);
		}
	}

	public class TimePickerFragment extends DialogFragment implements
	TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			false);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			Date date = new Date();
			date.setHours(hourOfDay);
			date.setMinutes(minute);
			String dt = sdf.format(date);
			time.setText(dt);
		}
	}
}