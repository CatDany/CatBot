package dany.catbot.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Charsets;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.lib.Helper;

public class CommandQuote extends ChatCommand
{
	private static final Random random = new Random();
	
	public CommandQuote()
	{
		super("quote", "[ add {quote} ]", EnumPermissionLevel.USER);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		File file = new File("quotes.txt");
		if (!file.exists())
		{
			Helper.send(e, Localization.get(Localization.QUOTES_NOT_ENABLED));
			return;
		}
		List<String> allQuotes = null;
		try
		{
			allQuotes = Files.readAllLines(file.toPath(), Charsets.UTF_8);
			if (allQuotes.contains(""))
			{
				allQuotes.remove("");
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		
		if ((query == null || query.equals("")) && allQuotes.size() > 0)
		{
			String quote = allQuotes.get(random.nextInt(allQuotes.size()));
			Helper.send(e, quote);
		}
		else if (query.matches("^add .+$"))
		{
			String quote = query.substring(4);
			Date date = Calendar.getInstance(TimeZone.getTimeZone(Settings.TIMEZONE)).getTime();
			String timeanddate = new SimpleDateFormat("dd.MM.yyyy hh:mm").format(date);
			String line = quote + " (c) " + Settings.QUOTE_AUTHOR + " @ " + timeanddate + " " + Settings.TIMEZONE;
			try
			{
				PrintWriter pw = new PrintWriter(file);
				for (String i : allQuotes)
				{
					pw.append(i + "\n");
				}
				pw.append(line + "\n");
				pw.close();
				Helper.send(e, Localization.get(Localization.QUOTE_ADDED, e.getUser().getNick()));
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
		else
		{
			Helper.send(e, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
		}
	}
}