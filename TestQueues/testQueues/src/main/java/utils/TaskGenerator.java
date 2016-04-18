package utils;

import java.util.ArrayList;
import java.util.Random;

import model.Task;

public class TaskGenerator {
	public ArrayList<Task> getTasksWithRestrictions(int minArrivingTime, int maxArrivingTime, int minServiceTime,
			int maxServiceTime, int simulationInterval, int numberOfClients) {
		//System.out.println("Arriving Time: " + minArrivingTime +" " + maxArrivingTime + " " +minServiceTime +  " " +maxServiceTime );
		ArrayList<Task> tasksArray = new ArrayList<Task>();
		int currentTime = 0;
		int clientIndex;
		for (clientIndex = 0; clientIndex < numberOfClients; ++clientIndex){
			Task task = new Task();
			task.setID(clientIndex);
			Random random = new Random();
			int time = random.nextInt(maxArrivingTime - minArrivingTime + 1) + minArrivingTime;
			if (time > maxArrivingTime || time < minArrivingTime){
				System.err.println("Error: random is idiot " + time + " " + maxArrivingTime + " " + minArrivingTime);
			}
			currentTime+=time;
			task.setArrivalTime(currentTime);
			random = new Random();
			time = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
			if (time > maxServiceTime || time < minServiceTime){
				System.err.println("Error: random is idiot " + time + " " + maxServiceTime + " " + minServiceTime);
			}
			task.setProcessTime(time);
			tasksArray.add(task);
		}
		return tasksArray;
	}
}
