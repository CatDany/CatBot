package dany.catbot.command;

public enum EnumPermissionLevel
{
	USER(0, "user"),
	CHOSEN(1, "chosen"),
	MODERATOR(2, "mod"),
	BROADCASTER(3, null);
	
	private final int lvl;
	public final String ul;
	
	private EnumPermissionLevel(int lvl, String ul)
	{
		this.lvl = lvl;
		this.ul = ul;
	}
	
	public boolean greaterOrEqual(EnumPermissionLevel perm)
	{
		return this.lvl >= perm.lvl;
	}
	
	public static EnumPermissionLevel parseUserLevel(String ul)
	{
		switch (ul)
		{
		case "user":
			return EnumPermissionLevel.USER;
		case "chosen":
			return EnumPermissionLevel.CHOSEN;
		case "mod":
			return EnumPermissionLevel.MODERATOR;
		}
		return EnumPermissionLevel.USER;
	}
}