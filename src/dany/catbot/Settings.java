package dany.catbot;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;

import dany.catbot.command.ChatCommand;
import dany.catbot.command.TextCommand;

public class Settings
{
	public static List<ChatCommand> COMMANDS = new ArrayList<ChatCommand>();
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
				String key = i.split("=", 2)[0];
				String value = i.split("=", 2)[1];
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
					CHANNEL = "#" + value;
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
			reloadChosen();
			reloadCommands();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			System.err.println("Unable to load settings!");
			System.err.println("Terminating..");
			System.exit(-1);
		}
	}
	
	public static boolean reloadChosen()
	{
		try
		{
			File fileChosen = new File("chosen.txt");
			CHOSEN = Files.readAllLines(fileChosen.toPath(), Charsets.UTF_8);
			return true;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return false;
		}
	}
	
	public static boolean reloadCommands()
	{
		try
		{
			COMMANDS.clear();
			File file = new File("commands.txt");
			List<String> list = Files.readAllLines(file.toPath(), Charsets.UTF_8);
			if (!list.isEmpty())
			{
				list.set(0, list.get(0).substring(1));
			}
			for (String i : list)
			{
				if (i == null || i.isEmpty())
				{
					break;
				}
				String name = i.split("\\:", 3)[0];
				String ul = i.split("\\:", 3)[1];
				String response = i.split("\\:", 3)[2];
				new TextCommand(name, ul, response).register();
			}
			return true;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return false;
		}
		finally
		{
			Main.handler.addSystemCommands();
		}
	}
	
	public static void writeCommands()
	{
		File file = new File("commands.txt");
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
		for (ChatCommand i : COMMANDS)
		{
			if (i instanceof TextCommand)
			{
				TextCommand cmd = (TextCommand)i;
				String name = cmd.commandName;
				String ul = cmd.commandPermission.ul;
				String response = cmd.response;
				cw.append(name + ":" + ul + ":" + response + "\n");
			}
		}
		cw.close();
	}
}