package dany.catbot.command;

import dany.catbot.Settings;

public abstract class ChatCommand
{
	public String commandName;
	public String commandUsage;
	public EnumPermissionLevel commandPermission;
	
	public ChatCommand(String name, String usage, EnumPermissionLevel perm)
	{
		this.commandName = name;
		this.commandUsage = usage;
	}
	
	public void init()
	{
		Settings.COMMANDS.put(commandName, this);
	}
	
	public boolean canBeExecuted(String user, EnumPermissionLevel perm)
	{
		return perm.greaterOrEqual(commandPermission);
	}
	
	public abstract void execute(String query);
}