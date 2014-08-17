package dany.catbot.command;

public enum EnumPermissionLevel
{
	USER(0),
	CHOSEN(1),
	MODERATOR(2),
	BROADCASTER(3);
	
	private final int lvl;
	
	private EnumPermissionLevel(int lvl)
	{
		this.lvl = lvl;
	}
	
	public boolean greaterOrEqual(EnumPermissionLevel perm)
	{
		return this.lvl >= perm.lvl;
	}
}