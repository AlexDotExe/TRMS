package archive;

import models.Employee;
import models.Request;
import models.Status;

public interface AllDAO {

	public Employee getE(double eid, String pass);
	public boolean newRequest(Request r);
	public Request viewRequest(Double r);
	public boolean updateStatus(Status s);
	public Status getStatus();
	
}
