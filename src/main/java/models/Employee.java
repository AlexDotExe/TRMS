package models;

public class Employee {
double eid;
double sid;
String fname;
String lname;
String pass;
double remaining;
double pending;

public Employee() {
	super();
	// TODO Auto-generated constructor stub
}

public Employee(double eid, double sid, String fname, String lname, String pass, double remaining, double pending) {
	super();
	this.eid = eid;
	this.sid = sid;
	this.fname = fname;
	this.lname = lname;
	this.pass = pass;
	this.remaining = remaining;
	this.pending = pending;
}
public double getPending() {
	return pending;
}
public void setPending(double pending) {
	this.pending = pending;
}

public double getEid() {
	return eid;
}
public void setEid(double eid) {
	this.eid = eid;
}
public double getSid() {
	return sid;
}
public void setSid(double sid) {
	this.sid = sid;
}
public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public String getLname() {
	return lname;
}
public void setLname(String lname) {
	this.lname = lname;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public double getRemaining() {
	return remaining;
}
public void setRemaining(double remaining) {
	this.remaining = remaining;
}
@Override
public String toString() {
	return "Employee [eid=" + eid + ", sid=" + sid + ", fname=" + fname + ", lname=" + lname + ", pass=" + pass
			+ ", remaining=" + remaining + ", pending=" + pending + "]";
}



}