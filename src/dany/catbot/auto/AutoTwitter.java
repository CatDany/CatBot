package dany.catbot.auto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
import dany.catbot.Localization;
import dany.catbot.command.EnumPermissionLevel;
import dany.catbot.lib.Helper;

public class AutoTwitter extends AutoTrigger
{
	public AutoTwitter()
	{
		super("twitter.com");
	}
	
	@Override
	public void execute(MessageEvent<CatBot> e, String user, EnumPermissionLevel perm)
	{
		Helper.send(e, ".timeout " + user + " 1");
		String[] words = e.getMessage().split(" ");
		for (String i : words)
		{
			i = i.replace("http://", "").replace("https://", "");
			if (i.matches("^twitter\\.com\\/.+\\/status\\/[0-9].+$"))
			{
				String response = getTweetInfo(user, "http://" + i);
				Helper.send(e, response);
			}
		}
	}
	
	public String getTweetInfo(String user, String url)
	{
		String author = null, tweet = null;
		try
		{
			Document doc = Jsoup.connect(url).get();
			author = doc.getElementsByClass("js-action-profile-name").get(0).text().trim();
			tweet = doc.getElementsByClass("js-tweet-text").get(0).text().trim().replaceAll("pic.twitter.com\\/[a-zA-Z0-9]+", "").trim();
			if (tweet.startsWith("."))
			{
				tweet = tweet.substring(1).trim();
			}
			return Localization.get(Localization.TWEET_INFO, user, author, tweet);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return "<ERROR>";
		}
	}
}