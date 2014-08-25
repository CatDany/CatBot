package dany.catbot;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

import org.pircbotx.output.OutputIRC;

import com.google.common.base.Charsets;

import dany.catbot.lib.Helper;

public class AutoMessage implements Runnable
{
	private final Random random = new Random();
	private final OutputIRC irc;
	private long lastMessage = 0;
	private List<String> messages;
	
	public AutoMessage(OutputIRC irc)
	{
		this.irc = irc;
		
		File file = new File("automessages.txt");
		if (!file.exists())
		{
			return;
		}
		try
		{
			messages = Files.readAllLines(file.toPath(), Settings.CHARSET);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return;
		}
		
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			if (lastMessage + Double.parseDouble(Settings.AUTO_MESSAGE_TIMEOUT) * 60 * 1000 < System.currentTimeMillis())
			{
				String msg = messages.get(random.nextInt(messages.size()));
				Helper.send(irc, msg);
				lastMessage = System.currentTimeMillis();
			}
		}
	}
}