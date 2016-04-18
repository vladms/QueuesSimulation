package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Task;

public class MainFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;

	private AppInterfaceButtonEvents buttonEventHandler;

	private JFrame simulatorFrame;
	private JPanel inputPanel;
	private JPanel controlPanel;
	private JPanel outputPanel;

	private JTextField minArrivingTimeTextField;
	private JTextField maxArrivingTimeTextField;

	private JTextField minServiceTimeTextField;
	private JTextField maxServiceTimeTextField;

	private JTextField numberOfQueuesTextField;
	private JTextField numberOfClientsTextField;

	private JTextField simulationIntervalTextField;

	private JButton startSimulationButton;

	public MainFrame(AppInterfaceButtonEvents buttonEventHandler) {
		this.buttonEventHandler = buttonEventHandler;

		this.prepareGUI();
		this.placeButtons();
	}

	public void prepareGUI() {
		setSize(1200, 1200);
		setLayout(new GridLayout(3, 3));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(8, 1));

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 3));

		outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(1, 1));

		add(inputPanel);

		add(controlPanel);

		add(outputPanel);

		setVisible(true);
	}

	private void placeButtons() {

		minArrivingTimeTextField = new JTextField();
		maxArrivingTimeTextField = new JTextField();

		minServiceTimeTextField = new JTextField();
		maxServiceTimeTextField = new JTextField();

		numberOfQueuesTextField = new JTextField();
		numberOfClientsTextField = new JTextField();

		simulationIntervalTextField = new JTextField();

		startSimulationButton = new JButton();

		startSimulationButton.setText("Start simulation!");

		minArrivingTimeTextField.setText("minArrivingTime");
		maxArrivingTimeTextField.setText("maxArrivingTime");

		minServiceTimeTextField.setText("minServiceTime");
		maxServiceTimeTextField.setText("maxServiceTime");

		numberOfQueuesTextField.setText("numberOfQueues");
		numberOfClientsTextField.setText("numberOfClients");

		simulationIntervalTextField.setText("simulationInterval");

		minArrivingTimeTextField.setText("1");
		maxArrivingTimeTextField.setText("10");

		minServiceTimeTextField.setText("3");
		maxServiceTimeTextField.setText("30");

		numberOfQueuesTextField.setText("5");
		numberOfClientsTextField.setText("10");

		simulationIntervalTextField.setText("100");

		inputPanel.add(minArrivingTimeTextField);
		inputPanel.add(maxArrivingTimeTextField);
		inputPanel.add(minServiceTimeTextField);
		inputPanel.add(maxServiceTimeTextField);
		inputPanel.add(numberOfQueuesTextField);
		inputPanel.add(numberOfClientsTextField);
		inputPanel.add(simulationIntervalTextField);
		inputPanel.add(startSimulationButton);

		startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonEventHandler.buttonTouched(minArrivingTimeTextField.getText(), maxArrivingTimeTextField.getText(),
						minServiceTimeTextField.getText(), maxServiceTimeTextField.getText(),
						numberOfQueuesTextField.getText(), numberOfClientsTextField.getText(),
						simulationIntervalTextField.getText());
			}
		});

		setVisible(true);
	}

	public void prepareForSimulation() {
		panel = new JPanel();
		panel.setBounds(0, 0, 1200, 1200);
		panel.setBackground(Color.RED);
		simulatorFrame = new JFrame();
		simulatorFrame.setBounds(0, 0, 1200, 1200);
		simulatorFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		simulatorFrame.setLayout(null);
		simulatorFrame.add(panel);
		JButton button = new JButton("ASDASDASDASDASD");
		button.setBounds(100, 100, 100, 100);

		simulatorFrame.add(button);

		simulatorFrame.setVisible(true);
	}

	public void showErrorMessage(String errorMesage) {
		JOptionPane.showMessageDialog(this, errorMesage);
	}

	public void actionPerformed(ActionEvent e) {

	}

	

}
