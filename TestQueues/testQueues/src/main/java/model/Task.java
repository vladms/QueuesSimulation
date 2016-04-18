package model;

public class Task {
	private int ID;
	private int arrivalTime = 0;
	private int processTime = 0;
	
	public Task(){
	}
	
	public Task(int arrivalTime, int processTime){
		this.arrivalTime = arrivalTime;
		this.processTime = processTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	
	@Override 
	public String toString(){
		return "Task: " + String.valueOf(ID)  + " "+ String.valueOf(arrivalTime) + " " + String.valueOf(processTime);
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
