package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Settings;
import dany.catbot.lib.Helper;

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
		String reply = response;
		
		if (response.contains("%s") && query != null && !query.equals(""))
		{
			reply = String.format(reply, query);
		}
		if (response.contains("$u"))
		{
			reply = reply.replace("$u", e.getUser().getNick());
		}
		if (response.contains("$b"))
		{
			reply = reply.replace("$b", Settings.BROADCASTER_NAME);
		}
		
		Helper.send(e,  reply);
	}
}