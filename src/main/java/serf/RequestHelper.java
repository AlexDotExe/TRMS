package serf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.DBController;
public class RequestHelper {
	

	public static void process(HttpServletRequest req, HttpServletResponse res) throws IOException {
		//method will delegate other methods on controller layer of application to  process the request 
	try {
		String parse = req.getParameter("action");
	
	
	switch(parse) {
	case"login":{
		DBController.getE(req, res);
		break;}
	case"populate":{
		DBController.populate(res);
		break;}
	case"new" :{
		DBController.makeRequest(req, res);
		break;
	}
	case"notifnum":{
		DBController.notifnum(req, res);
		break;
	}
	case"notify":{
		DBController.notify(req, res);
		break;
	}
	case"emoreInfo":{
		DBController.moreInfo(req, res, parse);
		break;
	}
	case"smoreInfo":{
		DBController.moreInfo(req, res, parse);
		break;
	}
	case"hmoreInfo":{
		DBController.moreInfo(req, res, parse);
		break;
	}
	case"sApprove":{
		DBController.approve(req, res, parse);
		break;
	}
	case"hApprove":{
		DBController.approve(req, res, parse);
		break;
	}
	case"bApprove":{
		DBController.approve(req, res, parse);
		break;
	}
	case"deny":{
		DBController.deny(req, res);
		break;
	}
	case"uploadGrade":{
		DBController.uploadGrade(req, res);
		break;
	}
	case"bConfirm":{
		DBController.bConfirm(req, res);
		break;
	}
	default:{break;}
	}
	}catch(Exception e) {System.out.println(e.getMessage());e.printStackTrace();}
	}
	
}
