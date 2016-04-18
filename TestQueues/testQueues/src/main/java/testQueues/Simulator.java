package testQueues;

import java.util.ArrayList;

import gui.AppInterfaceButtonEvents;
import gui.MainFrame;
import gui.SimulatorFrame;
import model.Task;
import utils.TaskGenerator;
import java.lang.Runnable;

public class Simulator implements Runnable, AppInterfaceButtonEvents {
	private Scheduler scheduler;
	private static MainFrame mainFrame;
	private static SimulatorFrame simFrame;

	private int currentTask = 0;
	private TaskGenerator taskGenerator;

	private int minArrivingTime;
	private int maxArrivingTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int nrOfServers;
	private int nrOfTasks;
	private int simulationInterval;
	
	private int maxNumberOfTasks = 0;
	private int maxNumberOfTasksTime = 0;

	private ArrayList<Task> tasksArray;

	private static Simulator sSimulatorInstance;
	
	private Simulator() {
		mainFrame = new MainFrame(this);
		taskGenerator = new TaskGenerator();
		
	}
	
	public static Simulator getInstance(){
		if (sSimulatorInstance == null){
			sSimulatorInstance = new Simulator();
		}
		return sSimulatorInstance;
	}

	public static void main(String[] args) {
		sSimulatorInstance = Simulator.getInstance();
		
		
	}

	//@Override
	public void run() {
		int currentTime = 0;
		for (currentTime = 0; currentTime < simulationInterval && currentTask < nrOfTasks; ++currentTime) {
			Task task = tasksArray.get(currentTask);
			int aux = scheduler.getNumberOfTasks();
			if (maxNumberOfTasks < aux){
				maxNumberOfTasks = aux;
				maxNumberOfTasksTime = currentTime;
			}
			scheduler.updateServers(1);
			
			if (task.getArrivalTime() == currentTime){
				if (scheduler.dispatchTaskOnServer(task)){
					currentTask++;
					currentTime--;
				} else {
					currentTime--;
				}
			}
			simFrame.displayData(scheduler.getTasks());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boolean queuesAreEmpty = false;
		while (!queuesAreEmpty) {
			scheduler.updateServers(1);
			simFrame.displayData(scheduler.getTasks());
			queuesAreEmpty = scheduler.queuesAreEmpty();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		presentResults();
	}
	
	private void presentResults(){
		float totalAverageWaitingTime = 0;
		float totalAverageProcessingTime = 0;

		for (int index = 0; index < nrOfServers; ++index){
			float result = scheduler.getAverageWaitingTimeOfServer(index);
			totalAverageWaitingTime += result;
			System.err.println("Average waiting time of queue: " + index + " is equal: " + result + "\n");
			result = scheduler.getAverageProcessingTimeOfServer(index);
			totalAverageProcessingTime += result;
			System.err.println("Average processing time of queue: " + index + " is equal: " + result + "\n");
		}
		System.err.println("Total average waiting time: " + totalAverageWaitingTime / (float)nrOfServers + "\n");
		System.err.println("Total average processing time: " + totalAverageProcessingTime / (float)nrOfServers + "\n");

		System.err.println("Peak period with maximum number of tasks was: " + maxNumberOfTasksTime + " with: " + maxNumberOfTasks + " number of tasks" +"\n");
	}
	
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			mainFrame.showErrorMessage(s + " is not in good format!");
			return false;
		} catch (NullPointerException e) {
			mainFrame.showErrorMessage(s + " is not in good format!");
			return false;
		}
		return true;
	}

	public void buttonTouched(String minArrivingTime, String maxArrivingTime, String minServiceTime,
			String maxServiceTime, String nrOfServers, String nrOfClients, String simulationInterval) {
		boolean goodInput = true;

		if (goodInput = isInteger(minArrivingTime)) {
			this.minArrivingTime = Integer.parseInt(minArrivingTime);
		}
		if (goodInput && (goodInput = isInteger(maxArrivingTime))) {
			this.maxArrivingTime = Integer.parseInt(maxArrivingTime);
		}
		if (goodInput && (goodInput = isInteger(minServiceTime))) {
			this.minServiceTime = Integer.parseInt(minServiceTime);
		}
		if (goodInput && (goodInput = isInteger(maxServiceTime))) {
			this.maxServiceTime = Integer.parseInt(maxServiceTime);
		}
		if (goodInput && (goodInput = isInteger(nrOfServers))) {
			this.nrOfServers = Integer.parseInt(nrOfServers);
		}
		if (goodInput && (goodInput = isInteger(nrOfClients))) {
			this.nrOfTasks = Integer.parseInt(nrOfClients);
		}
		if (goodInput && (goodInput = isInteger(simulationInterval))) {
			this.simulationInterval = Integer.parseInt(simulationInterval);
		}

		if (goodInput && (this.minArrivingTime < 0 || this.maxArrivingTime < 0 || this.minServiceTime < 0
				|| this.maxServiceTime < 0 || this.nrOfServers < 0 || this.simulationInterval < 0)) {
			mainFrame.showErrorMessage("Values can not be negative!");
			goodInput = false;
		}

		if (goodInput && (this.minArrivingTime > this.maxArrivingTime)) {
			mainFrame.showErrorMessage("Minimum arriving time should be less than maximum!");
			goodInput = false;
		}
		if (goodInput && (this.minServiceTime > this.maxServiceTime)) {
			mainFrame.showErrorMessage("Minimum service time should be less than maximum!");
			goodInput = false;
		}
		if (goodInput && (this.nrOfServers == 0)) {
			mainFrame.showErrorMessage("Number of queues should pe positive!");
			goodInput = false;
		}
		

		if (goodInput && (this.nrOfTasks == 0)) {
			mainFrame.showErrorMessage("Number of clients should pe positive!");
			goodInput = false;
		}

		if (goodInput && (this.simulationInterval == 0)) {
			mainFrame.showErrorMessage("Simulation interval should pe positive!");
			goodInput = false;
		}
		if (goodInput) {
			getTasks();
		}
	}
	
	private void getTasks() {
		tasksArray = new ArrayList<Task>();
		tasksArray = taskGenerator.getTasksWithRestrictions(minArrivingTime, maxArrivingTime, minServiceTime,
				maxServiceTime, simulationInterval, nrOfTasks);
		
		for (int index = 0; index < nrOfTasks; index++) {
			Task task = tasksArray.get(index);
		}
		startSimulation();
	}
	
	private void startSimulation() {
		scheduler = new Scheduler(nrOfServers);
		simFrame = new SimulatorFrame();
		Thread th = new Thread(sSimulatorInstance);
		th.start();
		}
	
	
}
