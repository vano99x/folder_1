package ta.timeattendance;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import ta.lib.*;
import ta.lib.tabui.*;

import ta.timeattendance.R;

public class TabMainMenu extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	ta.timeattendance.MainActivityProxy _mainActivity;

	public TabMainMenu(ta.timeattendance.MainActivityProxy paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2); //TALog.Log("===========TabMainMenu=================:" + this);
		this._engine = MainEngine.getInstance();
		this._mainActivity = paramContext;

		RelativeLayout start = (RelativeLayout)this.root.findViewById(R.id.MainMenu_StartBtn_Id);
		start.setOnClickListener(this);
		start.setTag(R.id.MainMenu_StartBtn_Id);

		RelativeLayout sync = (RelativeLayout)this.root.findViewById(R.id.MainMenu_SyncBtn_Id);
		sync.setOnClickListener(this);
		sync.setTag(R.id.MainMenu_SyncBtn_Id);

		RelativeLayout list = (RelativeLayout)this.root.findViewById(R.id.MainMenu_CheckinListBtn_Id);
		list.setOnClickListener(this);
		list.setTag(R.id.MainMenu_CheckinListBtn_Id);

		Button referenceBtn = (Button)this.root.findViewById(R.id.MainMenu_ReferenceBtn_Id);
		referenceBtn.setOnClickListener(this);
		referenceBtn.setTag(R.id.MainMenu_ReferenceBtn_Id);

		Button updateBtn = (Button)this.root.findViewById(R.id.MainMenu_UpdateBtn_Id);
		updateBtn.setOnClickListener(this);
		updateBtn.setTag(R.id.MainMenu_UpdateBtn_Id);

		RelativeLayout facilityInfoBtn = (RelativeLayout)this.root.findViewById(R.id.MainMenu_FacilityInfoBtn_Id);
		facilityInfoBtn.setOnClickListener(this);
		facilityInfoBtn.setTag(R.id.MainMenu_FacilityInfoBtn_Id);
	}



	//*********************************************************************************************
	//*      Ctrl Handler
	public void onClick_start()
	{
		//UIHelper.Instance().switchState(MainActivity.State.POINTS_LIST);
	}

	public void onClick_sync()
	{
		this._engine.Sync();
	}

	public void onClick_list()
	{
		UIHelper.Instance().switchState(MainActivity.State.CHECKIN_LIST);
	}

	public void onClick_ReferenceBtn()
	{
		UIHelper.Instance().switchState(MainActivity.State.REFERENCE);
	}

	public void onClick_UpdateProgramm()
	{
		//Intent intent = new Intent(Intent.ACTION_VIEW);
		//intent.setDataAndType(
		//    Uri.fromFile(
		//        new File(Environment.getExternalStorageDirectory() + "/APPS/" + "AnyName.apk")
		//    ),
		//    "application/vnd.android.package-archive"
		//);
		//startActivity(intent);

		//String var2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		//this._file = new File(var2 + "/" + "test6_red1.apk");
		//if( this._file.exists() )
		//{
			UIHelper.Instance().MessageBoxInUIThread(
				"Обновить?",
				get_onClickMessageBox_Yes(), 
				get_onClickMessageBox_No());
		//}
	}

	public void onClick_FacilityInfoBtn()
	{
		UIHelper.Instance().switchState(MainActivity.State.FACILITY_INFO);
	}
	
	//private File _file;
	private
	onClickOkMessageBox_Yes       get_onClickMessageBox_Yes() { onClickOkMessageBox_Yes o = new onClickOkMessageBox_Yes(); o._this = this; return o; }
	                              class onClickOkMessageBox_Yes implements OnClickListener { public TabMainMenu _this;public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	{
		UpdateManager.LoadNewVersionAndRun(_this._mainActivity);
	}}
	private
	onClickOkMessageBox_No        get_onClickMessageBox_No() { onClickOkMessageBox_No o = new onClickOkMessageBox_No(); o._this = this; return o; }
	                              class onClickOkMessageBox_No implements OnClickListener { public TabMainMenu _this;public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	{
		int aaa = 9;
	}}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.MainMenu_StartBtn_Id:{
		        	onClick_start();
		        break;}
		        case R.id.MainMenu_SyncBtn_Id:{
		            onClick_sync();
		        break;}
		        case R.id.MainMenu_CheckinListBtn_Id:{
		            onClick_list();
		        break;}
		        case R.id.MainMenu_ReferenceBtn_Id:{
		            onClick_ReferenceBtn();
		        break;}
		        case R.id.MainMenu_UpdateBtn_Id:{
		            onClick_UpdateProgramm();
		        break;}
		        case R.id.MainMenu_FacilityInfoBtn_Id:{
		            onClick_FacilityInfoBtn();
		        break;}
		    }
		}
	}
}