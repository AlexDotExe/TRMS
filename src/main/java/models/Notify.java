package models;

public class Notify {
double	rid;
double  eid;
String typ;
public Notify() {
	super();
	// TODO Auto-generated constructor stub
}
public Notify(double rid, double eid, String typ) {
	super();
	this.rid = rid;
	this.eid = eid;
	this.typ = typ;
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
public String getTyp() {
	return typ;
}
public void setTyp(String typ) {
	this.typ = typ;
}
@Override
public String toString() {
	return "Notify [rid=" + rid + ", eid=" + eid + ", typ=" + typ + "]";
}


}
