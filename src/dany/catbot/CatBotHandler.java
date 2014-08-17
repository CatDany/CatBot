package dany.catbot;

import java.util.ArrayList;
import java.util.List;

import org.pircbotx.Configuration.Builder;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSortedSet;

import dany.catbot.auto.AutoCaps;
import dany.catbot.auto.AutoLinkShrinker;
import dany.catbot.auto.AutoTrigger;
import dany.catbot.auto.AutoYouTube;
import dany.catbot.command.ChatCommand;
import dany.catbot.command.CommandAdd;
import dany.catbot.command.CommandColor;
import dany.catbot.command.CommandDel;
import dany.catbot.command.CommandLMGTFY;
import dany.catbot.command.CommandReload;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;

public class CatBotHandler implements Listener<CatBot>
{
	private boolean saidHello = false;
	private List<AutoTrigger> autoTriggers = new ArrayList<AutoTrigger>();
	
	@Override
	public void onEvent(Event<CatBot> e) throws Exception
	{
		if (e instanceof MessageEvent)
		{
			MessageEvent<CatBot> me = (MessageEvent<CatBot>)e;
			
			if (!saidHello)
			{
				System.out.println("Saying Hello...");
				me.getBot().sendIRC().message(Settings.CHANNEL, Localization.get(Localization.BOT_JOINED));
				saidHello = true;
			}
			
			ImmutableSortedSet<UserLevel> list = me.getUser().getUserLevels(me.getChannel());
			EnumPermissionLevel perm;
			if (me.getUser().getNick().equalsIgnoreCase(Settings.BROADCASTER_NAME))
			{
				perm = EnumPermissionLevel.BROADCASTER;
			}
			else if (list.contains(UserLevel.OP))
			{
				perm = EnumPermissionLevel.MODERATOR;
			}
			else if (Helper.containsIgnoreCase(Settings.CHOSEN, (me.getUser().getNick())))
			{
				perm = EnumPermissionLevel.CHOSEN;
			}
			else
			{
				perm = EnumPermissionLevel.USER;
			}
			
			if (me.getMessage().startsWith("!"))
			{
				for (ChatCommand i : Settings.COMMANDS)
				{
					if (me.getMessage().startsWith("!" + i.commandName) && i.canBeExecuted(me.getUser().getNick(), perm))
					{
						String query = me.getMessage().replace("!" + i.commandName, "").trim();
						i.execute(me, query);
						break;
					}
				}
			}
			
			for (AutoTrigger i : autoTriggers)
			{
				for (String j : i.startsWith)
				{
					if (j.equals("") || Helper.containsIgnoreCase(me.getMessage(), j))
					{
						i.execute(me, me.getUser().getNick(), perm);
						break;
					}
				}
			}
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
			.addAutoJoinChannel(Settings.CHANNEL)
			.setVersion(Settings.NAME)
			.setServerPort(6667)
			.setServerPassword(Settings.OAUTH_TOKEN)
			.setEncoding(Charsets.UTF_8);
		CatBot bot = new CatBot(builder.buildConfiguration());
		
		autoTriggers.add(new AutoLinkShrinker());
		autoTriggers.add(new AutoYouTube());
		autoTriggers.add(new AutoCaps());
		addSystemCommands();
		
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
		
		return bot;
	}
	
	public void addSystemCommands()
	{
		new CommandColor().register();
		new CommandLMGTFY().register();
		new CommandAdd().register();
		new CommandDel().register();
		new CommandReload().register();
	}
}