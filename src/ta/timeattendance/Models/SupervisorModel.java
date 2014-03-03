package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;

public class SupervisorModel implements ISupervisorModel
{
	public SupervisorModel()
	{
		this.CurrentSuperviserApplied = new CurrentSuperviserAppliedEventClass();
	}



	//*********************************************************************************************
	//**     Event
	class   CurrentSuperviserAppliedEventClass extends Event<Personel,Object> {}
	private CurrentSuperviserAppliedEventClass CurrentSuperviserApplied;
	@Override
	public void OnSvAppliedEvt(RunnableWithArgs runnable)
	{
		CurrentSuperviserApplied.Add(runnable);
	}



	//*********************************************************************************************
	//**     Property
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
			this.CurrentSuperviserApplied.RunEvent( this.__currentSuperviser );
		}
	}
}
