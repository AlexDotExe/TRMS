package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import models.Employee;
import models.IntJson;
import models.Notify;
import models.Request;
import services.TService;

@SuppressWarnings("unused")
public class DBController {
public static TService service = new TService();
public static Gson gson = new Gson();


public static void addE(HttpServletRequest req, HttpServletResponse res) throws JsonSyntaxException, JsonIOException, IOException {
//POST REQUEST data from request body
	
//gson will convert a json to an object
	Employee e = gson.fromJson(req.getReader(), Employee.class);
	res.getWriter().append(gson.toJson(e));
}
public static void getE(HttpServletRequest req, HttpServletResponse res) throws IOException {
	//http:localhost:8080/proj1/getE.do?id=1
	
	double eid =Double.parseDouble(req.getParameter("eid"));
	String pw = req.getParameter("pass"	);	
	Employee e = service.getE(eid,pw);
	if(e != null) {
		service.estack.push(e);
		res.getWriter().append(gson.toJson(e));
	}
	else res.sendError(404, "Bad Input");
	
}
// create or replace procedure newRequest(s_id number,eid number,co number,preim number, dat varchar2, es varchar2,mg varchar2,
// loc varchar2, inf varchar2, ju varchar2,fg varchar2,et varchar2)

//+eid+"&locale="+locale+"&eventstart="+eventstart+
//"&eventtype="+eventtype+"&mingrade="+ mingrade +"&info="+info+"&cost="+cost+"&preim="+preim+"&just="+just;
public static void makeRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
	double eid =Double.parseDouble(req.getParameter("eid"));
	double cost =Double.parseDouble(req.getParameter("cost"));
	String locale =req.getParameter("locale");
	String eventstart =req.getParameter("eventstart");
	String eventtype =req.getParameter("eventtype");
	String mingrade =req.getParameter("mingrade");
	String info =req.getParameter("info");
	double preim =Double.parseDouble(req.getParameter("preim"));
	String just = req.getParameter("just");
	if(service.makeRequest(eid, cost, preim, locale, eventtype, mingrade, info, just, eventstart))
		res.setStatus(200);
	else res.sendError(404);
	
}
public static void notifnum(HttpServletRequest req, HttpServletResponse res) {
	double eid = Double.parseDouble(req.getParameter("eid"));
	List<Notify> notifs = service.notifynum(eid);
	
	if(notifs == null) {IntJson i= new IntJson(0);	try {
		res.getWriter().append(gson.toJson(i));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();}}
	
	else {
	for(Notify n : notifs)service.nstack.push(n);	
	IntJson i= new IntJson(notifs.size()); 
	try {
		res.getWriter().append(gson.toJson(i));res.setStatus(200);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
}
public static void notify(HttpServletRequest req, HttpServletResponse res) throws IOException {	
	System.out.println("nstack peek:"+ service.nstack.peek().toString());
	Notify n = service.nstack.pop();
	res.getWriter().append(gson.toJson(n));
}
public static void populate(HttpServletResponse res) throws IOException {
	if(service.estack.peek() != null)
	res.getWriter().append(gson.toJson(service.estack.pop()));
}
public static void moreInfo(HttpServletRequest req, HttpServletResponse res, String s) throws IOException {
	char f = s.charAt(0);
	switch(f) {
	case'e':{break;}
	case's':{break;}
	case'h':{break;}
	}
}	
public static void approve(HttpServletRequest req, HttpServletResponse res, String s) throws IOException {
	char f = s.charAt(0);
	double eid =Double.parseDouble(req.getParameter("eid"));
	double rid =Double.parseDouble(req.getParameter("rid"));
	switch(f) {
	case's':{if(service.sApprove(rid, eid))	res.getWriter().append(gson.toJson(new IntJson(0)));	
			else res.getWriter().append(gson.toJson(new IntJson()));					break;}
	case'h':{if(service.hApprove(rid, eid))	res.getWriter().append(gson.toJson(new IntJson(0)));	
			else res.getWriter().append(gson.toJson(new IntJson()));					break;}
	case'b':{if(service.bApprove(rid, eid))	res.getWriter().append(gson.toJson(new IntJson(0)));	
			else res.getWriter().append(gson.toJson(new IntJson()));					break;}
	}
}
public static void bConfirm(HttpServletRequest req, HttpServletResponse res) throws IOException {
	double rid =Double.parseDouble(req.getParameter("rid"));
	if(service.bConfirm(rid))	res.getWriter().append(gson.toJson(new IntJson(0)));	
	else res.getWriter().append(gson.toJson(new IntJson()));					
	}

public static void uploadGrade(HttpServletRequest req, HttpServletResponse res) throws IOException {
	double rid =Double.parseDouble(req.getParameter("rid"));
	String grade =req.getParameter("grade");
	if(service.uploadGrade(rid, grade))	res.getWriter().append(gson.toJson(new IntJson(0)));	
	else res.getWriter().append(gson.toJson(new IntJson()));		
}
public static void deny(HttpServletRequest req, HttpServletResponse res) throws IOException {
	double rid =Double.parseDouble(req.getParameter("rid"));
	if(service.deny(rid))	res.getWriter().append(gson.toJson(new IntJson(0)));	
	else res.getWriter().append(gson.toJson(new IntJson()));		
}



}


