package com.example.wechatsample;

//通话记录
public class MyCall {
	private String name;
	
	private String number;
	
	private String callType;
	
	private String time;
	
	private String duration;    //通话时间
	
	private String position;       //归属地

	public MyCall(String name, String number, String callType, String time,
			String duration, String local) {
		super();
		this.name = name;
		this.number = number;
		this.callType = callType;
		this.time = time;
		this.duration = duration;
		this.position = local;
	}
	
	public MyCall(){
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	

	
}
