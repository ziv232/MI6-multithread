package bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	private static class DiaryHolder{
		private static Diary instance = new Diary();
	}
	private List<Report> reports;
	private AtomicInteger total;
	//Constructor
	private Diary(){
		this.reports = new ArrayList<>();
		this.total = new AtomicInteger(0);
	}



	/**
	 * Retrieves the single instance of this class.
	 */
	public static Diary getInstance() {
		return DiaryHolder.instance;
	}

	public List<Report> getReports() {

		return this.reports;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public synchronized void addReport(Report reportToAdd){
		reports = getReports();
		reports.add(reportToAdd);//Check if add returns boolean


	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		DiaryOutPut outPut = new DiaryOutPut(reports,total.get());
		try {
			FileWriter writer = new FileWriter(filename);
			Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
			gson.toJson(outPut, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){
		return total.get();
	}

	public void incrementTotal(){
//		int current = getTotal();
//		int next = current+1;
//		if(total.compareAndSet(current,next)){
//		}
//		else{
//			this.incrementTotal();
//		}
		int val;
		do{
			val=total.get();
		}while (!total.compareAndSet(val,val+1));
//		total.incrementAndGet();

	}
}
