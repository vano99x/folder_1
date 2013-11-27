﻿package com.ifree.lib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.widget.Toast;
import android.os.Handler;

import com.ifree.timeattendance.*;
import com.ifree.CheckinList.*;

import com.ifree.timeattendance.MainActivityProxy;
import com.ifree.lib.tabui.Tab;
import com.ifree.lib.Controls.SvBox;
import com.ifree.lib.Controls.LoadingProgressBar;
import com.ifree.timeattendance.MainActivity;
import com.ifree.timeattendance.MainActivity.State;
import com.ifree.lib.RunnableWithArgs;

import android.support.v4.app.FragmentActivity;

import com.example.test6.R;


public class UIHelper implements IMessageReceiver
{
	public static UIHelper    __instance;
	private MainActivityProxy context;
	private ViewGroup         rootView;
	public  State             currentState;
	private Handler           __respondHandler;

	public  SvBox           svBox;
	public  TabPin           tabPin;
	public  TabMainMenu      tabMainMenu;
	public  TabReference      tabReference;
	private TabPointsList    tabPointsList;
	private TabModeSelection tabModeSelection;
	private TabWait          tabWait;
	public  TabPersonelInfo  tabPersonelInfo;
	public  TabPersonelList  tabPersonelList;
	private TabCheckinList   tabCheckinList;

	private TabItem[]         __tabItemArray;
	//private HttpMessage       _myRunnable;
	private ProgressDialog    mProgress;
	//private final Object _lockObj = new Object();
	private Runnable          serverRunnable;
	private Runnable          showScreenRunnable;

	
	//*********************************************************************************************
	//       instance
	public static UIHelper Instance()
	{
		return UIHelper.__instance;
	}
	public static void CreateInstance( com.ifree.timeattendance.MainActivityProxy context, ViewGroup root)
	{
		UIHelper.__instance = null;
		UIHelper.__instance = new UIHelper(context,root);
	}
	public static void DeleteInstance()
	{
		UIHelper.__instance = null;
	}

	//*********************************************************************************************
	//       ctor
	private UIHelper( com.ifree.timeattendance.MainActivityProxy context, ViewGroup root)
	{
		this.Nulling();

		this.context = context;
		this.rootView = root;
		this.get_RespondHandler(); // create handler

		//*****************************************************************************************
		this.svBox = new SvBox( this.context, this.rootView);
		//svBox.HideNameBlock();

		this.tabPin             = new TabPin(            this.context, this.rootView, R.layout._1_pin,               R.id.PagePin);
		this.tabMainMenu        = new TabMainMenu(       this.context, this.rootView, R.layout._2_main_menu,              R.id.MainMenu_Id);
		this.tabReference        = new TabReference(       this.context, this.rootView, R.layout._5_reference,              R.id.Reference_Id);
		this.tabPointsList      = new TabPointsList(     this.context, this.rootView, R.layout._3_points_list_2,       R.id.PagePointsList);
		this.tabCheckinList     = new TabCheckinList(    this.context, this.rootView, R.layout._4_checkin_list,      R.id.PageCheckinList);
		this.tabModeSelection   = new TabModeSelection(  this.context, this.rootView, R.layout.page_mode_selection,    R.id.PageModeSelection);
		this.tabWait            = new TabWait(           this.context, this.rootView, R.layout.page_wait2,             R.id.PageWait);
		//this.tabDataTransmition = new TabDataTransmition(this.context, this.rootView, R.layout.page_data_transmition2, R.id.PageDataTransmition);
		//this.tabErrorReading    = new TabErrorReading(   this.context, this.rootView, R.layout.page_error_reading,     R.id.PageErrorReading);
		//this.tabErrorConnection = new TabErrorConnection(this.context, this.rootView, R.layout.page_error_connection,  R.id.PageErrorConnection);
		this.tabPersonelInfo    = new TabPersonelInfo(   this.context, this.rootView, R.layout.page_personel_info,     R.id.PagePersonelInfo);
		this.tabPersonelList    = new TabPersonelList(   this.context, this.rootView, R.layout.page_personel_list,     R.id.PagePersonelList);
		//this.tabSync            = new TabSync(           this.context, this.rootView, R.layout.page_sync,              R.id.PageSync);

		this.SetToDefaultState();
	}
	public void UIHelper_Clear()
	{
		this.__respondHandler.removeCallbacksAndMessages(null);
		svBox.Clear();
		tabPin.Clear();
		tabMainMenu.Clear();
		tabReference.Clear();
		tabPointsList.Clear();
		tabModeSelection.Clear();
		tabWait.Clear();
		tabPersonelInfo.Clear();
		tabPersonelList.Clear();
		tabCheckinList.Clear();

		this.Nulling();
	}
	private void Nulling()
	{
		svBox = null;
		tabPin = null;
		tabMainMenu = null;
		tabReference = null;
		tabPointsList = null;
		tabModeSelection = null;
		tabWait = null;
		tabPersonelInfo = null;
		tabPersonelList = null;
		tabCheckinList = null;

		this.context = null;
		this.rootView = null;
		this.currentState = State.NULL;
		this.__respondHandler = null;

		this.__tabItemArray = null;
		//this._myRunnable = null;
		this.mProgress = null;
		this.serverRunnable = null;
		this.showScreenRunnable = null;
	}



