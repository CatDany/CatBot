package dany.catbot;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Charsets;

import dany.catbot.command.ChatCommand;

public class Settings
{
	public static final HashMap<String, ChatCommand> COMMANDS = new HashMap<String, ChatCommand>();
	public static List<String> CHOSEN = new ArrayList<String>();
	
	public static String NAME;
	public static String OAUTH_TOKEN;
	public static String CHANNEL;
	public static String BROADCASTER_NAME;
	public static String LOCALE;
	
	public static void init()
	{
		try
		{
			File fileSettings = new File("settings.properties");
			List<String> listSettings = Files.readAllLines(fileSettings.toPath(), Charsets.UTF_8);
			for (String i : listSettings)
			{
				String key = i.split("=")[0];
				String value = i.split("=")[1];
				if ("name".equals(key))
				{
					NAME = value;
				}
				else if ("oauth_token".equals(key))
				{
					OAUTH_TOKEN = value;
				}
				else if ("channel".equals(key))
				{
					CHANNEL = value;
				}
				else if ("broadcaster_name".equals(key))
				{
					BROADCASTER_NAME = value;
				}
				else if ("locale".equals(key))
				{
					LOCALE = value;
				}
			}
			File fileChosen = new File("chosen.txt");
			CHOSEN = Files.readAllLines(fileChosen.toPath(), Charsets.UTF_8);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}