  package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Employee;
import models.Request;
import util.JDBCConnection;

public class StatusDAO {
	public static Connection conn = JDBCConnection.getConnection();
	RequestDAO rq = new RequestDAO();
	EmployeeDAO em = new EmployeeDAO();
	public boolean sApprove(double rid, double eid){
		try {
			eid = em.getHead(eid);
			
			String sql = "UPDATE notify SET eid = ?,typ = ? WHERE rid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, eid);
			ps.setString(2, "happrove");
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET ss = 1 WHERE rid =?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			return true;
		} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Something went wrong"); return false;}
			
	
	
	public boolean hApprove(double rid, double eid){
		try {
			eid = 9000;
			String sql = "UPDATE notify SET eid = ?,typ =? where rid = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, eid);
			ps.setString(2, "bApprove");
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET ss = 1, hs = 1 WHERE rid =?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();}
		System.out.println("Something went wrong"); return false;}
	
	public boolean bApprove(double rid, double eid){
		try {
			Request r = rq.getRequest(rid);
			String sql = "UPDATE notify SET eid = ?,typ = ? where rid = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, r.getEid());
			ps.setString(2, "uploadGrade");
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET ss = 1, hs = 1, bs =1 WHERE rid =?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			return true;

		} catch (SQLException e) {e.printStackTrace();}
			System.out.println("Something went wrong"); return false;}	
	public boolean bConfirm(double rid){
		try {
					
					Request r = rq.getRequest(rid);
					Employee e = em.easy(r.getEid());
					double pend = e.getPending() - r.getPreim();
					
					String sql = "Delete from notify where rid = ? ";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setDouble(1, rid);
					ps.executeQuery();
					
					sql = "UPDATE status SET re_status = ?,re_granted =? WHERE rid = ?  ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, "APPROVED");
					ps.setDouble(2, r.getPreim());
					ps.setDouble(3, rid);
					ps.executeQuery();
					
					sql = "UPDATE employees SET pending = ? WHERE eid = ?  ";
					ps = conn.prepareStatement(sql);
					ps.setDouble(1, pend);
					ps.setDouble(2, e.getEid());
					ps.executeQuery();
					
					return true;

				} catch (SQLException e) {e.printStackTrace();}
					System.out.println("Something went wrong"); return false;}	

			
	public boolean deny(double rid) {
		try {
			
			Request r = rq.getRequest(rid);
			Employee e = em.easy(r.getEid());
			double remain = e.getRemaining() + r.getPreim();
			double p = e.getPending()-r.getPreim();
			String sql = "Delete from notify where rid = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET re_status = ?,re_granted =? WHERE rid = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "DENIED");
			ps.setDouble(2, 0);
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE employees SET remaining = ?, pending = ? WHERE eid = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, remain);
			ps.setDouble(2, p);
			ps.setDouble(3, e.getEid());
			ps.executeQuery();
			
			return true;

		} catch (SQLException e) {e.printStackTrace();}
			System.out.println("Something went wrong"); return false;}	
	
}
