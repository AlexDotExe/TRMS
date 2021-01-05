package services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

import dao.EmployeeDAO;
import dao.NotifyDAO;
import dao.RequestDAO;
import dao.StatusDAO;
import models.Employee;
import models.Notify;
import models.Request;

@SuppressWarnings("unused")
public class TService {
	NotifyDAO nt = new NotifyDAO();
	RequestDAO rq = new RequestDAO();
	StatusDAO st = new StatusDAO();
	EmployeeDAO em = new EmployeeDAO();
	public Stack<Employee> estack = new Stack<>();
	public Stack<Notify> nstack = new Stack<>();
	public Stack<Integer> numstack = new Stack<>();	

	
	
	public Employee getE(Double eid, String pass){
		Employee e = em.getE(eid, pass);
		return e;
	}
	public Employee easy(Double eid){
		Employee e = em.easy(eid);
		return e;
	}
	public boolean makeRequest(double eid, double cost, double preim, String locale, String eventtype, String mingrade, String info, String just, String eventstart) {
		//LocalDateTime t = LocalDateTime.now();
		String date = "8-14-2020";
		Request r = new Request(1.0,eid,cost,preim,date, eventstart,mingrade,locale, info, just,"0","0","0","n/a",eventtype);
		if(rq.newRequest(r))
				return true;
		return false;
	}
	public boolean sApprove(double rid, double eid) {
	//	Employee e = easy(eid);
		if(eid % 1000 == 0 ||  eid == 9999)
			return st.hApprove(rid,eid);
		else return st.sApprove(rid,eid);
	}
	public boolean hApprove(double rid, double eid) {
		return st.hApprove(rid,eid);
			}
	public boolean bApprove(double rid, double eid) {
		return st.bApprove(rid,eid);
			}
	public void smoreInfo(double rid, String note) throws SQLException {
		nt.smoreInfo(rid, note);
	}
	public boolean bConfirm(double rid) {
		return st.bConfirm(rid);
			}
	public boolean uploadGrade(double rid, String grade) {
		return rq.uploadGrade(rid , grade);
			}
	public boolean deny(double rid) {
		return st.deny(rid);
			}
	
	public List<Notify> notifynum(double eid) {
		return nt.notifList(eid);
	}

	
}
