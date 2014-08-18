package dany.catbot.command;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

public class CommandList extends ChatCommand
{
	public final List listObj;
	public final String listName;
	
	public CommandList(String name, List list, String listName)
	{
		super(name, null, EnumPermissionLevel.USER);
		this.listObj = list;
		this.listName = listName;
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		String strList = "";
		for (Object i : listObj)
		{
			strList += i + ", ";
		}
		strList = strList.substring(0, strList.length() - 2);
		
		e.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(listName) + ": " + strList);
	}
}