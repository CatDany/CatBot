package dany.catbot;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import dany.catbot.command.ChatCommand;
import dany.catbot.command.TextCommand;

public class Settings
{
	public static final Charset CHARSET = Charset.defaultCharset();
	public static final String[] REQUESTED_PROPERTIES = new String[]
			{
				"name",
				"oauth_token",
				"channel",
				"broadcaster_name",
				"locale",
				"auto_message_timeout",
				"timezone",
				"quote_author",
				"timeout_in_sec"
			};
	
	public static List<ChatCommand> COMMANDS = new ArrayList<ChatCommand>();
	public static List<String> CHOSEN = new ArrayList<String>();
	
	public static String NAME;
	public static String OAUTH_TOKEN;
	public static String CHANNEL;
	public static String BROADCASTER_NAME;
	public static String LOCALE;
	public static String AUTO_MESSAGE_TIMEOUT;
	public static String TIMEZONE;
	public static String QUOTE_AUTHOR;
	public static String TIMEOUT_IN_SEC;
	
	public static void init()
	{
		try
		{
			File fileSettings = new File("settings.properties");
			List<String> listSettings = Files.readAllLines(fileSettings.toPath(), Charset.defaultCharset());
			for (String i : listSettings)
			{
				if (i.equals(""))
				{
					continue;
				}
				String key = i.split("=", 2)[0];
				String value = i.split("=", 2)[1];
				Field f = Settings.class.getField(key.toUpperCase());
				f.set(null, value);
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
			CHOSEN = Files.readAllLines(fileChosen.toPath(), Settings.CHARSET);
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
			List<String> list = Files.readAllLines(file.toPath(), Settings.CHARSET);
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