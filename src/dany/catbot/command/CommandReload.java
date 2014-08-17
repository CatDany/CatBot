package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

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
			e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.RELOAD_SUCCESS, e.getUser().getNick()));
		}
		else
		{
			e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.RELOAD_FAIL, e.getUser().getNick()));
		}
	}
}