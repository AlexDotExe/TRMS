package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.*;

import models.Employee;
import models.Notify;
import models.Request;
import util.JDBCConnection;

@SuppressWarnings("unused")
public class NotifyDAO {
	public static Connection conn = JDBCConnection.getConnection();
	RequestDAO rq = new RequestDAO();
	EmployeeDAO em = new EmployeeDAO();
	
	public List<Notify> notifList(double eid){
		try {
			
			String sql = "SELECT * FROM notify where eid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, eid);
			ResultSet rs = ps.executeQuery();

			List<Notify> Listing = new ArrayList<Notify>();

			while (rs.next()) {
				Listing.add(new Notify(rs.getDouble("RID"), rs.getDouble("eid"),rs.getString("TYP")));
	}

			return Listing;

		} catch (SQLException e) {
			e.printStackTrace();} return null;
		
	}
public void emoreInfo(double rid, String note) throws SQLException {
		
		Request r = rq.getRequest(rid);
		Notify n = getNotify(rid);
		double id = n.getEid();
		String s = ""; 
		if(em.isHead(id))s = "Department Head Inquiry: "+note;
		else if(id == 9000)s = "Benefits Coordinator Inquiry: "+note;
		else s = "Supervior Inquiry: "+ note;
		
		String sql = "UPDATE notify SET eid = ?,typ = ? WHERE rid = ?  ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, r.getEid());
		ps.setString(2, s);
		ps.setDouble(3, rid);
		ps.executeQuery();
		
		}
public Notify getNotify(double rid) {
	try {
		String sql = "SELECT * FROM notify WHERE rid = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, rid);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) return new Notify(rs.getDouble("RID"),rs.getDouble("eid"),rs.getString("TYP")); }
		catch (SQLException e) {e.printStackTrace();}
	System.out.println("returned null"); return null;}

public void smoreInfo(double rid, String note) throws SQLException {
	try {
		Request r = rq.getRequest(rid);
		Employee e = em.easy(r.getEid());
		
		Notify n = getNotify(rid);
		double id = n.getEid();
		String s = ""; 
		if(em.isHead(id))s = "Department Head Inquiry: "+note;
		else if(id == 9000)s = "Benefits Coordinator Inquiry: "+note;
		
		String sql = "UPDATE notify SET eid = ? WHERE rid =?  ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, e.getSid());
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		sql = "UPDATE notify SET typ = ? WHERE rid = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, s);
		ps.setDouble(2, rid);
		ps.executeQuery();
		}catch(Exception e) {System.out.println(e.getMessage());}
}
public void hmoreInfo(double rid, String note) throws SQLException {
	Request r = rq.getRequest(rid);
	double h = em.getHead(r.getEid());
	String s = "Benefits Coordinator wants to know : "+note;
	
	
	String sql = "UPDATE notify SET eid = ? WHERE rid =?  ";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setDouble(1, h);
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	sql = "UPDATE notify SET typ = ? WHERE rid = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, s);
	ps.setDouble(2, rid);
	ps.executeQuery();
	}

@SuppressWarnings("resource")
public boolean answer(double rid, String ans) {
	try {
	Request r = rq.getRequest(rid);
	double id = 0;
	if(ans.charAt(0)=='D') {id = em.getHead(r.getEid());}
	else if(ans.charAt(0) == 'B')id = 9000;
	else id = em.easy(r.getEid()).getSid(); 
	
	String sql = "UPDATE notify SET eid = ? WHERE rid =?  ";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setDouble(1, id);
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	sql = "UPDATE notify SET typ = ? WHERE rid = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, "answer");
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	if(id == 9000) {
	sql = "UPDATE request SET extra3 = ? WHERE rid = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, "answer");
	ps.setDouble(2, rid);
	ps.executeQuery();
	}
	else if(em.isHead(id)) {
		sql = "UPDATE request extra2 typ = ? WHERE rid = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "answer");
		ps.setDouble(2, rid);
		ps.executeQuery();
		}
	else {
		sql = "UPDATE request SET extra = ? WHERE rid = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "answer");
		ps.setDouble(2, rid);
		ps.executeQuery();
		}
	
	return true;}
	catch(Exception e) {System.out.println(e.getStackTrace());}
	return false;

}
}
