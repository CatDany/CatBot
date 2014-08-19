package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.lib.Helper;
import dany.catbot.misc.BirthdayDatabase;
import dany.catbot.misc.BirthdayDate;

public class CommandBirthday extends ChatCommand
{
	public CommandBirthday()
	{
		super("birthday", "dd.MM.yyyy", EnumPermissionLevel.USER);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		if (query == null || query.equals(""))
		{
			Helper.send(e, Localization.get(Localization.INTRODUCING_BIRTHDAY));
			return;
		}
		else if (!query.matches("^[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}$"))
		{
			Helper.send(e, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
			return;
		}
		
		if (BirthdayDatabase.birthdayMap.containsKey(e.getUser().getNick()))
		{
			Helper.send(e, Localization.get(Localization.BIRTHDAY_IS_ALREADY_SET, e.getUser().getNick()));
		}
		else
		{
			BirthdayDatabase.birthdayMap.put(e.getUser().getNick(), BirthdayDate.parse(query));
			BirthdayDatabase.writeDatabase();
			Helper.send(e, Localization.get(Localization.BIRTHDAY_SET, e.getUser().getNick()));
		}
	}
}