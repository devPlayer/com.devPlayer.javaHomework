import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class mainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel historicalChoiceLabel;
	private JRadioButton getHistoricalFromFileRadioButton;
	private JRadioButton inputHistoricalButton;
	private JTextField historicalChoiceTextField;
	private ButtonGroup historicalChoiceButtonGroup;
	
	private JLabel sessionDataLabel;
	private JTextField sessionDataTextField;
	
	private JButton processSession;

	// Session Data //
	@SuppressWarnings("unused")
	private int myFile;
	private Double userHistoricalBackground;
	private String sessionData;
	// Session Data //
	
	public Session ongoingSession = new Session();
	
	public mainWindow()
	{
		super("Radiation Data Log - New Session");
		setLayout(new FlowLayout());
		
		historicalChoiceLabel = new JLabel("Choose how to get the historical background:");
		add(historicalChoiceLabel);
		
		getHistoricalFromFileRadioButton = new JRadioButton("From File");
		add(getHistoricalFromFileRadioButton);
		inputHistoricalButton = new JRadioButton("Manually (ENTER to validate)");
		add(inputHistoricalButton);
		
		getHistoricalFromFileRadioButton.addItemListener(new RadioButtonHandler("from file"));
		inputHistoricalButton.addItemListener(new RadioButtonHandler("from user"));
		
		historicalChoiceButtonGroup = new ButtonGroup();
		historicalChoiceButtonGroup.add(getHistoricalFromFileRadioButton);
		historicalChoiceButtonGroup.add(inputHistoricalButton);
		
		historicalChoiceTextField = new JTextField(2);
		add(historicalChoiceTextField);
		historicalBackgroundTextFieldHandler myHistoricalBackgroundTextFieldHandler = new historicalBackgroundTextFieldHandler();
		historicalChoiceTextField.addActionListener(myHistoricalBackgroundTextFieldHandler);
		
		sessionDataLabel = new JLabel("Enter session data (ENTER to validate): ");
		add(sessionDataLabel);
		sessionDataTextField = new JTextField(2);
		add(sessionDataTextField);
		sessionDataTextFieldHandler mySessionDataTextFieldHandler = new sessionDataTextFieldHandler();
		sessionDataTextField.addActionListener(mySessionDataTextFieldHandler);
		
		processSession = new JButton("Process");
		add(processSession);
		processSessionHandler myProcessSessionHandler = new processSessionHandler();
		processSession.addActionListener(myProcessSessionHandler);
		
	}
		
		private class RadioButtonHandler implements ItemListener
		{
			private String historicChoice;
			public RadioButtonHandler(String choice)
			{
				historicChoice = choice;
			}
			public void itemStateChanged(ItemEvent event)
			{
				ongoingSession.setHistoricChoice(historicChoice);
			}
		}
	
		private class historicalBackgroundTextFieldHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				userHistoricalBackground = Double.parseDouble(event.getActionCommand());
				String myString = String.format("Historical background received as %s", userHistoricalBackground);
				JOptionPane.showMessageDialog(null,myString);
			}
		}
		
		private class sessionDataTextFieldHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				sessionData = event.getActionCommand();

				ongoingSession.addData(sessionData);
				
				String myString = String.format("%s has been added to the ongoing session's data. You can keep adding data, or run the analysis by clicking Process.", sessionData);
				JOptionPane.showMessageDialog(null, myString);
				
				
			}
		}
		
		private class processSessionHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				resultWindow myResultWindow = new resultWindow(ongoingSession, userHistoricalBackground);
				myResultWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				myResultWindow.setSize(640,150);
				myResultWindow.setVisible(true);
				dispose();
			}
		}
		
	}
	
	
	

