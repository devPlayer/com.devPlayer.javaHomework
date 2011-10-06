import java.util.ArrayList;

public class Session {
	
	private String data = "";
	private String historicChoice;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void addData(String value) {
		data += value + "_";
	}
	
	public String getHistoricChoice()
	{
		return historicChoice;
	}
	
	public void setHistoricChoice(String value)
	{
		this.historicChoice = value;
	}
	
	
	private ArrayList<Double> getDataInArray() {
		ArrayList<Double> returnArray = new ArrayList<Double>();
		String thisValue = "";
		for (int i=0; i<data.length();i++){
			if (!String.valueOf(data.charAt(i)).contentEquals("_")) {
				thisValue += String.valueOf(data.charAt(i));
			} else {
				returnArray.add(Double.parseDouble(thisValue));
				thisValue = "";
			}
		}
		return returnArray;
	}
	
	public Double getMin() {
		ArrayList<Double> myArrayList = this.getDataInArray();
		Double min = myArrayList.get(0);
		for (int i=0; i<myArrayList.size();i++)
		{
			if (myArrayList.get(i) < min) {
				min = myArrayList.get(i);
			}
		}
		return min;
	}
	
	public Double getMax() {
		ArrayList<Double> myArrayList = this.getDataInArray();
		Double max = myArrayList.get(0);
		for (int i=0; i<myArrayList.size();i++)
		{
			if (myArrayList.get(i) > max) {
				max = myArrayList.get(i);
			}
		}
		return max;
	}
	
	public Double getMean() {
		ArrayList<Double> myArrayList = this.getDataInArray();
		Double mean = myArrayList.get(0);
		for (int i=1; i<myArrayList.size();i++)
		{
			mean += myArrayList.get(i);
		}
		return mean/myArrayList.size();
	}
}
