package dany.catbot;

public class Main
{
	public static CatBotHandler handler;
	public static CatBot bot;
	public static AutoMessage autoMsg;
	
	public static void main(String[] args)
	{
		handler  = new CatBotHandler();
		bot = handler.init();
		bot.start();
	}
}