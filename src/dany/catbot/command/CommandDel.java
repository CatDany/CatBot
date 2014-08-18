package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.lib.Helper;

public class CommandDel extends ChatCommand
{
	public CommandDel()
	{
		super("del", "{name}", EnumPermissionLevel.MODERATOR);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		if (query == null || query.equals(""))
		{
			Helper.send(e, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
			return;
		}
		
		for (int i = 0; i < Settings.COMMANDS.size(); i++)
		{
			ChatCommand j = Settings.COMMANDS.get(i);
			if (j instanceof TextCommand)
			{
				TextCommand cmd = (TextCommand)j;
				if (cmd.commandName.equalsIgnoreCase(query))
				{
					Settings.COMMANDS.remove(i);
					Settings.writeCommands();
					Helper.send(e, Localization.get(Localization.COMMAND_DELETED, e.getUser().getNick(), query));
					return;
				}
			}
		}
		
		Helper.send(e, Localization.get(Localization.NO_SUCH_COMMAND, e.getUser().getNick(), query));
	}
}