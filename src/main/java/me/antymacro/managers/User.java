package me.antymacro.managers;

public class User {
	private final String 
	name;
	private int cps;
	private boolean alert;

	public User(String name) {
		this.name = name;
		this.cps = 0;
		UserManager.users.add(this);
	}

	public String getName() {
		return name;
	}

	public int getCps() {
		return cps;
	}

	public void setCps(int newcps) {
		this.cps = newcps;
	}

	public boolean getAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}
}
