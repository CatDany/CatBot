package dany.catbot.auto;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputIRC;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;

public class AutoCaps extends AutoTrigger
{
	public AutoCaps()
	{
		super("");
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm)
	{
		if (!perm.greaterOrEqual(EnumPermissionLevel.CHOSEN))
		{
			int minLength = 4;
			double maxCaps = 0.75;
			
			if (e.getMessage().length() > minLength)
			{
				int total = e.getMessage().length();
				int current = 0;
				for (char i : e.getMessage().toCharArray())
				{
					if (Character.isWhitespace(i))
					{
						total--;
					}
					else if (Character.isUpperCase(i) || i == '!')
					{
						current++;
					}
				}
				if (current / total >= maxCaps)
				{
					Helper.send(e, ".timeout " + user + " 150");
					Helper.send(e, Localization.get(Localization.CAPS_BANNED, user));
				}
			}
		}
	}
}