	//*********************************************************************************************
	//       manage state
	public void SetToDefaultState()
	{
		this.currentState = State.NULL;
		this.HideAll();
		//this.tabPin.setListener((TabActionListener)this.context);
		//this.tabModeSelection.setListener((TabActionListener)this.context);
		//this.tabPointsList.setListener((TabActionListener)this.context);
		//this.tabWait.setListener((TabActionListener)this.context);
		//////this.tabDataTransmition.setListener(this.context);
		//////this.tabErrorReading.setListener(this.context);
		//////this.tabErrorConnection.setListener(this.context);
		//this.tabPersonelInfo.setListener((TabActionListener)this.context);
		//this.tabPersonelList.setListener((TabActionListener)this.context);
		//this.tabMainMenu.setListener((TabActionListener)this.context);
		//////this.tabSync.setListener(this.context);
		//this.tabCheckinList.setListener((TabActionListener)this.context);

		int sizeInDp = 53;
		float scale = this.context.getResources().getDisplayMetrics().density;
		int dpAsPixels = (int) (sizeInDp*scale + 0.5f);

		TabItem[] myArray = get_TabItemArray();
		int count = myArray.length;
		for( int i = 0; i < count; i++)
		{
		    ViewGroup viewGroup = myArray[i].Tab.getRoot();
			viewGroup.setPadding(0, dpAsPixels, 0, 0);
		    //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
		    //mlp.setMargins(0, 20, 0, 0);
		}
	}
	public void HideAll()
	{
		this.tabPin.Hide();
		this.tabModeSelection.Hide();
		this.tabPointsList.Hide();
		this.tabWait.Hide();
		//this.tabDataTransmition.hide();
		//this.tabErrorReading.hide();
		//this.tabErrorConnection.hide();
		this.tabPersonelInfo.Hide();
		this.tabPersonelList.Hide();
		this.tabMainMenu.Hide();
		this.tabReference.Hide();
		//this.tabSync.hide();
		this.tabCheckinList.Hide();
	}

	
	//*********************************************************************************************
	//       properties
	public Handler get_RespondHandler()
	{
		if(this.__respondHandler == null)
		{
			try
			{
				this.__respondHandler = new Handler();
			}
			catch(Exception e)
			{
				Exception ex = e;
			}
		}
		return this.__respondHandler;
	}


	
	class TabItem 
	{
		public TabItem(int stateInt, Tab tab){ StateInt = stateInt; Tab = tab;}
		public int StateInt;
		public Tab Tab;
	}

