package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.Services.*;

public class SupervisorModel implements ISupervisorModel
{
	private IAppService __appService;
	//private static int _counter;
	public SupervisorModel()
	{
		//_counter++;
		this.CurrentSuperviserApplied = new CurrentSuperviserAppliedEventClass();
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());
	}



	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		SupervisorModel _this = (SupervisorModel)this.arg1;
		_this.__currentSuperviser = null;
	}}



	//*********************************************************************************************
	//**     Event
	class   CurrentSuperviserAppliedEventClass extends Event<Personel,Object> {}
	private CurrentSuperviserAppliedEventClass CurrentSuperviserApplied;
	@Override
	public void SvChanged_EventAdd(RunnableWithArgs runnable)
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
		if(p != null)
		{
			this.__currentSuperviser = p;
			this.CurrentSuperviserApplied.RunEvent( this.__currentSuperviser );
		}
	}
	
	static
	{
		//_counter = 0;
	}
}
