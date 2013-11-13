package com.ifree.timeattendance;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.ifree.Database.Checkin;
import com.ifree.Database.Personel;
import com.ifree.Database.PersonelPoint;
import com.ifree.Database.Point;
import java.io.IOException;

import java.net.URL;
import java.net.*;

import java.util.TimerTask;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.ifree.lib.*;
import com.ifree.timeattendance.MainActivity.State;
import com.ifree.lib.UIHelper.Act;

public class MainEngine
{
	//private static final String KEY_CURRENT_MODE = "timeattendance.currentMode";
	//private static final String KEY_CURRENT_PIN = "timeattendance.PIN";
	//private static final String KEY_CURRENT_POINT_ID = "timeattendance.pointID";
	//private static final String KEY_STATE = "timeattendance.state";
	//private static final String SETTINGS_PREF_NAME = "com.ifree.timeattendance";

	private static MainEngine _mainEngineObj = null;
	private Context mContext;
	private String mPin;
	private int currentMode;

	private Personel __currentPersonel;
	private Personel __currentSuperviser;
	private boolean __isIntenetDisable;
	private int __currentPointId;
	public Event<RunnableWithArgs> AuthenticateSVCompleteEvent;
	public Event<RunnableWithArgs> SaveCheckinCompleteEvent;
	public Event<RunnableWithArgs> WorkerFound;
	public Event<RunnableWithArgs> Clearing;
	public Event<RunnableWithArgs> Closing;

	Runnable showPersonelRunnable;
	Runnable showScreenRunnable;
	Runnable serverRunnable;
	//*********************************************************************************************
	//       instance
	public static MainEngine getInstance()
	{
		return MainEngine._mainEngineObj;
	}
	public static void CreateInstance( Context context )
	{
		MainEngine._mainEngineObj = new MainEngine(context);
	}
	public static void DeleteEngine()
	{
		MainEngine._mainEngineObj = null;
	}

	//*********************************************************************************************
	//       ctor
	private MainEngine( Context context )
	{
		this.Nulling();

		this.mContext = context;
		//this.mSettings = context.getSharedPreferences("com.ifree.timeattendance", 0);

		this.AuthenticateSVCompleteEvent = new Event<RunnableWithArgs>();
		this.SaveCheckinCompleteEvent    = new Event<RunnableWithArgs>();
		this.WorkerFound                 = new Event<RunnableWithArgs>();
		this.Clearing                    = new Event<RunnableWithArgs>();
		this.Closing                     = new Event<RunnableWithArgs>();

		this.SaveCheckinCompleteEvent.Add(get_onSaveCheckin_CompleteEventHandler());
	}
	public void MainEngine_Clear()
	{
		//this.Closing.RunEvent(null);
		this.Nulling();
	}
	public void Nulling()
	{
		this.mContext = null;
		this.mPin = null;
		this.currentMode = 1;

		this.__currentPersonel = null;
		this.__currentSuperviser = null;
		this.__isIntenetDisable = false;
		this.__currentPointId = -1;

		this.AuthenticateSVCompleteEvent = null;
		this.SaveCheckinCompleteEvent = null;
		this.WorkerFound = null;
		this.Clearing = null;
		this.Closing = null;

		this.showPersonelRunnable = null;
		this.showScreenRunnable = null;
		this.serverRunnable = null;
	}





	//*********************************************************************************************
	//       manage state
	public void MainEngine_ToDefault()
	{
		set_CurrentSuperviser(null);
		setCurrentMode(1);
		MainEngine._mainEngineObj.Clearing.RunEvent(null);
	}



	//*********************************************************************************************
	//       Event Handler
	private onSaveCheckin_CompleteEventHandler get_onSaveCheckin_CompleteEventHandler()
	{
		onSaveCheckin_CompleteEventHandler o = new onSaveCheckin_CompleteEventHandler();
		return o;
	}
	class onSaveCheckin_CompleteEventHandler extends RunnableWithArgs { public void run()
	{
		//UIHelper.Instance().Toast(" in onSaveCheckin" + UIHelper.Instance().currentState.toString(), 3);
		switch(UIHelper.Instance().currentState)
		{
			case PERSONEL_INFO:{
				UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
			break;}
			case WAIT_MODE:{
				Object[] resultArr = (Object[])this.result;
				boolean result = ((Boolean)resultArr[0]).booleanValue();
				if(result)
				{
					UIHelper.Instance().tabPersonelInfo.IsShowCheckiedWorker = true;
					UIHelper.Instance().switchState(MainActivity.State.PERSONEL_INFO);
				}
			break;}
			default:{
				// ...
			break;}
		}
	}}