	TabItem[] get_TabItemArray()
	{
		if(__tabItemArray == null)
		{
			int intPin               = State.PIN.ordinal();
			int intMenu              = State.MAIN_MENU.ordinal();
			int intPointsList        = State.POINTS_LIST.ordinal();
			int intSelection         = State.MODE_SELECTION.ordinal();
			int intWaitMode          = State.WAIT_MODE.ordinal();
			int intPersonelListMode  = State.PERSONEL_LIST_MODE.ordinal();
			int intPersonelInfo      = State.PERSONEL_INFO.ordinal();
			int intCHECKIN_LIST      = State.CHECKIN_LIST.ordinal();
			int intReference      = State.REFERENCE.ordinal();

			__tabItemArray = new TabItem[] { 
				new TabItem(intPin,          tabPin),            new TabItem(intMenu,             tabMainMenu),
				new TabItem(intPointsList,   tabPointsList),     new TabItem(intSelection,        tabModeSelection),
				new TabItem(intWaitMode,     tabWait),           new TabItem(intPersonelListMode, tabPersonelList),
				new TabItem(intPersonelInfo, tabPersonelInfo), 
				new TabItem(intCHECKIN_LIST, tabCheckinList), 
				new TabItem(intReference, tabReference)
			};
		}
		return __tabItemArray;
	}
	public void switchState(State paramState)
	{
		try
		{



		//TALog.Log("switchState = " + paramState);
		this.currentState = paramState;

		int intParamState = paramState.ordinal();

		int intPin               = State.PIN.ordinal();
		int intMenu              = State.MAIN_MENU.ordinal();
		int intPointsList        = State.POINTS_LIST.ordinal();
		int intSelection         = State.MODE_SELECTION.ordinal();
		int intWaitMode          = State.WAIT_MODE.ordinal();
		int intPersonelListMode  = State.PERSONEL_LIST_MODE.ordinal();
		int intPersonelInfo      = State.PERSONEL_INFO.ordinal();
		int intCHECKIN_LIST      = State.CHECKIN_LIST.ordinal();
		int intReference      = State.REFERENCE.ordinal();

		TabItem[] myArray = get_TabItemArray();

		int count = myArray.length;
		for( int i = 0; i < count; i++)
		{
			if(
				myArray[i].StateInt != intParamState && 
				myArray[i].Tab.getRoot().getVisibility() == View.VISIBLE)
			{
				myArray[i].Tab.Hide();
			}
		}

		if(     intParamState == intPin)
		{
			this.tabPin.Show(); // bool2
		}
		else if(intParamState == intMenu)
		{
			this.tabMainMenu.Show();
		}
		else if(intParamState == intReference)
		{
			this.tabReference.Show();
		}
		else if(intParamState == intPointsList)
		{
			this.tabPointsList.Show();
		}
		else if(intParamState == intSelection)
		{
			this.tabModeSelection.Show();
		}
		else if(intParamState == intWaitMode)
		{
			this.tabWait.Show();
		}
		else if(intParamState == intPersonelListMode)
		{
			this.tabPersonelList.Show();
		}
		else if(intParamState == intPersonelInfo)
		{
			this.tabPersonelInfo.Show();
		}
		else if(intParamState == intCHECKIN_LIST)
		{
			this.tabCheckinList.Show();
		}



		} catch(Exception e)
		{
			Exception ex = e;
		}
	}

	public void onBackPressed()
	{
		State state = this.currentState;

		switch(state)
		{
			case PIN:
			case MAIN_MENU:{

		        Builder b = new android.app.AlertDialog.Builder(this.context);

		        MainActivityProxy ma = (MainActivityProxy)this.context;

		        Object o1 = b.setMessage("Вы действительно хотите выйти?");
		        Object o2 = b.setNegativeButton("Нет", null).setPositiveButton("Да", ma.get_onClickBackBtn() );
		        Object o3 = b.create();
				Object o4 = b.show();

			break;}

			case POINTS_LIST:
			case CHECKIN_LIST:
			case REFERENCE:{
				switchState(State.MAIN_MENU);
			break;}

			case MODE_SELECTION:{
				switchState(State.POINTS_LIST);
			break;}
			case WAIT_MODE:{
				switchState(State.MODE_SELECTION);
			break;}
			case PERSONEL_LIST_MODE:{
				switchState(State.MODE_SELECTION);
			break;}
			case PERSONEL_INFO:{
				switchState(State.WAIT_MODE);
			break;}
		}
	}





