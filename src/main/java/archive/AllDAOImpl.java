package archive;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Employee;
import models.Notify;
import models.Request;
import util.JDBCConnection;

import models.Employee;
import models.Status;

public class AllDAOImpl implements AllDAO {
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
			String sql = "UPDATE employees set pending = ? WHERE e_id = ?";
			
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
				String sql = "SELECT * FROM employees WHERE e_id = ?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, eid);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					return new Employee(rs.getDouble("E_ID"), rs.getDouble("S_ID"), rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("PASSWORD"),rs.getDouble("REMAINING"), rs.getDouble("PENDING"));
				}

			} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
				System.out.println("Something went wrong"); return null;}
	
	public Employee getE(double eid, String pass) {
		try {
				String sql = "SELECT * FROM employees WHERE e_id = ? AND password = ?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, eid);
				ps.setString(2,pass);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					return new Employee(rs.getDouble("E_ID"), rs.getDouble("S_ID"), rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("PASSWORD"),rs.getDouble("REMAINING"), rs.getDouble("PENDING"));
				}

			} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
			System.out.println("returned null"); return null;}
	
	public Request getRequest(double rid) {
		try {
			String sql = "SELECT * FROM request WHERE r_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {return new Request(rs.getDouble("R_ID"), rs.getDouble("E_ID"), rs.getDouble("COSTS"),rs.getDouble("PREIMBURSEMENT"),rs.getString("DATES"),rs.getString("EVENTSTART"),
					rs.getString("MINGRADE"),rs.getString("LOCALE"),rs.getString("INFO"),rs.getString("JUSTIFICATION"),rs.getString("EXTRA1"),rs.getString("EXTRA2"),rs.getString("EXTRA3"),rs.getString("FINGRADE"),rs.getString("EVENTTYPE"));}

		} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
		System.out.println("returned null"); return null;}

	public List<Notify> numNotif(double eid){
	try {

		String sql = "SELECT * FROM notify where E_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, eid);
		ResultSet rs = ps.executeQuery();

		List<Notify> Listing = new ArrayList<Notify>();

		while (rs.next()) {
			Listing.add(new Notify(rs.getDouble("RID"), rs.getDouble("E_ID"),rs.getString("TYP")));
}

		return Listing;

	} catch (SQLException e) {
		System.out.println(e.getStackTrace().toString());} return null;
	
}
	
	public Notify getNotify(double eid) {
		try {
			String sql = "SELECT * FROM notify WHERE e_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, eid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) return new Notify(rs.getDouble("RID"),rs.getDouble("E_ID"),rs.getString("TYP")); }
			catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
		System.out.println("returned null"); return null;}

	public boolean newRequest(Request r) {
		try {
		
			// Our addPokemon should use our sequence.
			// add_pokemon2() procedure
			String sql = "CALL newRequest(?,?,?,?,?,?,?,?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setDouble(1, easy(r.getEid()).getSid());
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
			Employee e = easy(r.getEid());
		//Update Employees Remaining balance	
		double remain = e.getRemaining() - r.getPreim();
		double pending = e.getPending() +r.getPreim();
			if(updatePending(remain,pending)) 
		//Add a notification to the employee supervisor
				return true;

		} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Something went wrong"); return false;}
	
	public boolean sApprove(double rid, double eid){
		try {
			eid = getHead(eid);
			
			String sql = "UPDATE notify SET e_id = ?,typ =? WHERE r_id =?  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, eid);
			ps.setString(2, "happrove");
			ps.setDouble(2, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET ss = 1 WHERE re_id =?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			return true;
		} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
		System.out.println("Something went wrong"); return false;}
			
	
	
	public boolean hApprove(double rid, double eid){
		try {
			eid = 9000;
			String sql = "UPDATE notify SET e_id = ?,typ =? where r_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET ss = 1, hs = 1 WHERE re_id =?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getStackTrace().toString());}
		System.out.println("Something went wrong"); return false;}
	
public void smoreInfo(double rid, String note) throws SQLException {
	try {
		Request r = getRequest(rid);
		Employee e = easy(r.getEid());
		
		Notify n = getNotify(rid);
		double id = n.getEid();
		String s = ""; 
		if(isHead(id))s = "Department Head Inquiry: "+note;
		else if(id == 9000)s = "Benefits Coordinator Inquiry: "+note;
		
		String sql = "UPDATE notify SET e_id = ? WHERE r_id =?  ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, e.getSid());
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		sql = "UPDATE notify SET typ = ? WHERE r_id = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, s);
		ps.setDouble(2, rid);
		ps.executeQuery();
		}catch(Exception e) {System.out.println(e.getMessage());}
}
public void hmoreInfo(double rid, String note) throws SQLException {
	Request r = getRequest(rid);
	double h = getHead(r.getEid());
	String s = "Benefits Coordinator wants to know : "+note;
	
	
	String sql = "UPDATE notify SET e_id = ? WHERE r_id =?  ";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setDouble(1, h);
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	sql = "UPDATE notify SET typ = ? WHERE r_id = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, s);
	ps.setDouble(2, rid);
	ps.executeQuery();
	}

public boolean answer(double rid, String ans) {
	try {
	Request r = getRequest(rid);
	double id = 0;
	if(ans.charAt(0)=='D') {id = getHead(r.getEid());}
	else if(ans.charAt(0) == 'B')id = 9000;
	else id = easy(r.getEid()).getSid(); 
	
	String sql = "UPDATE notify SET e_id = ? WHERE r_id =?  ";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setDouble(1, id);
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	sql = "UPDATE notify SET typ = ? WHERE r_id = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, "answer");
	ps.setDouble(2, rid);
	ps.executeQuery();
	
	if(id == 9000) {
	sql = "UPDATE request SET extra3 = ? WHERE r_id = ?  ";
	ps = conn.prepareStatement(sql);
	ps.setString(1, "answer");
	ps.setDouble(2, rid);
	ps.executeQuery();
	}
	else if(isHead(id)) {
		sql = "UPDATE request extra2 typ = ? WHERE r_id = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "answer");
		ps.setDouble(2, rid);
		ps.executeQuery();
		}
	if(id == 9000) {
		sql = "UPDATE request SET extra = ? WHERE r_id = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "answer");
		ps.setDouble(2, rid);
		ps.executeQuery();
		}
	
	return true;}
	catch(Exception e) {System.out.println(e.getStackTrace());}
	return false;

}
public boolean bApprove(double rid, double eid){
	try {
		Request r = getRequest(rid);
		String sql = "UPDATE notify SET e_id = ? where r_id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, r.getEid());
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		sql = "UPDATE status SET ss = 1, hs = 1, bs =1 WHERE re_id =?  ";
		ps = conn.prepareStatement(sql);
		ps.setDouble(1, rid);
		ps.executeQuery();
		
		sql = "UPDATE notify SET typ = ? WHERE r_id = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "uploadGrade");
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		return true;

	} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
		System.out.println("Something went wrong"); return false;}	
public boolean uploadGrade(double rid, String grade) {
	try {
		
		Request r = getRequest(rid);
		Employee e = easy(r.getEid());
		
		String sql = "Update notify set typ = ?, eid=? where r_id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "Bconfirm");
		ps.setDouble(2,9000);
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		sql = "UPDATE request SET fingrade = ?, WHERE r_id = ?  ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, grade);
		ps.setDouble(2, rid);
		ps.executeQuery();
		
		return true;

	} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
		System.out.println("Something went wrong"); return false;}	

public boolean bConfirm(double rid){
try {
			
			Request r = getRequest(rid);
			Employee e = easy(r.getEid());
			double pend = e.getPending() - r.getPreim();
			
			String sql = "Delete from notify where r_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET re_status = ?,re_granted =? WHERE re_id = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "APPROVED");
			ps.setDouble(2, r.getPreim());
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE employees SET pending = ? WHERE e_id = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, pend);
			ps.setDouble(2, e.getEid());
			ps.executeQuery();
			
			return true;

		} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
			System.out.println("Something went wrong"); return false;}	

	public boolean decline(double rid) {
		try {
			
			Request r = getRequest(rid);
			Employee e = easy(r.getEid());
			double remain = e.getRemaining() + r.getPreim();
			double p = e.getPending()-r.getPreim();
			String sql = "Delete from notify where r_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, rid);
			ps.executeQuery();
			
			sql = "UPDATE status SET re_status = ?,re_granted =? WHERE re_id = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "DENIED");
			ps.setDouble(2, 0);
			ps.setDouble(3, rid);
			ps.executeQuery();
			
			sql = "UPDATE employees SET remaining = ?, pending = ? WHERE e_id = ?  ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, remain);
			ps.setDouble(2, p);
			ps.setDouble(3, e.getEid());
			ps.executeQuery();
			
			return true;

		} catch (SQLException e) {System.out.println(e.getStackTrace().toString());}
			System.out.println("Something went wrong"); return false;}	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
	
	
	
	
	
	@Override
	public boolean updateStatus(Status s) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Request viewRequest(Double r) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
