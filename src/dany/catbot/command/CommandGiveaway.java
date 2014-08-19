package dany.catbot.command;

import java.util.Random;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Main;
import dany.catbot.lib.Helper;

public class CommandGiveaway extends ChatCommand
{
	public static RunningGiveaway giveaway = null;
	
	public CommandGiveaway()
	{
		super("giveaway", "{start|stop}", EnumPermissionLevel.MODERATOR);
	}
	
	@Override
	public void execute(final MessageEvent<CatBot> e, String query)
	{
		if (query.equals("start"))
		{
			new Thread()
			{
				public void run()
				{
					giveaway = new RunningGiveaway();
					Helper.send(e, Localization.get(Localization.GIVEAWAY_START));
					while (true)
					{
						if (giveaway == null)
						{
							Helper.send(Main.bot.sendIRC(), Localization.get(Localization.GIVEAWAY_STOP));
							return;
						}
						else if (!giveaway.winner.equals(""))
						{
							Helper.send(Main.bot.sendIRC(), Localization.get(Localization.GIVEAWAY_WINNER, giveaway.winner));
							giveaway = null;
							return;
						}
					}
				}
			}.start();
		}
		else if (query.equals("stop"))
		{
			giveaway = null;
		}
		else
		{
			Helper.send(e, Localization.get(Localization.WRONG_ARGUMENTS, e.getUser().getNick(), "!" + commandName + " " + commandUsage));
		}
	}
	
	public class RunningGiveaway
	{
		private final Random random = new Random();
		private final int theNumber;
		private String winner = "";
		
		public RunningGiveaway()
		{
			this.theNumber = random.nextInt(100);
			System.out.println("Started giveaway with secret number #" + theNumber + " ;)");
		}
		
		public void trigger(String user, int number)
		{
			if (number == theNumber)
			{
				winner = user;
				System.out.println("The winner is " + winner + "!");
			}
		}
		
		public void parseAndTrigger(String user, String msg)
		{
			try
			{
				if (msg.startsWith("#"))
				{
					int suggestedNumber = Integer.parseInt(msg.substring(1));
					trigger(user, suggestedNumber);
				}
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
}