	public static enum Act { 
		StartOperation,
		Text,
		TimeoutException, ServerNotRespond, ServerNotAvailable, UnhandledException, 
		ServerAuthOk, LocalAuthOk, ServerAuthError, LocalAuthError, 
		SyncOk, SyncError, CheckinSuccess, CheckinSaveLocal, CheckinFailed,
		LoadNewVersionFailed, LoadNewVersionSuccess, CanNotCreateFileForLoading; }
	private HttpMessage get_HttpMessage(Act act)
	{
		return this.get_HttpMessage( act, null, -1);
	}
	private HttpMessage get_HttpMessage(Act act, String str)
	{
		return this.get_HttpMessage( act, str, -1);
	}
	private HttpMessage get_HttpMessage(Act act, String str, int time)
	{
		//if(_myRunnable == null)
		//{
			HttpMessage _myRunnable = new HttpMessage();
			_myRunnable.arg1 = this;
		//}
		_myRunnable.arg2 = act;
		_myRunnable.arg3 = str;
		_myRunnable.arg4 = time;

		return _myRunnable;
	}
	private class HttpMessage extends RunnableWithArgs { public void run() 
	{
		//try {
		//Thread.sleep(250L);
		//} catch (InterruptedException e) {
		//}

		//synchronized( _lockObj )
		//{
			UIHelper _ui = (UIHelper)this.arg1;
			Act _data = (Act)this.arg2;

			if( _data == Act.StartOperation )
			{
				_ui.mProgress = ProgressDialog.show( _ui.context, "", "Пожалуйста, подождите...", true );
			}
			else if( _data == Act.Text )
			{
				_ui.ToastCENTER( (String)this.arg3, (Integer)this.arg3 );
			}
			else
			{
				if(_ui.mProgress.isShowing())
				{
					_ui.mProgress.dismiss();
				}
				switch(_data)
				{
					case ServerAuthOk:{
						_ui.Toast("Cерверная авторизация \nпрошла успешно!"); //TALog.Log("Авторизация прошла успешно!");
					break;}
					case LocalAuthOk:{
						_ui.Toast("Локальная авторизация \nпрошла успешно!"); //TALog.Log("Авторизация прошла успешно!");
					break;}
					case ServerAuthError:{
						_ui.Toast("Супервайзер не найден на сервере!");
					break;}
					case LocalAuthError:{
						_ui.Toast("Супервайзер не найден на телефоне!");
					break;}


					case SyncOk:{
						_ui.Toast("Синхронизация завершена!" );
					break;}
					case SyncError:{
						_ui.Toast("На сервере отсутствует текущий супервайзер!" );
					break;}


					case CheckinSuccess:{
						_ui.Toast("Чекин прошёл успешно!");
					break;}
					case CheckinFailed:{
						_ui.Toast("соединение с интернетом отсутствует \n нажмите на кнопку синхронизации при появлении интернета");
					break;}
					case CheckinSaveLocal:{
						_ui.Toast(            "чекин сохранен локально \n нажмите на кнопку синхронизации при появлении интернета");
					break;}


					case TimeoutException:{
						_ui.Toast("Время ожидания ответа от сервера истекло!" );
					break;}
					case ServerNotRespond:{
						_ui.Toast("Сервет не отвечает!" );
					break;}
					case ServerNotAvailable:{
						_ui.Toast("отсутствует подключение к интернету,\nиспользуйте автономную работу" );
						// инета нет , чекин сохр локально, исп автоном работу
					break;}
					case UnhandledException:{
						_ui.Toast( (String)this.arg3, (Integer)this.arg4 );
					break;}
				}
			}
		//synchronized( _lockObj )
		//{
		//    _lockObj.notifyAll();
		//} // synchronized
	}}
	private void WaitUpdateUI(Runnable r)
	{
		this.get_RespondHandler().post(r);
		//synchronized( _lockObj ) { try { _lockObj.wait(); } catch (InterruptedException e) { } }
	}
	public void MsgFromBackground(Act act)
	{
		WaitUpdateUI(get_HttpMessage( act ));
	}
	public void MsgExceptionFromBackground(Exception e)
	{
		String str = ExceptionToMsg(e);
		WaitUpdateUI(get_HttpMessage( Act.UnhandledException, str, android.widget.Toast.LENGTH_LONG ));
	}
	//public void MsgUnknownExceptionFromBackground()
	//{
	//            String name = e.getClass().getName();
	//            int index = name.lastIndexOf(".");
	//            if(index != -1 )
	//            {
	//                String target = ".";
	//                String replacement = "";
	//                name = name.substring(index).replace(target, replacement);

