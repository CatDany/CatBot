package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputIRC;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;

public class CommandColor extends ChatCommand
{
	public CommandColor()
	{
		super("color", null, EnumPermissionLevel.BROADCASTER);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		final OutputIRC irc = e.getBot().sendIRC();
		irc.message(Settings.CHANNEL, ".color " + query);
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
				irc.message(Settings.CHANNEL, Localization.get(Localization.COLOR_CHANGED));
			}
		}.start();
	}
}