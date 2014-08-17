package dany.catbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.output.OutputIRC;

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
		while (true)
		{
			// This is a runnable
			// RUUUUUUUUNNNNN!!!!!!!111111
		}
	}
	
	public void start()
	{
		new Thread(this).start();
	}
}