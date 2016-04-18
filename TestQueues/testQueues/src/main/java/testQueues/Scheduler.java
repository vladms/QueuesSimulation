package testQueues;

import java.util.ArrayList;

import model.Server;
import model.Task;

public class Scheduler {

	public static final int MAX_WAITING_TIME = 20;

	private ArrayList<Server> serversList;
	private int numberOfServers;

	public Scheduler(int numberOfServers) {
		this.numberOfServers = numberOfServers;
		serversList = new ArrayList<Server>();

		for (int index = 0; index < numberOfServers; ++index) {
			Server server = new Server(index);
			serversList.add(server);
			Thread th = new Thread(server);
			th.start();
		}
	}

	boolean dispatchTaskOnServer(Task task) {
		int index;
		int indexOfBestUsedQueue = -1;
		int lowestProcessingTimeUsedQueue = 1000;
		int indexOfBestUnusedQueue = -1;
		int lowestProcessingTimeUnusedQueue = 1000;
		for (index = 0; index < numberOfServers; ++index) {
			Server server = serversList.get(index);
			int serverProcessingTime = server.getProcessingTime();
			System.out.println(
					"serverProcessingTime: " + serverProcessingTime + " " + index + " " + server.isUsed() + "\n");
			if (server.isUsed()) {
				if (lowestProcessingTimeUsedQueue > serverProcessingTime) {
					lowestProcessingTimeUsedQueue = serverProcessingTime;
					indexOfBestUsedQueue = index;
				}
			} else {
				if (lowestProcessingTimeUnusedQueue > serverProcessingTime) {
					lowestProcessingTimeUnusedQueue = serverProcessingTime;
					indexOfBestUnusedQueue = index;
				}
			}
		}
		
		if (lowestProcessingTimeUsedQueue != -1) {

			if (lowestProcessingTimeUsedQueue + task.getProcessTime() < MAX_WAITING_TIME) {
				Server server = serversList.get(indexOfBestUsedQueue);
				server.addTask(task);
				server.setUsed(true);
			} else if (lowestProcessingTimeUnusedQueue != -1
					&& lowestProcessingTimeUnusedQueue + task.getProcessTime() < MAX_WAITING_TIME) {
				Server server = serversList.get(indexOfBestUnusedQueue);
				server.addTask(task);
				server.setUsed(true);
			} else {
				if (lowestProcessingTimeUsedQueue < lowestProcessingTimeUnusedQueue
						|| lowestProcessingTimeUnusedQueue == -1) {
					Server server = serversList.get(indexOfBestUsedQueue);
					server.addTask(task);
					server.setUsed(true);
				} else {
					Server server = serversList.get(indexOfBestUnusedQueue);
					server.addTask(task);
					server.setUsed(true);
				}
			}
		} else {
			Server server = serversList.get(indexOfBestUnusedQueue);
			server.addTask(task);
			server.setUsed(true);
			for (int reversedIndex = 0; reversedIndex < index; ++reversedIndex) {
				Server serverReversed = serversList.get(reversedIndex);
				Task taskReversed = serverReversed.getLastTask();
				if (task != null) {
					this.dispatchTaskOnServer(taskReversed);
				}
			}
		}
		return true;
	}

	public void updateServers(int updateNumber) {
		int index;
		for (index = 0; index < numberOfServers; ++index) {
			Server server = serversList.get(index);
			server.updateProcessingTime(updateNumber);
			if (server.getProcessingTime() <= 0 && server.isUsed()) {
				server.setUsed(false);
			}
		}
	}

	public Task[] getTasks(int index) {
		Task[] tasks = serversList.get(index).getTasks();
		return tasks;
	}

	public Task[][] getTasks() {
		Task[][] tasks = new Task[numberOfServers][];
		for (int index = 0; index < numberOfServers; index++) {
			Server server = serversList.get(index);
			Task[] tasksFromServer = server.getTasks();
			tasks[index] = tasksFromServer;
		}
		return tasks;
	}

	public float getAverageWaitingTimeOfServer(int index) {
		return serversList.get(index).getAverageWaitingTime();
	}

	public float getAverageProcessingTimeOfServer(int index) {
		return serversList.get(index).getAverageProcessingTime();
	}

	public int getNumberOfTasks() {
		int result = 0;
		for (int index = 0; index < numberOfServers; index++) {
			Server server = serversList.get(index);
			result += server.getTasks().length;
		}
		return result;
	}

	public boolean queuesAreEmpty() {
		for (int index = 0; index < numberOfServers; ++index) {
			Server server = serversList.get(index);
			if (server.getTasks().length != 0) {
				return false;
			}
		}
		return true;
	}
}
