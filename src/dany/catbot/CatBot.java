package dany.catbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.output.OutputIRC;

import dany.catbot.lib.Helper;

public class CatBot extends PircBotX implements Runnable
{
	public final OutputIRC output;
	
	public CatBot(Configuration<CatBot> config)
	{
		super(config);
		
		this.output = new OutputIRC(this);
	}
	
	@Override
	public OutputIRC sendIRC()
	{
		return output;
	}
	
	@Override
	public void run()
	{
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				String input = r.readLine();
				input(input);
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
	
	public void start()
	{
		new Thread(this).start();
	}
	
	public void input(String input)
	{
		Helper.send(sendIRC(), input);
	}
}