package dany.catbot.misc;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Charsets;

public class BirthdayDatabase
{
	static
	{
		File bddb = new File("birthday_database.txt");
		if (!bddb.exists())
		{
			try
			{
				bddb.createNewFile();
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
	
	public static final HashMap<String, BirthdayDate> birthdayMap = new HashMap<String, BirthdayDate>();
	
	public static void reloadDatabase()
	{
		try
		{
			birthdayMap.clear();
			File file = new File("birthday_database.txt");
			List<String> list = Files.readAllLines(file.toPath(), Charsets.UTF_8);
			for (String i : list)
			{
				if (i == null || i.isEmpty())
				{
					break;
				}
				String nick = i.split("\\:", 4)[0];
				String d = i.split("\\:", 4)[1];
				String m = i.split("\\:", 4)[2];
				String y = i.split("\\:", 4)[3];
				birthdayMap.put(nick, BirthdayDate.createFromDMY(d, m, y));
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	public static void writeDatabase()
	{
		File file = new File("birthday_database.txt");
		PrintWriter cw;
		try
		{
			cw = new PrintWriter(file);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return;
		}
		Iterator<Entry<String, BirthdayDate>> it = birthdayMap.entrySet().iterator();
		while (it.hasNext())
		{
			Entry<String, BirthdayDate> entry = it.next();
			String nick = entry.getKey();
			BirthdayDate bd = entry.getValue();
			cw.append(nick + ":" + bd.day + ":" + bd.month + ":" + bd.year + "\n");
		}
		cw.close();
	}
}