package dany.catbot.auto;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.command.EnumPermissionLevel;

public abstract class AutoTrigger
{
	public final String[] startsWith;
	
	public AutoTrigger(String... startsWith)
	{
		this.startsWith = startsWith;
	}
	
	public abstract void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm);
}