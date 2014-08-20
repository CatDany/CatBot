package dany.catbot.command;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;

import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Charsets;

import dany.catbot.CatBot;
import dany.catbot.Settings;
import dany.catbot.lib.Helper;

public class TextCommand extends ChatCommand
{
	public final String response;
	
	public TextCommand(String name, String ul, String response)
	{
		super(name, null, EnumPermissionLevel.parseUserLevel(ul));
		this.response = response;
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		String reply = response;
		
		if (response.startsWith("$CUSTOM_API="))
		{
			try
			{
				URL website = new URL(response.substring(12));
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("_customapi");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				File file = new File("_customapi");
				String api_response = Helper.arrayToString(" ", Files.readAllLines(file.toPath(), Charsets.ISO_8859_1).toArray(new String[0]));
				file.delete();
				reply = api_response;
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
		else
		{
			if (response.contains("%s") && query != null && !query.equals(""))
			{
				reply = String.format(reply, query);
			}
			if (response.contains("$u"))
			{
				reply = reply.replace("$u", e.getUser().getNick());
			}
			if (response.contains("$b"))
			{
				reply = reply.replace("$b", Settings.BROADCASTER_NAME);
			}
		}
		
		Helper.send(e,  reply);
	}
}