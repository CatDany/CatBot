package dany.catbot;

import dany.catbot.lib.Helper;


public class Main
{
	public static CatBotHandler handler;
	public static CatBot bot;
	public static AutoMessage autoMsg;
	
	public static void main(String[] args)
	{
		Helper.createFiles();
		
		handler  = new CatBotHandler();
		bot = handler.init();
		bot.start();
	}
}