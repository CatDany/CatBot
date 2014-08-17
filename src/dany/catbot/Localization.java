package dany.catbot;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Charsets;

public class Localization
{
	public static final HashMap<String, String> localizationMap = new HashMap<String, String>();
	
	public static String BOT_JOINED = "bot_joined";
	public static String NO_SUCH_COMMAND = "no_such_command";
	public static String WRONG_ARGUMENTS = "wrong_arguments";
	
	public static String get(String key, Object... args)
	{
		return String.format(localizationMap.get(key), args);
	}
	
	public static void init()
	{
		try
		{
			String localeKey = Settings.LOCALE;
			File file = new File(String.format("lang\\%s.lang", localeKey));
			List<String> list = Files.readAllLines(file.toPath(), Charsets.UTF_8);
			for (String i : list)
			{
				String key = i.split("=")[0];
				String value = i.split("=")[1];
				localizationMap.put(key, value);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}