package ta.timeattendance.Services;

import ta.lib.*;

public class AppService implements IAppService
{
	public AppService()
	{
		this.Closing = new ClosingEventClass();
	}



	public class ClosingEventClass extends Event<Object,Object> {}
	private ClosingEventClass Closing;
	public ClosingEventClass get_Closing(){ return this.Closing; }



	public void ClosingRunEvent()
	{
		this.Closing.RunEvent( null );
	}
}