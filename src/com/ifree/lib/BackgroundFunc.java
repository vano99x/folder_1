package com.ifree.lib;

import java.lang.reflect.*;

	public class BackgroundFunc extends RunnableWithArgs { public void run()
	{
		String message = null;

		final RunnableWithArgs targetFunc = (RunnableWithArgs)this.arg1;
		Object eventHolder = this.arg2;
		final String eventName = (String)this.arg3;
		try {

		UIHelper.Instance().RunAndWait(
			targetFunc 
			);
		}
		//catch(NoSuchFieldException nsfe)
		//{
		//    Exception e = nsfe;
		//    message = e.getMessage();
		//}
		//catch(NoSuchMethodException nsme)
		//{
		//    Exception e = nsme;
		//}
		//catch(IllegalAccessException iae)
		//{
		//    Exception e = iae;
		//    message = e.getMessage();
		//}
		catch(Exception ite)//InvocationTargetException
		{
			Exception e = ite;
			message = e.getMessage();
		}

		if(message == null)
		{
			message = "SUCCESS";
		}

		//this.result = new Object[]{message};
		//BackgroundFuncComplete.RunEvent(new Object[]{message});
		UIHelper.Instance().get_RespondHandler().post(new Runnable()
		{
			public void run()
			{
				Object res = targetFunc.result;
				BackgroundFunc.this.BackgroundFuncComplete.RunEvent((Object[])res);
			}
		});
	}

	public Event<RunnableWithArgs> BackgroundFuncComplete;

	public static BackgroundFunc get_BackgroundFunc(
		RunnableWithArgs targetFunc, 
		Object eventHolder, 
		String eventName 
		//,Object[] paramArr
	)
	{
		BackgroundFunc bf = new BackgroundFunc();
		bf.BackgroundFuncComplete = new Event<RunnableWithArgs>();

		bf.arg1 = targetFunc;
		bf.arg2 = eventHolder;
		bf.arg3 = eventName;
		//bf.arg4 = paramArr;
		return bf;
	}}





			/*
		final Object[] eventParams = (Object[])targetFunc.result;

			Field eventField = eventHolder.getClass().getField(eventName);
			final Object eventObj = eventField.get(eventHolder);

			//Class classf = field.getType();
			//Method method = classf.getMethod("RunEvent", eventParams.getClass());

			//final Method method = eventField.getType().getMethod("RunEvent", eventParams.getClass());
			//final Method method = eventField.getType().getMethod("RunEvent");
			Method [] methods = eventField.getType().getMethods();
			Method method = null;
			for (Method m : methods)
			{
				String name = m.getName();
				//if( name.equals("RunEvent") )
				//{
				//    method = m;
				//    break;
				//}
				if(eventName == "SaveCheckinCompleteEvent")
				{
				    if( name.equals("RunEvent2") )
				    {
				        method = m;
				        break;
				    }
				}
				else
				{
				    if( name.equals("RunEvent") )
				    {
				        method = m;
				        break;
				    }
				}
			}

			if(method != null)
			{
				final Method methodF = method;

				UIHelper.Instance().get_RespondHandler().post(new Runnable()
				{
				  public void run()
				  {
					try
					{
					final Method   _methodF     = methodF;
					final Object   _eventObj    = eventObj;
					final Object[] _eventParams = eventParams;
		//UIHelper.Instance().ToastInUIThread("aaa \n before methodF.invoke", 3);
		//UIHelper.Instance().Toast("aaa \n before methodF.invoke", 3);
						if(eventName == "SaveCheckinCompleteEvent")
						{
						Object resinvoke = _methodF.invoke( _eventObj, new Object [] { _eventParams, "in ui thread" } );
						}
						else
						{
						Object resinvoke = _methodF.invoke( _eventObj, new Object [] { _eventParams } );
						}
						int aaa = 9;
						int aaa2 = aaa - 2;
					}
					catch(Exception e)
					{
						Exception ex = e;
					}

				  }
				});
			}
			else
			{
				message = "событие чекина не найдено";
			}
*/