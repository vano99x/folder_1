﻿package ta.Tabs.Settings;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import ta.lib.*;
import ta.lib.Common.*;
import ta.lib.DatePicker.MyDatePicker;
import ta.lib.tabui.Tab;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.Tabs.Settings.CurrentVersionServices.CurrentVersionLoadedEventClass;
import ta.timeattendance.R;

public class TabSettings extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	private CheckpointAdapter _checkpointAdapter;
	private ta.timeattendance.MainActivityProxy _mainActivity;
	private ListView _listView;
	private TextView  _labelCurrentVersion;

	private IPointModel __pointModel;
	private ICurrentVersionServices __currentVersionServices;


	public TabSettings(
		MainActivityProxy context, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(context, paramViewGroup, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance();
		this._mainActivity = context;
		
		Button referenceBtn = (Button)this.root.findViewById(R.id.Settings_ReferenceBtn_Id);
		referenceBtn.setOnClickListener(this);
		referenceBtn.setTag(new Object[]{R.id.Settings_ReferenceBtn_Id});
		
		Button updateBtn = (Button)this.root.findViewById(R.id.Settings_UpdateBtn_Id);
		updateBtn.setOnClickListener(this);
		updateBtn.setTag(new Object[]{R.id.Settings_UpdateBtn_Id});

		_labelCurrentVersion = (TextView)this.root.findViewById(R.id.Settings_CurrentVersionTextView_Id);

		this._listView = (ListView)this.root.findViewById(R.id.Settings_ListView_Id);
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );

		this.__currentVersionServices = Bootstrapper.Resolve( ICurrentVersionServices.class );
		onCVL aaa = get_onCurrentVersionLoaded();
		CurrentVersionLoadedEventClass bbb = this.__currentVersionServices.get_CurrentVersionLoaded();
		bbb.Add(aaa);
	}


	private void createListRouteAdapter(Point[] pointArray)
	{
		if (this._checkpointAdapter != null)
		{
			this._checkpointAdapter = null;
		}
		this._checkpointAdapter = new CheckpointAdapter(this.context, pointArray, this);
		this._listView.setAdapter(this._checkpointAdapter);
	}

	//*********************************************************************************************
	//**     Event Handler
	private onCVL get_onCurrentVersionLoaded() { onCVL o = new onCVL(); o.arg1 = this; return o; }
	class   onCVL extends RunnableWithArgs<String,Boolean> { public void run()
	{
		TabSettings _this = (TabSettings)this.arg1;
		_this._labelCurrentVersion.setText( this.arg );
	}}


	//*********************************************************************************************
	//**     Control Handler
	private void referenceBtn_Click()
	{
		UIHelper.Instance().switchState(MainActivity.State.REFERENCE);
	}

	private onClickMsb_Yes get_onClickMsb_Yes() { onClickMsb_Yes o = new onClickMsb_Yes(); o._this = this; return o; }
	  class onClickMsb_Yes implements OnClickListener { public TabSettings _this;public void onClick( DialogInterface dialogInterface, int pInt)
	{
		UpdateManager.LoadNewVersionAndRun(_this._mainActivity);
	}}
	private onClickMsb_No get_onClickMsb_No() { onClickMsb_No o = new onClickMsb_No(); o._this = this; return o; }
	  class onClickMsb_No implements OnClickListener { public TabSettings _this;public void onClick( DialogInterface dialogInterface, int pInt)
	{
		int aaa = 9;
	}}
	private void updateBtn_Click()
	{
		UIHelper.Instance().MessageBoxInUIThread(
			"Обновить?",
			get_onClickMsb_Yes(), 
			get_onClickMsb_No());
	}

	public void PointsListItem_Selected(Point point)
	{
		//this._engine.set_CurrentPointId(   ((Point)point).Id   );
		this.__pointModel.set_CurrentPoint(point);
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
	}



	//*********************************************************************************************
	//**     private func
	private void LoadAndShowCurrentVersion()
	{
		this.__currentVersionServices.LoadCurrentVersionNumber();
	}


	//*********************************************************************************************
	//**     Code behind override
	public void Show()
	{
		super.Show();
		Personel superviser = MainActivityProxy.get_SvModel().get_CurrentSuperviser();
		//Point [] pointArr   = Point.getBySuperviser( superviser.Id, this.context);
		superviser.get_Points(true, get_onLoadComplete(), this.context);

		LoadAndShowCurrentVersion();
	}

	private onLC get_onLoadComplete() { onLC o = new onLC(); o.arg1 = this; return o; }
	class onLC extends RunnableWithArgs { public void run()
	{
		TabSettings _this = (TabSettings)this.arg1;
		Personel superviser = MainActivityProxy.get_SvModel().get_CurrentSuperviser();
		Point [] pointArr = superviser.get_Points(false,null,null);
		_this.createListRouteAdapter(pointArr);
	}}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.Settings_ReferenceBtn_Id:{
					referenceBtn_Click();
				break;}
				case R.id.Settings_UpdateBtn_Id:{
					updateBtn_Click();
				break;}
				case R.id.PointsListItem_Id:{
					PointsListItem_Selected((Point)arr[1]);
				break;}
			}
		}
	}
}