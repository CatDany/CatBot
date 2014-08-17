package dany.catbot.lib;

import java.util.List;

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
}