  //private void showError()
  //{
  //  showScreen(MainActivity.State.ERROR_CONNECTION, 0L);
  //  showScreen(MainActivity.State.WAIT_MODE, 3000L);
  //}

	//public void cancelOtherTasks()
	//{
	//    try
	//    {
	//        if (this.showScreenRunnable != null){
	//            this.respondHandler.removeCallbacks(this.showScreenRunnable);
	//        }
	//        if (this.serverRunnable != null){
	//            this.respondHandler.removeCallbacks(this.serverRunnable);
	//        }
	//        if (this.showPersonelRunnable != null){
	//            this.respondHandler.removeCallbacks(this.showPersonelRunnable);
	//        }
	//    }
	//    catch (Exception localException)
	//    {
	//        //TALog.Log(localException.toString());
	//    }
	//}

  //public void cancelTimerTask()
  //{
  //  if (this.mTimerTask != null)
  //  {
  //    this.mTimerTask.cancel();
  //    this.mTimerTask = null;
  //  }
  //}

  //void saveState(MainActivity.State paramState)
  //{
  //  SharedPreferences.Editor localEditor = this.mSettings.edit();
  //  TALog.Log("SAVE_STATE : state = " + paramState);
  //  localEditor.putInt("timeattendance.state", paramState.ordinal());
  //  localEditor.putString("timeattendance.PIN", this.mPin);
  //  localEditor.putInt("timeattendance.currentMode", this.currentMode);
  //  localEditor.putInt("timeattendance.pointID", this.get_CurrentPointId());
  //  localEditor.commit();
  //}
	
	public int getCurrentMode()
	{
		return this.currentMode;
	}
	public void setCurrentMode(int paramInt)
	{
		this.currentMode = paramInt;
	}
	
	public Personel get_CurrentPersonel()
	{
		return this.__currentPersonel;
	}
	public void set_CurrentPersonel(Personel p)
	{
		this.__currentPersonel = p;
	}
	
	public Personel get_CurrentSuperviser()
	{
		return this.__currentSuperviser;
	}
	public void set_CurrentSuperviser(Personel p)
	{
		this.__currentSuperviser = p;
	}
	
	public boolean get_IsIntenetDisable()
	{
		return this.__isIntenetDisable;
	}
	public void set_IsIntenetDisable(boolean val)
	{
		this.__isIntenetDisable = val;
	}
	
	public int get_CurrentPointId()
	{
		return this.__currentPointId;
	}
	public void set_CurrentPointId(int paramPointId)
	{
		this.__currentPointId = paramPointId;
	}

	private void MsgFromBackground(Act act)
	{
		UIHelper h = UIHelper.Instance();
		h.MsgFromBackground( act );
	}

	private static void Synchronization(
		Personel sv, Personel [] workers, Point [] points, PersonelPoint [] ppoints, Context context)
		throws java.io.IOException, java.net.ProtocolException, java.lang.IndexOutOfBoundsException
	{
		Personel.sync( new Personel [] { sv }, context);
		Personel.sync(      workers,           context);
		Point.sync(         points,            context);
		PersonelPoint.sync( ppoints,           context);

		ArrayList<Checkin> ch = Checkin.GetLocalCheckins(context);
		String respond = null;
		for(Checkin item : ch)
		{
			String strUrl = HttpHelper.getCheckinURL( item );
			respond = HttpHelper.httpGet(new URL(strUrl));
			
			item.IsCheckinExistOnServer = true;
			Checkin.Update( item, context );
		}
	}

	private static Personel LoadPersonelFromServer( String pin, Context context)
		throws MalformedURLException , IOException , JSONException
	{
//String str = "http://akme.telemetry.i-free.ru/Api/sync/checkin?id=4&WorkerId=5&cardId=2999600235&status=3&pointId=4&date=1382632193964";
		String str = HttpHelper.getAuthRequestURL(pin);
		URL url = new URL(str);
		String data = HttpHelper.httpGet(url);

		JSONObject json = new JSONObject(data);

		Personel p = Personel.FromJson( json, context);
		if(p != null)
		{
			p.Pin = pin;
		}

		return p;
	}



