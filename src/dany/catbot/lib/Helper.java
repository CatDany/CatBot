package dany.catbot.lib;

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
			str += separator;
		}
		str = str.substring(0, str.length() - separator.length());
		return str;
	}
}