package com.ifree.timeattendance.Models;

import com.ifree.lib.*;
import com.ifree.Database.*;

public class SupervisorModel implements ISupervisorModel
{
	public SupervisorModel()
	{
		this.CurrentSuperviserApplied = new Event<RunnableWithArgs>();
	}



	private Event<RunnableWithArgs> CurrentSuperviserApplied;
	@Override
	public void set_CurrentSuperviserApplied(RunnableWithArgs runnable)
	{
		CurrentSuperviserApplied.Add(runnable);
	}



	private Personel __currentSuperviser;
	@Override
	public Personel get_CurrentSuperviser()
	{
		return this.__currentSuperviser;
	}
	@Override
	public void set_CurrentSuperviser(Personel p)
	{
		this.__currentSuperviser = p;

		if(this.__currentSuperviser == null)
		{
		}
		else
		{
			this.CurrentSuperviserApplied.RunEvent( new Object[]{ this.__currentSuperviser });
		}
	}
}
