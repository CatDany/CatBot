package dany.catbot.command;

import org.pircbotx.hooks.events.MessageEvent;

import dany.catbot.CatBot;
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
		this.commandPermission = perm;
	}
	
	public void register()
	{
		Settings.COMMANDS.add(this);
	}
	
	public boolean canBeExecuted(String user, EnumPermissionLevel perm)
	{
		return perm.greaterOrEqual(commandPermission);
	}
	
	public abstract void execute(MessageEvent<CatBot> e, String query);
}