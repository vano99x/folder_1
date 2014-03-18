package ta.timeattendance.Services;

import ta.lib.*;

public class AppService implements IAppService
{
	public AppService()
	{
		this.Closing = new ClosingEventClass();
		this.Running = new RunningEventClass();
		this.Logout = new LogoutEventClass();
	}



	public class ClosingEventClass extends Event<Object,Object> {}
	private ClosingEventClass Closing;
	public ClosingEventClass get_Closing(){ return this.Closing; }

	public class RunningEventClass extends Event<Object,Object> {}
	private RunningEventClass Running;
	public RunningEventClass get_Running(){ return this.Running; }



	public class LogoutEventClass extends Event<Object,Object> {}
	private LogoutEventClass Logout;
	public LogoutEventClass get_Logout(){ return this.Logout; }



	public void ClosingRunEvent()
	{
		this.Closing.RunEvent( null );
	}
	public void RunningRunEvent()
	{
		this.Running.RunEvent( null );
	}
	public void LogoutRunEvent()
	{
		this.Logout.RunEvent( null );
	}
}