package com.example.hw2;

import java.io.Serializable;

public class Task implements Serializable {
	private String name;
	private String date;
	private String time;
	private String priority;

	public Task(String name, String date, String time, String priority) {
		super();
		this.name = name;
		this.date = date;
		this.time = time;
		this.priority = priority;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", date=" + date + ", time=" + time + ", priority=" + priority + "]";
	}

	@Override
	public boolean equals(Object o) {
		Task t = (Task) o;
		// TODO Auto-generated method stub
		if (this.date.equals(t.getDate()) && this.name.equals(t.getName()) && this.priority.equals(t.getPriority()) && this.time.equals(t.getTime())) {
			return true;
		} else {
			return false;
		}
	}

	public int getPriorityInt() {
		if (priority.equals("High")) {
			return 0;
		} else if (priority.equals("Medium")) {
			return 1;
		} else if (priority.equals("Low")) {
			return 2;
		} else return 0;
	}
}