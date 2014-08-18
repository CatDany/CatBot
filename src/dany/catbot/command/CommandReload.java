package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.lib.Helper;

public class CommandReload extends ChatCommand
{
	public CommandReload()
	{
		super("reload", null, EnumPermissionLevel.MODERATOR);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		boolean b0 = Settings.reloadChosen() && Settings.reloadCommands();
		if (b0)
		{
			Helper.send(e, Localization.get(Localization.RELOAD_SUCCESS, e.getUser().getNick()));
		}
		else
		{
			Helper.send(e, Localization.get(Localization.RELOAD_FAIL, e.getUser().getNick()));
		}
	}
}