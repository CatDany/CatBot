package dany.catbot.misc;

public class StrawpollInfo
{
	public final String question;
	public final String[] options;
	
	public StrawpollInfo(String question, String... options)
	{
		this.question = question;
		this.options = options;
	}
}