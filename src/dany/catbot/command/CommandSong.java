package dany.catbot.command;

import java.io.File;

import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.lib.Helper;

public class CommandSong extends ChatCommand
{
	public CommandSong()
	{
		super("song", null, EnumPermissionLevel.USER);
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String query)
	{
		File file = new File("currentsong.txt");
		if (!file.exists())
		{
			Helper.send(e, Localization.get(Localization.CURRENT_SONG_NOT_ENABLED));
			return;
		}
		String song;
		try
		{
			song = Files.readFirstLine(file, Charsets.UTF_8);
		}
		catch (Throwable t)
		{
			song = "<ERROR>";
			t.printStackTrace();
		}
		Helper.send(e, Localization.get(Localization.CURRENT_SONG, song));
	}
}