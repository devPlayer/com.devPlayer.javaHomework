// Hery Ratsimihah
// Java Homework Assignment #2
// Version 1.0
// resultWindow.java

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class resultWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel historicalChoiceLabel;
	private JLabel historicalValueLabel;
	private JLabel averageStatisticsLabel;
	private JLabel sessionStatisticsLabel;
	private JLabel alertLabel;
	
	private JButton closeJFrameAndStartNewSession;
	
	private String historicalChoice;		// default value
	private double historicalBackground;
	
	// if historical background is calculated
	private double averageMin;
	private double averageMax;
	private double averageStandardDeviation;
	
	private Session mySession;
	private historicalData myHistoricalData = new historicalData();
	
	private String alertMessage = "";
	
	public resultWindow(Session ongoingSession, Double userHBackground)
	{
		super("Results");
		setLayout(new FlowLayout());
		
		mySession = ongoingSession;
		
		averageMin = myHistoricalData.getHistoricalData(mySession).get(1);
		averageMax = myHistoricalData.getHistoricalData(mySession).get(2);
		averageStandardDeviation = myHistoricalData.getStd(mySession);
		
		historicalChoice = mySession.getHistoricChoice();		// is either "from file" or "from user"
		
		
		if (historicalChoice.contentEquals("from file"))
		{
			historicalBackground = myHistoricalData.getHistoricalData(mySession).get(0);
			historicalChoiceLabel = new JLabel(String.format("Historical Background: %s.", historicalBackground));
			historicalValueLabel = new JLabel(String.format("It was obtained %s.", historicalChoice));
			add(historicalChoiceLabel);
			add(historicalValueLabel);
			averageStatisticsLabel = new JLabel(String.format("Average minimum: %s, average maximum: %s, standard deviation:%s." , averageMin, averageMax, averageStandardDeviation));
			add(averageStatisticsLabel);	// display only if historical background was calculated
			if (ongoingSession.getMean() > historicalBackground + 2*averageStandardDeviation)
			{
				alertMessage = "Warning, the ongoing mean is two standard deviations higher than the calculated historic mean!";
				alertLabel = new JLabel(String.format("%s", alertMessage));
			}
		} else {
			historicalBackground = userHBackground;
			historicalChoiceLabel = new JLabel(String.format("Historical Background: %s.", historicalBackground));
			historicalValueLabel = new JLabel(String.format("It was obtained %s.", historicalChoice));
			add(historicalChoiceLabel);
			add(historicalValueLabel);
			if (ongoingSession.getMean() > historicalBackground*1.10)
			{
				alertMessage = "Warning, the ongoing mean is higher by 10% or more than the manually entered historic mean!";
				alertLabel = new JLabel(String.format("%s", alertMessage));
			}
				
		}
		
		sessionStatisticsLabel = new JLabel(String.format("Session minimum: %s, session maximum: %s, session average: %s." , ongoingSession.getMin(), ongoingSession.getMax(), ongoingSession.getMean()));
		add(sessionStatisticsLabel);
		
		if (!alertMessage.contentEquals(""))
		{
			add(alertLabel);
		}
	
		closeJFrameAndStartNewSession = new JButton("Write Data, Close Current Session, and Start New Session");
		add(closeJFrameAndStartNewSession);
		closeJFrameAndStartNewSessionHandler mycloseJFrameAndStartNewSessionHandler = new closeJFrameAndStartNewSessionHandler();
		closeJFrameAndStartNewSession.addActionListener(mycloseJFrameAndStartNewSessionHandler);
	}
	
	private class closeJFrameAndStartNewSessionHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{
			myHistoricalData.writeToFile(mySession);
			dispose();
			mainWindow myMainWindow = new mainWindow();
			myMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			myMainWindow.setSize(300,215);
			myMainWindow.setVisible(true); 
			
		}
		
	}

}
