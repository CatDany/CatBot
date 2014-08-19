package dany.catbot.auto;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;
import dany.catbot.misc.StrawpollInfo;

public class AutoStrawpoll extends AutoTrigger
{
	public AutoStrawpoll()
	{
		super("strawpoll.me");
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm)
	{
		String[] words = e.getMessage().split(" ");
		for (String i : words)
		{
			i = i.replace("http://", "").replace("/r", "");
			if (i.startsWith("strawpoll.me"))
			{
				StrawpollInfo response = getStrawpollInfo("http://" + i);
				Helper.send(e, Localization.get(Localization.STRAWPOLL_INFO, user, response.question) + " " + Helper.arrayToString(", ", response.options));
			}
		}
	}
	
	public StrawpollInfo getStrawpollInfo(String url)
	{
		String question = null;
		List<String> options = new ArrayList<String>();
		
		try
		{
			Document doc = Jsoup.connect(url).get();
			String pollHeaderRaw = doc.getElementsByClass("pollHeader").get(0).html();
			question = pollHeaderRaw.substring(pollHeaderRaw.indexOf("<div>") + 5, pollHeaderRaw.indexOf("<small>")).trim();
			
			for (Element i : doc.getElementsByClass("pollOption"))
			{
				String pollOptionRaw = i.html();
				String option = pollOptionRaw.substring(pollOptionRaw.indexOf("<div>") + 5, pollOptionRaw.indexOf("</div>")).trim();
				options.add(option);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		
		return new StrawpollInfo(question, options.toArray(new String[0]));
	}
}