	//************************************************************************************************
	// 1
	private onBfAuth get_onBfAuth()
	{
		onBfAuth o = new onBfAuth(); o.arg1 = this; return o;
	}
	class onBfAuth extends RunnableWithArgs { public void run()
	{
		MainEngine _this = (MainEngine)this.arg1;
		_this.AuthenticateSVCompleteEvent.RunEvent((Object[])this.result);
	}}

	void AuthenticateSV_2(final String pinStr)
	{
		boolean result = HttpHelper.CheckInternetAndShowMessage(this, this.mContext);
		if(!this.get_IsIntenetDisable() && !result)
			return;

		this.mPin = pinStr; //TALog.Log("Check Pin start");

		BackgroundFunc bf = 
			BackgroundFunc.get_BackgroundFunc( 
				get_onAuthenticateSV(this.mPin), this,  "AuthenticateSVCompleteEvent"
			);
		bf.BackgroundFuncComplete.Add(get_onBfAuth());

		this.serverRunnable = bf;

		Thread thread = new Thread(this.serverRunnable,"-Authenticate-"); // thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	private onAuthenticateSV get_onAuthenticateSV(String pinStr)
	{
		onAuthenticateSV o = new onAuthenticateSV();
		o.arg1 = this;
		o.arg2 = pinStr;
		return o;
	}
	class onAuthenticateSV extends RunnableWithArgs { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		String pinStr = (String)this.arg2;
		boolean result = false;

		MsgFromBackground( Act.StartOperation );
		Personel sv = null;

		if( !engine.get_IsIntenetDisable() )
		{
			try
			{
				sv = LoadPersonelFromServer( pinStr, engine.mContext);
				if(sv != null)
				{
					engine.set_CurrentSuperviser(sv);
					Personel [] workers = sv.LoadWorkers(engine.mContext);

					MainEngine.Synchronization( sv, workers, sv.pointArray, sv.get_PersonelPoints(), engine.mContext);

					MsgFromBackground(Act.ServerAuthOk);
					result = true;
				} else {
					MsgFromBackground(Act.ServerAuthError);
				}
			}
			catch( Exception e)
			{
				HttpHelper.ExceptionHandler( e );
			}
		}
		else
		{
			try
			{
				sv = Personel.SelecByPin( pinStr );
				if( sv != null && sv.Id != -1 && sv.IsSupervisor) {
					engine.set_CurrentSuperviser(sv);
					
					MsgFromBackground(Act.LocalAuthOk);
					result = true;
				}else{
					MsgFromBackground(Act.LocalAuthError);
				}
			}
			catch( Exception e)
			{
				int aaa = 9;
				int aaa2 = aaa - 2;
				Exception ex = e;
			}
		}

		this.result = new Object[]{result, sv};
		//UIHelper.Instance().ToastInUIThread("aaa \n onAuthenticateSV", 15);
	}}



	//************************************************************************************************
	// 2 entering tag
	private onClickOkMessageBox get_onClickOkMessageBox() { onClickOkMessageBox o = new onClickOkMessageBox(); o._this = this; return o; } 
	class onClickOkMessageBox implements android.content.DialogInterface.OnClickListener { public MainEngine _this;public void onClick( 
			android.content.DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	{
		_this.SaveCheckinCompleteEvent.RunEvent(new Object[]{false});
	}}
	public void OnNfcTagApply(final long cardId) // ---> call in bg thread
	{
		Personel p = Personel.SelectByCard( cardId );
		if(p != null && p.Id != -1)
		{
			this.set_CurrentPersonel(p);
			SaveCheckin();
		}
		else
		{
			UIHelper.Instance().MessageBoxInUIThread(
				"��������� �� ������! \r\n " + Long.toHexString(cardId),
				get_onClickOkMessageBox(),
				null
			);
		}
	}


