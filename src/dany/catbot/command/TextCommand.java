package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Settings;

public class TextCommand extends ChatCommand
{
	public final String response;
	
	public TextCommand(String name, String ul, String response)
	{
		super(name, null, EnumPermissionLevel.parseUserLevel(ul));
		this.response = response;
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		String reply;
		
		if (response.contains("%s") && query != null && !query.equals(""))
		{
			reply = String.format(response, query);
		}
		else
		{
			reply = response;
		}
		
		e.getBot().sendIRC().message(Settings.CHANNEL, reply);
	}
}