package models;

public class Request {
double rid;
double eid;
double costs;
double preim;
String dates;
String eventstart;
String mingrade;
String locale;
String info;
String just;
String ex1;
String ex2;
String ex3;
String fg;
String et;


public Request() {
	super();
	// TODO Auto-generated constructor stub
}



public String getFg() {
	return fg;
}



public void setFg(String fg) {
	this.fg = fg;
}



public Request(double rid, double eid, double costs, double preim, String dates, String eventstart, String mingrade,
		String locale, String info, String just, String ex1, String ex2, String ex3, String fg, String et) {
	super();
	this.rid = rid;
	this.eid = eid;
	this.costs = costs;
	this.preim = preim;
	this.dates = dates;
	this.eventstart = eventstart;
	this.mingrade = mingrade;
	this.locale = locale;
	this.info = info;
	this.just = just;
	this.ex1 = ex1;
	this.ex2 = ex2;
	this.ex3 = ex3;
	this.fg = fg;
	this.et = et;
}



public String getEx1() {
	return ex1;
}

public void setEx1(String ex1) {
	this.ex1 = ex1;
}

public String getEx2() {
	return ex2;
}

public void setEx2(String ex2) {
	this.ex2 = ex2;
}

public String getEx3() {
	return ex3;
}

public void setEx3(String ex3) {
	this.ex3 = ex3;
}

public String getEt() {
	return et;
}

public void setEt(String et) {
	this.et = et;
}

public double getRid() {
	return rid;
}

public void setRid(double rid) {
	this.rid = rid;
}

public double getEid() {
	return eid;
}

public void setEid(double eid) {
	this.eid = eid;
}

public double getCosts() {
	return costs;
}

public void setCosts(double costs) {
	this.costs = costs;
}

public double getPreim() {
	return preim;
}

public void setPreim(double preim) {
	this.preim = preim;
}

public String getDates() {
	return dates;
}

public void setDates(String dates) {
	this.dates = dates;
}

public String getEventstart() {
	return eventstart;
}

public void setEventstart(String eventstart) {
	this.eventstart = eventstart;
}

public String getMingrade() {
	return mingrade;
}

public void setMingrade(String mingrade) {
	this.mingrade = mingrade;
}

public String getLocale() {
	return locale;
}

public void setLocale(String locale) {
	this.locale = locale;
}

public String getInfo() {
	return info;
}

public void setInfo(String info) {
	this.info = info;
}

public String getJust() {
	return just;
}

public void setJust(String just) {
	this.just = just;
}


@Override
public String toString() {
	return "Request [rid=" + rid + ", eid=" + eid + ", costs=" + costs + ", preim=" + preim + ", dates=" + dates
			+ ", eventstart=" + eventstart + ", mingrade=" + mingrade + ", locale=" + locale + ", info=" + info
			+ ", just=" + just + ", ex1=" + ex1 + ", ex2=" + ex2 + ", ex3=" + ex3 + ", fg=" + fg + ", et=" + et + "]";
}




}
