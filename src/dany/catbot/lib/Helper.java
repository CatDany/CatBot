package dany.catbot.lib;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import org.pircbotx.hooks.Event;
import org.pircbotx.output.OutputIRC;

import dany.catbot.Settings;

public class Helper
{
	public static boolean containsIgnoreCase(List<String> list, String str)
	{
		for (String i : list)
		{
			if (str.equalsIgnoreCase(i))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsIgnoreCase(String string, String str)
	{
		return string.toLowerCase().contains(str);
	}
	
	public static void send(OutputIRC irc, String message)
	{
		irc.message(Settings.CHANNEL, message);
	}
	
	public static void send(Event e, String message)
	{
		send(e.getBot().sendIRC(), message);
	}
	
	public static String arrayToString(String separator, String... array)
	{
		String str = "";
		for (String i : array)
		{
			str += i + separator;
		}
		str = str.substring(0, str.length() - separator.length());
		return str;
	}
	
	public static void createFiles()
	{
		String[] dirnames = new String[]
				{
					"lang"
				};
		String[] filenames = new String[]
				{
					"automessages.txt.disabled",
					"birthday_database.txt",
					"chosen.txt",
					"commands.txt",
					"currentsong.txt.disabled",
					"quotes.txt.disabled",
					"settings.properties",
					"lang\\" + Settings.LOCALE + ".lang"
				};
		// Creating Blank Folders and Files
		try
		{
			for (String i : dirnames)
			{
				File f = new File(i);
				if (!f.exists() || !f.isDirectory())
				{
					f.mkdirs();
				}
			}
			for (String i : filenames)
			{
				if (i.endsWith(".disabled") && new File(i.substring(0, i.length() - 9)).exists())
				{
					continue;
				}
				File f = new File(i);
				if (!f.exists() || !f.isFile())
				{
					f.createNewFile();
				}
			}
		}
		catch (Throwable t)
		{
			System.err.println("Unable to create required folders and files.");
			t.printStackTrace();
		}
		// Writing to 'settings.properties'
		try
		{
			File file = new File("settings.properties");
			List<String> list = Files.readAllLines(file.toPath(), Settings.CHARSET);
			if (list.isEmpty())
			{
				PrintWriter spw = new PrintWriter(file);
				for (String i : Settings.REQUESTED_PROPERTIES)
				{
					spw.println(i + "=");
				}
				spw.close();
				System.out.println("Configure settings of bot in 'settings.properties' file.");
				System.exit(0);
			}
		}
		catch (Throwable t)
		{
			System.err.println("Unable to read from or write to 'settings.properties' file!");
			t.printStackTrace();
		}
	}
}