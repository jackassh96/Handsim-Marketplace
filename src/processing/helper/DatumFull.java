package processing.helper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class DatumFull implements Serializable, Comparable<DatumFull> {

	
	private String day; // --> dd (Bsp. 07)
	private String month;// --> mm (Bsp. 09)
	private String year; // --> yyyy (Bsp 1996)
	
	private String hours;
	private String minutes;
	private String seconds;
	static final private HashMap<Integer, Integer> monthDayMap = new  HashMap<Integer, Integer>();
	static{
	    monthDayMap.put(1,31);
	    monthDayMap.put(2,28);
	    monthDayMap.put(3, 31);
	    monthDayMap.put(4, 30);
	    monthDayMap.put(5, 31);
	    monthDayMap.put(6, 30);
	    monthDayMap.put(7, 31);
	    monthDayMap.put(8, 31);
	    monthDayMap.put(9, 30);
	    monthDayMap.put(10, 31);
	    monthDayMap.put(11, 30);
	    monthDayMap.put(12, 31);
	}
	
	// default constructor to create Date with current time
	public DatumFull() {
		long unitime = System.currentTimeMillis();	
		Date currentTime = new Date(unitime);
		
		//day
		SimpleDateFormat formatterDay = new SimpleDateFormat("dd");//"yyyy.MM.dd - HH:mm:ss");
		this.day = formatterDay.format(currentTime);
		//month 
		SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");//"yyyy.MM.dd - HH:mm:ss");
		this.month = formatterMonth.format(currentTime);
		//year
		SimpleDateFormat formatterYear = new SimpleDateFormat( "yyyy");//"yyyy.MM.dd - HH:mm:ss");
		this.year = formatterYear.format(currentTime);
		
		//hours
		SimpleDateFormat formatterHours = new SimpleDateFormat("HH");//"yyyy.MM.dd - HH:mm:ss");
		this.hours = formatterHours.format(currentTime);
		//minutes 
		SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");//"yyyy.MM.dd - HH:mm:ss");
		this.minutes = formatterMinutes.format(currentTime);
		//seconds
		SimpleDateFormat formatterSeconds = new SimpleDateFormat( "ss");//"yyyy.MM.dd - HH:mm:ss");
		this.seconds = formatterSeconds.format(currentTime);
	}
	
	// constructor to create specific date
	public DatumFull(String Tag, String Monat, String Jahr, String Stunde, String Minute, String Sekunde) throws Exception {
		
		try {
			int tag = Integer.parseInt(Tag);
			int monat = Integer.parseInt(Monat);
			int jahr = Integer.parseInt(Jahr);
			int stunde = Integer.parseInt(Stunde);
			int minute = Integer.parseInt(Minute);
			int sekunde = Integer.parseInt(Sekunde);
			
			if (tag < 1 || tag > 31 || monat < 1 || monat > 12 || jahr < 0 || jahr > 9999 || stunde > 23 || stunde < 0 || minute < 0 || minute > 59 || sekunde < 0 || sekunde > 59) throw new Exception();
			
			this.day = Tag;
			this.month = Monat;
			this.year = Jahr;
			this.hours = Stunde;
			this.minutes = Minute;
			this.seconds = Sekunde;
			
			
		}
		catch (Exception ex) {
			throw new Exception("\nEs ist ein Fehler aufgetreten, überprüfen Sie die übergebenen Werte!" +
					"\nBitte halten Sie sich an folgendes Schema:\n" +
					"Tag --> DD (1-31) \n" +
					"Monat --> MM (1-12) \n" +
					"Jahr --> YYYY (0-9999) \n" +
					"\n" +
					"Stunde --> hh (0-23) \n" +
					"Minute --> mm (0-59) \n" +
					"Sekunde --> ss (0-59)");
		}
	}
	
	public DatumFull(String Tag, String Monat, String Jahr) throws Exception {
		
		try {
			int tag = Integer.parseInt(Tag);
			int monat = Integer.parseInt(Monat);
			int jahr = Integer.parseInt(Jahr);
			int stunde = 12;
			int minute = 12;
			int sekunde = 12;
			
			if (tag < 1 || tag > 31 || monat < 1 || monat > 12 || jahr < 0 || jahr > 9999 || stunde > 23 || stunde < 0 || minute < 0 || minute > 59 || sekunde < 0 || sekunde > 59) throw new Exception("\nEs ist ein Fehler aufgetreten, überprüfen Sie die übergebenen Werte! \nBitte halten Sie sich an folgendes Schema: \nTag --> DD (1-31) \nMonat --> MM (1-12) \nJahr --> YYYY (0-9999) \n Stunde --> hh (0-23) \nMinute --> mm (0-59) \nSekunde --> ss (0-59)");
			
			this.day = Tag;
			this.month = Monat;
			this.year = Jahr;
			this.hours = new String("12");
			this.minutes = new String("12");
			this.seconds = new String("12");
			
			
		}
		catch (Exception ex) {
			throw new Exception("\nEs ist ein Fehler aufgetreten, überprüfen Sie die übergebenen Werte!" +
					"\nBitte halten Sie sich an folgendes Schema:\n" +
					"Tag --> DD (1-31) \n" +
					"Monat --> MM (1-12) \n" +
					"Jahr --> YYYY (0-9999) \n" +
					"\n" +
					"Stunde --> hh (0-23) \n" +
					"Minute --> mm (0-59) \n" +
					"Sekunde --> ss (0-59)");
		}
	}
	
	public DatumFull(String completeString) {
		String [] date = completeString.split("\\.");
		this.year = date[0];
		this.month = date[1];
		this.day = date [2];
	}
	
	// check if date is within time period
	public static boolean isInTime(DatumFull datum, DatumFull datumMin, DatumFull datumMax) {
		boolean flag = false;
		boolean flag2 = false;
		
		if (Long.parseLong(datum.getYear()) > Long.parseLong(datumMin.getYear())) {
			flag = true;
		}
		else { if (Long.parseLong(datum.getYear()) == Long.parseLong(datumMin.getYear())) {
			
					if (Long.parseLong(datum.getMonth()) > Long.parseLong(datumMin.getMonth())) {
						flag = true;
					}
					else { if (Long.parseLong(datum.getMonth()) == Long.parseLong(datumMin.getMonth())) {
						
								if (Long.parseLong(datum.getDay()) > Long.parseLong(datumMin.getDay())) {
					
									flag = true;
								}
								else	{if (Long.parseLong(datum.getDay()) == Long.parseLong(datumMin.getDay())) {
											flag = true;
											}
											else return false;
								}
							}
					}
				}
		}
		if (Long.parseLong(datum.getYear()) < Long.parseLong(datumMax.getYear())) {
			flag2 = true;
		}
		else { if (Long.parseLong(datum.getYear()) == Long.parseLong(datumMax.getYear())) {
			
					if (Long.parseLong(datum.getMonth()) < Long.parseLong(datumMax.getMonth())) {
						flag2 = true;
					}
					else { if (Long.parseLong(datum.getMonth()) == Long.parseLong(datumMax.getMonth())) {
						
								if (Long.parseLong(datum.getDay()) < Long.parseLong(datumMax.getDay())) {
					
									flag2 = true;
								}
								else	{if (Long.parseLong(datum.getDay()) == Long.parseLong(datumMax.getDay())) {
											flag2 = true;
											}
											else return false;
								}
							}
					}
				}
		}
		if (!flag) return false;
		if (!flag2) return false;
		return true;
		
	}
	
	public String toString() {
		return (this.day + "." + this.month + "." + this.year);
	}
	
	public String toMachineString() {
		return (this.year + "." + this.month + "." + this.day);
	}
	
	public String toDetailedString() {
		return (this.day + "." + this.month + "." + this.year + " - " + this.hours + ":" + this.minutes + ":" + this.seconds);
	}
	
	public void setDay(String day) {
		this.day = day;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getHours() {
		return hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public String getSeconds() {
		return seconds;
	}

	@Override
	public int compareTo(DatumFull date) {
		if(Integer.parseInt(date.getYear()) == Integer.parseInt(getYear())){
			if(Integer.parseInt(date.getMonth()) == Integer.parseInt(getMonth())){
				if(Integer.parseInt(date.getDay()) == Integer.parseInt(getDay())){
					return 0;
				}else if(Integer.parseInt(date.getDay()) < Integer.parseInt(getDay())){
					return 1;
				}else{
					return -1;
				}
			}else if(Integer.parseInt(date.getMonth()) < Integer.parseInt(getMonth())){
				return 1;
			}else{
				return -1;
			}
		}else if(Integer.parseInt(date.getYear()) < Integer.parseInt(getYear())){
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * Subtracting the given number from the date (without considering leap-years)
	 * @param minusDays to decrease the date with
	 */
	public void minusDays(int minusDays){
		int temp =(Integer.parseInt(day) - minusDays);
		while(temp <= 0){
			month =""+ (Integer.parseInt(month) - 1);
			if(month.equals("0")){
				year = ""+ (Integer.parseInt(year)-1);
				month = "12";
			}
			temp = monthDayMap.get(Integer.parseInt(month)) + temp;
		}
		day = ""+ temp;
	}
}
