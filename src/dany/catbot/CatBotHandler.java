package dany.catbot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import dany.catbot.command.CommandBirthday;
import dany.catbot.command.CommandColor;
import dany.catbot.command.CommandDel;
import dany.catbot.command.CommandHelp;
import dany.catbot.command.CommandLMGTFY;
import dany.catbot.command.CommandList;
import dany.catbot.command.CommandReload;
import dany.catbot.command.CommandSong;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;
import dany.catbot.misc.BirthdayDatabase;

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
				Helper.send(e, Localization.get(Localization.BOT_JOINED));
				saidHello = true;
			}
			
			if (BirthdayDatabase.birthdayMap.containsKey(me.getUser().getNick()) && BirthdayDatabase.birthdayMap.get(me.getUser().getNick()).shouldHappyBirthday())
			{
				/**
				 * years old
				 */
				int yo = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()))) - Integer.parseInt(BirthdayDatabase.birthdayMap.get(me.getUser().getNick()).year);
				for (int i = 0; i < 3; i++)
				{
					Helper.send(me, Localization.get(Localization.HAPPY_BIRTHDAY, yo, me.getUser().getNick()));
					Thread.sleep(250);
				}
				BirthdayDatabase.birthdayMap.get(me.getUser().getNick()).setHappyBirthdayDone();
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
		
		BirthdayDatabase.reloadDatabase();
		
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
		new CommandList("chosen", Settings.CHOSEN, "list_chosen").register();
		new CommandList("commands", Settings.COMMANDS, "list_commands").register();
		new CommandHelp().register();
		new CommandSong().register();
		new CommandBirthday().register();
	}
}