	//                this.MsgExceptionFromBackground( name + "\n\n" + e.getMessage(), android.widget.Toast.LENGTH_LONG );
	//                //UIHelper.Instance()
	//            }
	//}
	public static String ExceptionToMsg(Exception e)
	{
				String res = e.getClass().getName();
				int index = res.lastIndexOf(".");
				if(index != -1 )
				{
					String target = ".";
					String replacement = "";
					res = res.substring(index).replace(target, replacement);
					res = "\t-"+res+"-" + "\n" + e.getMessage();
				}
		return res;
	}





	//************************************************************************************************
	//       LoadingProgressBar
	//************************************************************************************************
	public void MsgProgress(Act act)     { WaitUpdateUI(get_MsgProgress( act,                    null )); }
	public void MsgProgress(String str)  { WaitUpdateUI(get_MsgProgress( Act.Text,               str )); }
	public void MsgProgress(Exception e) { WaitUpdateUI(get_MsgProgress( Act.UnhandledException, ExceptionToMsg(e) )); }

	private MsgProgress get_MsgProgress(Act act){ return get_MsgProgress(act,null); }
	private MsgProgress get_MsgProgress(Act act, String str)
	{
		MsgProgress o = new MsgProgress();
		o.arg1 = this;
		o.arg2 = act;
		o.arg3 = str;

		return o;
	}
	private LoadingProgressBar _loadingProgressBar;
	private LoadingProgressBar get_LoadingProgressBar()
	{
		if(this._loadingProgressBar == null)
		{
			this._loadingProgressBar = new LoadingProgressBar();
			this._loadingProgressBar.Init(this.context, this.context.get_FragmentManager());
		}
		return this._loadingProgressBar;
	}
	private class MsgProgress extends RunnableWithArgs { public void run()
	{
		UIHelper _ui =   (UIHelper)this.arg1;
		Act      _data = (Act)     this.arg2;
		String   _str =  (String)  this.arg3;

