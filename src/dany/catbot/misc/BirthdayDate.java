package dany.catbot.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthdayDate
{
	private String lastHappyBirthdayYear = "0";
	
	public final String day;
	public final String month;
	public final String year;
	
	private BirthdayDate(String day, String month, String year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public void setHappyBirthdayDone()
	{
		this.lastHappyBirthdayYear = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
	}
	
	public boolean isHappyBirthdayDone()
	{
		return lastHappyBirthdayYear.equals(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())));
	}
	
	public boolean shouldHappyBirthday()
	{
		String dateBirthday = day + "/" + month;
		String dateToday = new SimpleDateFormat("dd/MM").format(new Date(System.currentTimeMillis()));
		return !isHappyBirthdayDone() && dateToday.equals(dateBirthday);
	}
	
	public static BirthdayDate createFromMillis(long millis)
	{
		Date date = new Date(millis);
		String d = new SimpleDateFormat("dd").format(date);
		String m = new SimpleDateFormat("MM").format(date);
		String y = new SimpleDateFormat("yyyy").format(date);
		return new BirthdayDate(d, m, y);
	}
	
	public static BirthdayDate createFromDMY(String d, String m, String y)
	{
		return new BirthdayDate(d, m, y);
	}
	
	/**
	 * Format: dd.MM.yyyy
	 * @param date
	 * @return
	 */
	public static BirthdayDate parse(String date)
	{
		String d = date.split("\\.")[0];
		String m = date.split("\\.")[1];
		String y = date.split("\\.")[2];
		return new BirthdayDate(d, m, y);
	}
}