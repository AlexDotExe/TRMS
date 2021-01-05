package models;

public class IntJson {
String response;	
int num;

public IntJson(int num) {
	super();
	this.num = num;
	response = "success";
}
public IntJson() {
	response = "failed";
	num = 0;		
}
}
