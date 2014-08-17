package dany.catbot.auto;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputIRC;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.command.EnumPermissionLevel;

public class AutoLinkShrinker extends AutoTrigger
{
	public AutoLinkShrinker()
	{
		super("adf.ly", "bit.ly", "goo.gl", "go2.do", "t.co", "bit.do", "ow.ly",
				"adcrun.ch", "zpag.es", "ity.im", "q.gs", "lnk.co", "viralurl.com",
				"smarturl.it", "u.to", "x.co", "hit.my", "fun.ly", "gog.li",
				"aka.gr", "tweez.me", "1url.com");
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm)
	{
		OutputIRC irc = e.getBot().sendIRC();
		if (!perm.greaterOrEqual(EnumPermissionLevel.MODERATOR))
		{
			irc.message(Settings.CHANNEL, ".timeout " + user + " 150");
			irc.message(Settings.CHANNEL, Localization.get(Localization.URL_SHORTENERS_BANNED, user));
		}
	}
}