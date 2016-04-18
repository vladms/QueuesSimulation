package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
	private int ID;
	private boolean used = false;
	private BlockingQueue<Task> bq;
	private AtomicInteger waitingTime;
	private int averageWaitingTime = 0;
	private int averageProcessingTime = 0;
	private int numberOfTasks = 0;


	public Server(int ID) {
		this.setUsed(false);
		this.ID = ID;
		bq = new LinkedBlockingQueue<Task>();
		waitingTime = new AtomicInteger(0);
	}

	public void run() {
		while (true) {
			Task t;
			try {
				t = bq.peek();
				
				if (t != null) {
					System.out.println("Task: " + t.toString() + " is now processing on server: "+ this.ID);
					averageProcessingTime += t.getProcessTime();
					Thread.sleep(t.getProcessTime() * 100);
					waitingTime.addAndGet(t.getProcessTime() * (-1));
					System.out.println("Task: " + t.toString() + " is finished by server: "+ this.ID);
					t = bq.take();
					Task t2 = bq.peek();
					if (t2 != null){
						averageWaitingTime += t.getProcessTime();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addTask(Task task) {
		bq.add(task);
		waitingTime.addAndGet(task.getProcessTime());
		numberOfTasks++;
		System.out.println("Added task: " + task.toString() + " on server: "+ this.ID + " at: " + task.getArrivalTime() + "\n");
	}

	public Task[] getTasks() {
		Task[] tasks = new Task[bq.size()];
		bq.toArray(tasks);
		return tasks;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getProcessingTime() {
		int time = 0;
		for (Task task : bq) {
			time += task.getProcessTime();
		}
		return time;
	}
	
	public float getAverageProcessingTime() {
		float result = 0;
		if (numberOfTasks == 0){
			return 0;
		}
		result = (float)averageProcessingTime/(float)numberOfTasks;
		return result;
	}
	
	public float getAverageWaitingTime(){
		float result = 0;
		if (numberOfTasks == 0){
			return 0;
		}
		result = (float)averageWaitingTime/(float)numberOfTasks;
		return result;
	}
	
	public Task getLastTask(){
		Task[] tasks = getTasks();
		if (tasks.length <= 1){
			return null;
		}
		Task task = tasks[tasks.length - 1];
		return task;
	}
	
	public void removeObjectFromQueue(Task task){
		bq.remove(task);
	}
	public void updateProcessingTime(int updateNumber) {
		Task[] tasks;
		tasks = this.getTasks();
		if (tasks.length > 0) {
			Task task = tasks[0];
			task.setProcessTime(task.getProcessTime() - updateNumber);
			if (task.getProcessTime() <= 0) {
				bq.poll();
			}
		}
	}
}