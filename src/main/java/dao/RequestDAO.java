package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Employee;
import models.Request;
import util.JDBCConnection;

public class RequestDAO {
	
	EmployeeDAO em = new EmployeeDAO();
	public static Connection conn = JDBCConnection.getConnection();
	public boolean newRequest(Request r) {
		try {
		
			// Our addPokemon should use our sequence.
			// add_pokemon2() procedure
			String sql = "CALL newRequest(?,?,?,?,?,?,?,?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setDouble(1, em.easy(r.getEid()).getSid());
			cs.setDouble(2, r.getEid());
			cs.setDouble(3, r.getCosts());
			cs.setDouble(4, r.getPreim());
			cs.setString(5, r.getDates());
			cs.setString(6, r.getEventstart());
			cs.setString(7, r.getMingrade());
			cs.setString(8, r.getLocale());
			cs.setString(9, r.getInfo());
			cs.setString(10, r.getJust());
			cs.setString(11, r.getFg());
			cs.setString(12, r.getEt());
		
			cs.execute();
			System.out.println("Database Access Success ");
			Employee e = em.easy(r.getEid());
		//Update Employees Remaining balance	
		double remain = e.getRemaining() - r.getPreim();
		double pending = e.getPending() +r.getPreim();
			if(em.updatePending(remain,pending)) 
		//Add a notification to the employee supervisor
				return true;

		} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Something went wrong"); return false;}
	
	public boolean uploadGrade(double rid, String grade) {
		try {
				
			String sql = "Update notify set typ = ?, eid=? where rid = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "bConfirm");
			ps.setDouble(2,9000);
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE request SET fingrade = ? WHERE rid = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, grade);
			ps.setDouble(2, rid);
			ps.executeQuery();
			
			return true;

		} catch (SQLException e) {e.printStackTrace();}
			System.out.println("Something went wrong"); return false;}	

	
	public Request getRequest(double rid) {
		try {
			String sql = "SELECT * FROM request WHERE rid = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {return new Request(rs.getDouble("rid"), rs.getDouble("eid"), rs.getDouble("COSTS"),rs.getDouble("PREIMBURSEMENT"),rs.getString("DATES"),rs.getString("EVENTSTART"),
					rs.getString("MINGRADE"),rs.getString("LOCALE"),rs.getString("INFO"),rs.getString("JUSTIFICATION"),rs.getString("EXTRA1"),rs.getString("EXTRA2"),rs.getString("EXTRA3"),rs.getString("FINGRADE"),rs.getString("EVENTTYPE"));}

		} catch (SQLException e) {e.printStackTrace();}
		System.out.println("returned null"); return null;}


}
