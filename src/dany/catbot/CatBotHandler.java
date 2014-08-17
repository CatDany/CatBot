package dany.catbot;

import java.util.Iterator;
import java.util.Map.Entry;

import org.pircbotx.Configuration.Builder;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.collect.ImmutableSortedSet;

import dany.catbot.command.ChatCommand;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.ListHelper;

public class CatBotHandler implements Listener<CatBot>
{
	@Override
	public void onEvent(Event<CatBot> e) throws Exception
	{
		if (e instanceof MessageEvent)
		{
			MessageEvent<CatBot> me = (MessageEvent<CatBot>)e;
			ImmutableSortedSet<UserLevel> list = me.getUser().getUserLevels(me.getChannel());
			EnumPermissionLevel perm;
			if (me.getUser().getLogin().equalsIgnoreCase(Settings.BROADCASTER_NAME))
			{
				perm = EnumPermissionLevel.BROADCASTER;
			}
			else if (list.contains(UserLevel.OP))
			{
				perm = EnumPermissionLevel.MODERATOR;
			}
			else if (ListHelper.containsIgnoreCase(Settings.CHOSEN, (me.getUser().getLogin())))
			{
				perm = EnumPermissionLevel.CHOSEN;
			}
			else
			{
				perm = EnumPermissionLevel.USER;
			}
			Iterator<Entry<String, ChatCommand>> iter = Settings.COMMANDS.entrySet().iterator();
			while (iter.hasNext())
			{
				ChatCommand i = iter.next().getValue();
				if (me.getMessage().startsWith("!" + i.commandName) && i.canBeExecuted(me.getUser().getLogin(), perm))
				{
					String query = me.getMessage().replace("!" + i.commandName, "").trim();
					i.execute(query);
					break;
				}
			}
		}
		else if (e instanceof JoinEvent)
		{
			JoinEvent<CatBot> je = (JoinEvent<CatBot>)e;
			je.respond(Localization.get(Localization.BOT_JOINED));
		}
	}
	
	public CatBot init()
	{
		Settings.init();
		Localization.init();
		
		Builder<CatBot> builder = new Builder<CatBot>();
		builder
			.setName(Settings.NAME)
			.setLogin(Settings.NAME)
			.setCapEnabled(false)
			.addListener(this)
			.setServerHostname("irc.twitch.tv")
			.addAutoJoinChannel("#" + Settings.CHANNEL)
			.setVersion(Settings.NAME)
			.setServerPort(6667)
			.setServerPassword(Settings.OAUTH_TOKEN);
		CatBot bot = new CatBot(builder.buildConfiguration());
		
		System.out.println("Logging in...");
		
		bot.start();
		
		try
		{
			bot.startBot();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		
		bot.sendIRC().action("dany2001ru", "boya");
		
		return bot;
	}
}