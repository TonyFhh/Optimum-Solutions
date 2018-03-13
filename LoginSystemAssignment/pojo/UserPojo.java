package com.optimum.pojo;

/** UserPojo features for DAO design pattern, incorporating Singleton DP as well.
 * */

public class UserPojo {
	private String user;
	private String password;
	private int failed = 0;
	
	private static UserPojo refUPojo; //we initialised this as a null global variable;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}
	
	
}
