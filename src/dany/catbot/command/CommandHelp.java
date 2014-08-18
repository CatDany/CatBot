package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

public class CommandHelp extends ChatCommand
{
	public CommandHelp()
	{
		super("help", "{command}", EnumPermissionLevel.USER);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		if (query == null || query.equals(""))
		{
			e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
			return;
		}
		
		for (ChatCommand i : Settings.COMMANDS)
		{
			if (i.commandName.equals(query))
			{
				if (i.commandUsage != null)
				{
					e.getBot().sendIRC().message(Settings.CHANNEL, e.getUser().getNick() + " --> " + "!" + i.commandName + " " + i.commandUsage + " [" + i.commandPermission + "]");
				}
				else
				{
					e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.NO_USAGE_FOUND, e.getUser().getNick(), query));
				}
				return;
			}
		}
		e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.NO_SUCH_COMMAND, e.getUser().getNick(), query));
	}
}