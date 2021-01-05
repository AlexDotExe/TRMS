package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Employee;
import util.JDBCConnection;

public class EmployeeDAO {
	public static Connection conn = JDBCConnection.getConnection();
	
	
	public boolean isHead(double eid) {
		if(eid % 1000 == 0 || eid == 9999)
			return true;
		return false;
	}
	public double getHead(double eid) {
		double n = eid/1000;
		 
		if(n<1 ){eid = 1000;n = 10;}
		if( n<2 ){eid = 2000;n = 10;}
		if( n<3 ){eid = 3000;n = 10;}
		if( n<4 ){eid = 4000;n = 10;}
		if( n<5 ){eid = 5000;n = 10;}
		if( n<6 ){eid = 6000;n = 10;}
		if( n<7 ){eid = 7000;n = 10;}
		if( n<8 ){eid = 8000;n = 10;}
		if( n<9 ){eid = 9000;n = 10;}
		if( n<10 ){eid =9999;n = 10;}
		return eid;
	}
	public boolean updatePending(double eid, double p){
		try {
			String sql = "UPDATE employees set pending = ? WHERE eid = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, p);
			ps.setDouble(2, eid);

			ps.executeQuery();
			return true;

		} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Something went wrong"); return false;
	}
	public Employee easy(double eid) {
		try {
				String sql = "SELECT * FROM employees WHERE eid = ?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, eid);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					return new Employee(rs.getDouble("eid"), rs.getDouble("sid"), rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("PASSWORD"),rs.getDouble("REMAINING"), rs.getDouble("PENDING"));
				}

			} catch (SQLException e) {e.printStackTrace();}
				System.out.println("Something went wrong"); return null;}
	
	public Employee getE(double eid, String pass) {
		try {
				String sql = "SELECT * FROM employees WHERE eid = ? AND password = ?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, eid);
				ps.setString(2,pass);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					return new Employee(rs.getDouble("eid"), rs.getDouble("sid"), rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("PASSWORD"),rs.getDouble("REMAINING"), rs.getDouble("PENDING"));
				}

			} catch (SQLException e) {e.printStackTrace();}
			System.out.println("returned null"); return null;}

}
