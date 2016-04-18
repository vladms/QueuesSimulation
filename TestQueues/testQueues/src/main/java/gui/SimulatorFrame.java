package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Task;


public class SimulatorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	
	public SimulatorFrame(){
		panel = new JPanel();
		panel.setBounds(0, 0, 1280, 720);
		setLayout(null);
		panel.setLayout(null);
		add(panel);
		setSize(1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void displayData(Task[][] tasks) {
		panel.removeAll();
		panel.revalidate();
		int X = 10;
		for (int index = 0; index < tasks.length; ++index) {
			JLabel serverLabel = new JLabel("Queue: " + index);
			serverLabel.setBackground(Color.RED);
			serverLabel.setBounds(X + 30, 0, 120, 50);
			panel.add(serverLabel);
			JList<Task> tasksList = new JList<Task>(tasks[index]);
			tasksList.setBounds(0, 0, 50, 500);
			JScrollPane scrollPane = new JScrollPane(tasksList);
			scrollPane.setBounds(X, 60, 120, 150);
			X += 150;
			panel.add(scrollPane);
		}
		panel.repaint();
		panel.revalidate();
	}
	
	public void displayData(Task[] tasks){

		panel.removeAll();
		panel.revalidate();

		JList<Task> tasksList = new JList<Task>(tasks);
		tasksList.setBounds(0, 60, 100, 500);
		JScrollPane scrollPane = new JScrollPane(tasksList);
		scrollPane.setBounds(0, 60, 100, 500);
		JLabel serverLabel = new JLabel();
		serverLabel.setBackground(Color.RED);
		serverLabel.setBounds(0, 0, 50, 50);
		panel.add(serverLabel);
		panel.add(scrollPane);
		panel.repaint();
		panel.revalidate();
	}
}
