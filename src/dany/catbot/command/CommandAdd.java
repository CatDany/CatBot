package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

public class CommandAdd extends ChatCommand
{
	public CommandAdd()
	{
		super("add", "{name} {ul} {response}", EnumPermissionLevel.MODERATOR);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		String name;
		String ul;
		String response;
		
		try
		{
			name = query.split(" ", 3)[0];
			ul = query.split(" ", 3)[1];
			response = query.split(" ", 3)[2];
		}
		catch (IndexOutOfBoundsException t)
		{
			e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
			return;
		}
		
		for (ChatCommand i : Settings.COMMANDS)
		{
			if (i.commandName.equalsIgnoreCase(name))
			{
				e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.COMMAND_IS_EXISTING, e.getUser().getNick()));
				return;
			}
		}
		
		Settings.COMMANDS.add(new TextCommand(name, ul, response));
		Settings.writeCommands();
		
		e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.COMMAND_ADDED, e.getUser().getNick(), name, EnumPermissionLevel.parseUserLevel(ul)));
	}
}