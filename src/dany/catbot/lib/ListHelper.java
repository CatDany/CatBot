package dany.catbot.lib;

import java.util.List;

public class ListHelper
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
}