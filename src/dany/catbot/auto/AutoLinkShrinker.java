package dany.catbot.auto;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;

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
		if (!perm.greaterOrEqual(EnumPermissionLevel.MODERATOR))
		{
			Helper.send(e, ".timeout " + user + " " + Settings.TIMEOUT_IN_SEC);
			Helper.send(e, Localization.get(Localization.URL_SHORTENERS_BANNED, user));
		}
	}
}