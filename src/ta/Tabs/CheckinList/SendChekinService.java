package ta.Tabs.CheckinList;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.MainEngine;

public class SendChekinService implements ISendChekinService
{
	private MainEngine _engine;
	private static ScheduledExecutorService _scheduled;
	private static final Lock _lock;

	static // static ctor
	{
		_scheduled = null;
		_lock = new ReentrantLock();
	}
	
	public SendChekinService()
	{
		this._engine = MainEngine.getInstance();
		if(_scheduled != null)
		{
			_scheduled.shutdown();
			_scheduled = null;
			_scheduled = Executors.newScheduledThreadPool(1);
		}
		else
		{
			_scheduled = Executors.newScheduledThreadPool(1);
		}
		
		final SendChekinService service = this;

		Runnable task = new Runnable()
		{
			SendChekinService _service = service;
			@Override
			public void run() {
				_service.SendCheckin();
			}
		};

		_scheduled.scheduleAtFixedRate(task, 0L, 600L, TimeUnit.SECONDS);
	}



	class SendCheckinBFClass extends BackgroundFunc<Object,Boolean> {}
	public void SendCheckin()
	{
		int aaa = 9;
		if( _lock.tryLock() )
		{
			try {
				BackgroundFunc.Go( new SendCheckinBFClass(), get_Send(), get_SendComplete(), "-SendCheckin-");
			}
			finally {
				_lock.unlock();
			}
		}
	}
	
	private SendComplete get_SendComplete() { SendComplete o = new SendComplete(); o.arg1 = this; return o; }
	class   SendComplete extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		boolean result = this.result;
		if(result)
		{
			int aaa = 9;
		}
	}}
	
	private Send get_Send() { Send o = new Send(); o.arg1 = this; return o; }
	class Send extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		SendChekinService _this = (SendChekinService)this.arg1;
		boolean result = false;

		if( HttpHelper.IsInternetAvailable(_this._engine.mContext))
		{
			try {
				CheckinSender.SendCheckinArray(_this._engine.mContext);
			}
			catch( Exception e)
			{
				HttpHelper.ExceptionHandler( e );
				result = false;
			}
		}
		this.result = result;
	}}
}