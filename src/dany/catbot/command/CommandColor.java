package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.lib.Helper;

public class CommandColor extends ChatCommand
{
	public CommandColor()
	{
		super("color", "{color}", EnumPermissionLevel.BROADCASTER);
	}
	
	@Override
	public void execute(final MessageEvent<CatBot> e, String query)
	{
		Helper.send(e, ".color " + query);
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(1500);
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
				Helper.send(e, Localization.get(Localization.COLOR_CHANGED));
			}
		}.start();
	}
}