	//************************************************************************************************
	// 3 save checkin from personel info
	private onBfComplete get_onBfComplete() {
		onBfComplete o = new onBfComplete(); o.arg1 = this; return o;
	}
	class onBfComplete extends RunnableWithArgs { public void run()
	{
		MainEngine _this = (MainEngine)this.arg1;
		_this.SaveCheckinCompleteEvent.RunEvent((Object[])this.result);
	}}

	
	public void SaveCheckin() // ---> call in ui thread and bg thread
	{
		if((this.get_CurrentPersonel() != null) && (this.get_CurrentPersonel().Id != -1) )
		{
			BackgroundFunc bf = 
				BackgroundFunc.get_BackgroundFunc( 
					get_onSaveCheckin(), this, "SaveCheckinCompleteEvent"
				);
			bf.BackgroundFuncComplete.Add(get_onBfComplete());

			this.serverRunnable = bf;
			new Thread(this.serverRunnable,"-SaveCheckin-").start();
		}
	}
	private onSaveCheckin get_onSaveCheckin()
	{
		onSaveCheckin o = new onSaveCheckin();
		o.arg1 = this;
		return o;
	}
	class onSaveCheckin extends RunnableWithArgs { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		boolean result = false;

		MsgFromBackground(Act.StartOperation);

		Checkin ch = new Checkin(
			engine.get_CurrentSuperviser().Id,				// SupervicerId
			engine.get_CurrentPersonel().Id,					// Id
			engine.get_CurrentPersonel().CardId,				// CardId
			//engine.get_CurrentPersonel().IsSupervisor,
			engine.getCurrentMode(),							// Mode
			engine.get_CurrentPointId(),						// PointId
			String.valueOf(System.currentTimeMillis()) // DateTime
		);

		//Checkin [] ch2 = Checkin.GetCheckinByPersonelCode( this.currentPersonel.PersonelCode, this.mContext);
		//Personel p = Personel.checkPin((String)arg1, MainEngine.this.mContext);
		//Checkin [] checkinArr = Checkin.GetAllCheckin( this.mContext);


		if( engine.get_IsIntenetDisable() == false )
		{
			try
			{
				String strUrl = HttpHelper.getCheckinURL( ch );
				String respond = null;

				respond = HttpHelper.httpGet(new URL(strUrl));
				
				//ch.save( engine.mContext, true);
				ch.IsCheckinExistOnServer = true;
				ch.save( engine.mContext );
				
				MsgFromBackground( Act.CheckinSuccess );
				result = true;
			}
			catch( Exception e)
			{
				//ch.save(engine.mContext, false);
				ch.IsCheckinExistOnServer = false;
				ch.save( engine.mContext );
				
				HttpHelper.ExceptionHandler( e );
				result = false;
			}
		}
		else
		{
				//ch.save(engine.mContext, false);
				ch.IsCheckinExistOnServer = false;
				ch.save( engine.mContext );
			
				MsgFromBackground( Act.CheckinSaveLocal );
				result = true;
		}
		//UIHelper.Instance().ShowScreenFromBackground(state, 0L);//MainActivity.State.WAIT_MODE
		this.result = new Object[]{result};
		//UIHelper.Instance().ToastInUIThread("aaa \n onSaveCheckin", 15);
	}}


	//************************************************************************************************
	// 4 search worker
	public void searchPersonel(final String paramString)
	{
		final Personel[] arrayOfPersonel = Personel.search(paramString, this.mContext);
		if (arrayOfPersonel != null)
		{
			WorkerFound.RunEvent( new Object[]{ arrayOfPersonel});
		}
	}

	//************************************************************************************************
	// 5 Sync
	public void startSync()
	{
		this.serverRunnable = new Runnable() { public void run()
		{
			MsgFromBackground( Act.StartOperation );

			MainEngine _this = MainEngine.this;
			try
			{
				Personel sv = LoadPersonelFromServer( _this.mPin, _this.mContext);
				if(sv.Id != 0)
				{
					_this.set_CurrentSuperviser(sv);
					Personel [] workers = sv.LoadWorkers(_this.mContext);
					MainEngine.Synchronization( sv, workers, sv.pointArray, sv.get_PersonelPoints(), _this.mContext);

					MsgFromBackground(Act.SyncOk);
				}
				else
				{
					MsgFromBackground(Act.SyncError);
				}
			}
			catch( Exception e)
			{
				HttpHelper.ExceptionHandler( e );
			}
		}};
		new Thread(this.serverRunnable,"-startSync-").start();
	}
}