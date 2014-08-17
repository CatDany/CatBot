package dany.catbot;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Charsets;

public class Localization
{
	public static final HashMap<String, String> localizationMap = new HashMap<String, String>();
	
	public static final String BOT_JOINED = "bot_joined";
	public static final String NO_SUCH_COMMAND = "no_such_command";
	public static final String WRONG_ARGUMENTS = "wrong_arguments";
	public static final String COMMAND_IS_EXISTING = "command_is_existing";
	public static final String COMMAND_ADDED = "command_added";
	public static final String COMMAND_DELETED = "command_deleted";
	public static final String RELOAD_SUCCESS = "reload_success";
	public static final String RELOAD_FAIL = "reload_fail";
	public static final String URL_SHORTENERS_BANNED = "url_shorteners_banned";
	public static final String CAPS_BANNED = "caps_banned";
	public static final String COLOR_CHANGED = "color_changed";
	public static final String FOUND_IT = "found_it";
	public static final String YOUTUBE_INFO = "youtube_info";
	
	public static String get(String key, Object... args)
	{
		if (args.length == 0)
		{
			return localizationMap.get(key);
		}
		else
		{
			return String.format(localizationMap.get(key), args);
		}
	}
	
	public static void init()
	{
		try
		{
			String localeKey = Settings.LOCALE;
			File file = new File(String.format("lang\\%s.lang", localeKey));
			List<String> list = Files.readAllLines(file.toPath(), Charsets.UTF_8);
			if (!list.isEmpty())
			{
				list.set(0, list.get(0).substring(1));
			}
			for (String i : list)
			{
				String key = i.split("=", 2)[0];
				String value = i.split("=", 2)[1];
				localizationMap.put(key, value);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}