import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class historicalData {
	
	private PrintWriter outfile = null;
	private BufferedReader myFile = null;
	private String myTimeStamp;
	@SuppressWarnings("unused")
	private Double myHistoricalBackground;
	
	public historicalData() {
		try 
		{
			outfile = new PrintWriter(new BufferedWriter(new FileWriter("data.txt", true)), true);      
		} catch (IOException e) {
			System.out.println("Error: Could not open or create data.txt");	
		}
	}
	
	private ArrayList<String> getHistoricalDataStringArray(String line)
	{
		ArrayList<String> returnArray = new ArrayList<String>();
		String thisValue = "";
		for (int i=0; i<line.length();i++){
			if (!String.valueOf(line.charAt(i)).contentEquals(";")) {
				thisValue += String.valueOf(line.charAt(i));
			} else {
				returnArray.add(thisValue);
				thisValue = "";
			}
		}
		return returnArray;
	}
	
	public ArrayList<Double> getHistoricalData(Session ongoingSession)
	{
		ArrayList<Double> myReturnArray = new ArrayList<Double>();
		Double min = ongoingSession.getMin();
		Double max = ongoingSession.getMax();
		String myLine = "";
		Double sum = ongoingSession.getMean();
		Double count = 1.0;
		
		try {
			myFile = new BufferedReader(new FileReader("data.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not open or create data.txt");
		}		

			try{
				myLine = myFile.readLine();
			}	catch (IOException e) {
				e.printStackTrace();
			}

			while (myLine != null)
			{
				
				sum += Double.parseDouble(getHistoricalDataStringArray(myLine).get(0));
				count += 1;
				
				if ( Double.parseDouble(getHistoricalDataStringArray(myLine).get(1)) < min )
				{
					min = Double.parseDouble(getHistoricalDataStringArray(myLine).get(1));
				}
				
				if ( Double.parseDouble(getHistoricalDataStringArray(myLine).get(2)) > max )
				{
					max = Double.parseDouble(getHistoricalDataStringArray(myLine).get(2));
				} 
				
				try
				{
					myLine = myFile.readLine();
				} catch (IOException e)
				{
				e.printStackTrace();
				}
			}
			
		myReturnArray.add(sum/count);
		myReturnArray.add(min);
		myReturnArray.add(max);
		return myReturnArray;
		
		}
	
	public Double getStd(Session ongoingSession)
	{
		
		Double myHistoricalBackground = new Double(this.getHistoricalData(ongoingSession).get(0));
		String myLine = "";
		Double std = 0.0;
		Double count = 0.0;
		
		try {
			myFile = new BufferedReader(new FileReader("data.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not open or create data.txt");
		}		

			try{
				myLine = myFile.readLine();
			}	catch (IOException e) {
				e.printStackTrace();
			}

			while (myLine != null)
			{
				
				std += Math.pow(Double.parseDouble(getHistoricalDataStringArray(myLine).get(0)) - myHistoricalBackground, 2) ;
				count += 1;
				
				try
				{
					myLine = myFile.readLine();
				} catch (IOException e)
				{
				e.printStackTrace();
				}
			}
			
			return std/count;
		
	}
	
	public void writeToFile(Session ongoingSession)
	{
		Calendar myCal = Calendar.getInstance();
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		myTimeStamp = mySdf.format(myCal.getTime());	
		outfile.format("%s;%s;%s;%s;%s\n", Double.toString(ongoingSession.getMean()), Double.toString(ongoingSession.getMin()), Double.toString(ongoingSession.getMax()), myTimeStamp, ongoingSession.getData());
		// ^ format is ;mean;min;max;timestamp
		outfile.close();
	}
}
