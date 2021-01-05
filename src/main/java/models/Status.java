package models;

public class Status {
double rid;
double eid;
double ss;
double hs;
double bs;
String restatus;
double rereq;
double regranted;
String bcnotes;

public Status() {
	super();
	// TODO Auto-generated constructor stub
}
public Status(double rid, double eid, double ss, double hs, double bs, String restatus, double rereq, double regranted,
		String bcnotes) {
	super();
	this.rid = rid;
	this.eid = eid;
	this.ss = ss;
	this.hs = hs;
	this.bs = bs;
	this.restatus = restatus;
	this.rereq = rereq;
	this.regranted = regranted;
	this.bcnotes = bcnotes;
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
public double getSs() {
	return ss;
}
public void setSs(double ss) {
	this.ss = ss;
}
public double getHs() {
	return hs;
}
public void setHs(double hs) {
	this.hs = hs;
}
public double getBs() {
	return bs;
}
public void setBs(double bs) {
	this.bs = bs;
}
public String getRestatus() {
	return restatus;
}
public void setRestatus(String restatus) {
	this.restatus = restatus;
}
public double getRereq() {
	return rereq;
}
public void setRereq(double rereq) {
	this.rereq = rereq;
}
public double getRegranted() {
	return regranted;
}
public void setRegranted(double regranted) {
	this.regranted = regranted;
}
public String getBcnotes() {
	return bcnotes;
}
public void setBcnotes(String bcnotes) {
	this.bcnotes = bcnotes;
}
@Override
public String toString() {
	return "Status [rid=" + rid + ", eid=" + eid + ", ss=" + ss + ", hs=" + hs + ", bs=" + bs + ", restatus=" + restatus
			+ ", rereq=" + rereq + ", regranted=" + regranted + ", bcnotes=" + bcnotes + "]";
}
 
}
