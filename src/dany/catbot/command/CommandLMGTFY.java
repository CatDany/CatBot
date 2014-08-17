package dany.catbot.command;

import java.net.URLEncoder;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

public class CommandLMGTFY extends ChatCommand
{
	public CommandLMGTFY()
	{
		super("google", null, EnumPermissionLevel.CHOSEN);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		try
		{
			String nick = query.split(" ", 2)[0];
			String search = query.split(" ", 2)[1];
			String link = "https://www.google.com/#newwindow=1&q=" + URLEncoder.encode(search, "UTF-8");
			e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.FOUND_IT, nick, link));
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}