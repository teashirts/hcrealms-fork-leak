package me.stafftp.hcf.factions.event.conquest;

import me.stafftp.hcf.util.command.ArgumentExecutor;
import pls.carbon.hcf.Base;

public class ConquestExecutor
  extends ArgumentExecutor
{
  public ConquestExecutor(Base plugin)
  {
    super("conquest");
    addArgument(new ConquestSetpointsArgument(plugin));
  }
}