		switch(_data)
		{
			case StartOperation:{
				_ui.get_LoadingProgressBar().Show();
				_ui.get_LoadingProgressBar().SetTitle("Обновление");
			break;}
			case Text:{
				_ui.get_LoadingProgressBar().AddMessage(_str);
			break;}
			case LoadNewVersionFailed:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("обновление не удалось,\nотсутствует интернет");
				//_ui.get_LoadingProgressBar().Message("обновление не удалось,\nотсутствует интернет");
			break;}
			case LoadNewVersionSuccess:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("обновление завершено");
				//_ui.get_LoadingProgressBar().Message("обновление завершено");
			break;}
			case CanNotCreateFileForLoading:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("не удаётся создать файл\nдля загрезки новой версии");
			break;}
			case UnhandledException:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast( (String)this.arg3 );
			break;}
		}
	}}

	private SetTitle get_SetTitle(String str) { SetTitle o = new SetTitle();o.arg1 = this;o.arg2 = str;return o; }
	private class SetTitle extends RunnableWithArgs { public void run()
	{
		((UIHelper)this.arg1).get_LoadingProgressBar().SetTitle((String)this.arg2);
	}}
	public void SetTitle(String str) { WaitUpdateUI(get_SetTitle(str)); }

	private AddMessage get_AddMessage(String str) { AddMessage o = new AddMessage();o.arg1 = this;o.arg2 = str;return o; }
	private class AddMessage extends RunnableWithArgs { public void run()
	{
		((UIHelper)this.arg1).get_LoadingProgressBar().AddMessage((String)this.arg2);
	}}
	public void AddMessage(String str) { WaitUpdateUI(get_AddMessage(str)); }




	
	public void ToastInUIThread(String str,int time)
	{
		WaitUpdateUI(get_HttpMessage( Act.Text, str, time));
	}
	public void Toast(String paramString)
	{
		Toast( paramString, android.widget.Toast.LENGTH_LONG);
	}
	public void Toast(String paramString, int duration)
	{
		Toast.makeText(this.context, paramString, duration).show();
	}
	public void ToastCENTER(String paramString, int duration)
	{
		Toast toast = Toast.makeText(this.context, paramString, duration);
		toast.setGravity(android.view.Gravity.CENTER, 0, 0);
		toast.show();
	}
	public void hideKeyboard()
	{
		(
			(InputMethodManager)this.context.getSystemService("input_method")
		)
		.hideSoftInputFromWindow(   this.context.getCurrentFocus().getWindowToken(),   2   );
	}





	//************************************************************************************************
	//       MessageBox
	//************************************************************************************************
	public void MessageBoxInUIThread(String message, 
		android.content.DialogInterface.OnClickListener onClickOk, android.content.DialogInterface.OnClickListener onClickNo)
	{
		WaitUpdateUI(get_MessageBox(message,onClickOk,onClickNo));
	}
	public messageBox get_MessageBox(String message, 
		android.content.DialogInterface.OnClickListener onClickOk, android.content.DialogInterface.OnClickListener onClickNo)
	{
	    messageBox m = new messageBox();
	    m.arg1 = this.context;
	    m.arg2 = message;
	    m.arg3 = onClickOk;
	    m.arg4 = onClickNo;
		return m;
	}
	class messageBox extends RunnableWithArgs{ public void run(){
	    Context context = (Context)this.arg1;
	    String message = (String)this.arg2;
	    android.content.DialogInterface.OnClickListener onClickOk = (android.content.DialogInterface.OnClickListener)this.arg3;
	    android.content.DialogInterface.OnClickListener onClickNo = (android.content.DialogInterface.OnClickListener)this.arg4;

		android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(   context   );
		b.setMessage(   message   );
		b.setPositiveButton("Ok",  onClickOk);
		if(onClickNo != null)
			b.setNegativeButton("Нет", onClickNo);
		b.create();
		b.show();
			
		int aaa = 9;
	}}





	//************************************************************************************************
	//       run in other thread and wait
	//************************************************************************************************
	private RunnableAndNotify get_RunnableAndNotify(Runnable runnable)//, Object obj
	{
	    RunnableAndNotify r = new RunnableAndNotify(); r.arg1 = runnable;// r.arg2 = obj; 
		return r;
	}
	class RunnableAndNotify extends RunnableWithArgs{ public void run(){
	    Runnable target = (Runnable)this.arg1;
	    //Object obj      =           this.arg2;
	    target.run();
		//synchronized( obj )
		//{
		//    obj.notifyAll();
		//    int aaa = 9;
		//    int aaa2 = aaa - 2;
		//} // synchronized
	}}
	public void RunAndWait(Runnable runnable)//, Object obj
	{
	    Thread t = new Thread(get_RunnableAndNotify(runnable),"-RunAndWait-");//, obj
		t.start();

		//synchronized( obj )
		//{
		//    try {
		//        obj.wait();
		//    } catch (InterruptedException e) {
		//        //...
		//    }
		//}
		try {
		t.join();
		} catch (InterruptedException e) {
			Exception ex = e;
		}

		int aaa = 9;
		int aaa2 = aaa - 2;
	}
	//public void RunInUIThreadAndWait(Runnable runnable, Object obj)
	//{
	//    this.serverRunnable = get_RunnableAndNotify(runnable, obj);
	//    this.get_RespondHandler().post(this.serverRunnable);

	//    synchronized( obj )
	//    {
	//        try {
	//            obj.wait();
	//        } catch (InterruptedException e) {
	//            //...
	//        }
	//    }
	//    int aaa = 9;
	//    int aaa2 = aaa - 2;
	//}





	//************************************************************************************************
	// 3 update screen from back-ground thread
	public void ShowScreenFromBackground(State state, long paramLong)
	{
		this.showScreenRunnable = get_onShowScreen(state);

		this.get_RespondHandler().postDelayed(this.showScreenRunnable, paramLong);
	}
	private onShowScreen get_onShowScreen(State state)
	{
		onShowScreen o = new onShowScreen();
		o.arg1 = this;
		o.arg2 = state;
		return o;
	}
	class onShowScreen extends RunnableWithArgs { public void run()
	{
		UIHelper ui = (UIHelper)this.arg1;
		State state = (State)this.arg2;

		ui.switchState(state);
	}}





	//************************************************************************************************
	//       show status bar
	//************************************************************************************************
	public void ShowStatusBar()
	{
		android.view.Window wnd = this.context.getWindow();

		wnd.clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	




	//*********************************************************************************************
	//       static constructors
	static
	{
		UIHelper.__instance = null;
	}
}