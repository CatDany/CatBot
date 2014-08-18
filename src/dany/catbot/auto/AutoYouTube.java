package dany.catbot.auto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.Settings;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;

public class AutoYouTube extends AutoTrigger
{
	public AutoYouTube()
	{
		super("youtube.com/watch?v=",
				"www.youtube.com/watch?v=",
				"youtu.be/");
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm)
	{
		String[] words = e.getMessage().split(" ");
		for (String i : words)
		{
			i = i.replace("http://", "");
			if (i.startsWith("youtube.com/watch?v=")
			 || i.startsWith("www.youtube.com/watch?v=")
			 || i.startsWith("youtu.be/"))
			{
				Object[] response = getYouTubeInfo("http://" + i);
				response[0] = user;
				Helper.send(e, Localization.get(Localization.YOUTUBE_INFO, response));
			}
		}
	}
	
	public String[] getYouTubeInfo(String url)
	{
		try
		{
			Document doc = Jsoup.connect(url).get();
			String title = doc.title().substring(0, doc.title().length() - " - YouTube".length());
			String views = null, likes = null, dislikes = null, uploader = null;
			for (Element i : doc.getElementsByClass("yt-user-name"))
			{
				uploader = i.html();
			}
			for (Element i : doc.getElementsByClass("watch-view-count"))
			{
				views = i.html();
			}
			for (Element i : doc.getElementsByClass("likes-count"))
			{
				likes = i.html();
			}
			for (Element i : doc.getElementsByClass("dislikes-count"))
			{
				dislikes = i.html();
			}
			return new String[] {"#", title, uploader, views, likes, dislikes};
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		
		return new String[] {"#", "?", "?", "?", "?", "?"};
	}
}