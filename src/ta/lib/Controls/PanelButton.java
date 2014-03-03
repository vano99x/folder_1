package ta.lib.Controls;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;

import ta.lib.*;
import ta.lib.tabui.Tab;
import ta.timeattendance.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.R;

public class PanelButton extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	private UIHelper   _uiHelper;
	private Button _SyncBtn;
	private Button _CheckinListBtn;
	private Button _SettingsBtn;
	private Button _FacilityInfoBtn;

	public PanelButton(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2, UIHelper uiHelper)
	{
		super( mainActivity, rootView, paramInt1, paramInt2);
		this._engine   = MainEngine.getInstance();
		this._uiHelper = uiHelper;
		this._uiHelper.set_CurrentStateChanged(get_CurrentStateChanged());

		this._SyncBtn =         (Button)this.root.findViewById(R.id.PnBtn_SyncBtn_Id);
		this._CheckinListBtn =  (Button)this.root.findViewById(R.id.PnBtn_CheckinListBtn_Id);
		this._SettingsBtn =     (Button)this.root.findViewById(R.id.PnBtn_SettingsBtn_Id);
		this._FacilityInfoBtn = (Button)this.root.findViewById(R.id.PnBtn_FacilityInfoBtn_Id);

		this._SyncBtn.setOnClickListener(this);
		this._CheckinListBtn.setOnClickListener(this);
		this._SettingsBtn.setOnClickListener(this);
		this._FacilityInfoBtn.setOnClickListener(this);

		this._SyncBtn.setTag(        R.id.PnBtn_SyncBtn_Id);
		this._CheckinListBtn.setTag( R.id.PnBtn_CheckinListBtn_Id);
		this._SettingsBtn.setTag(    R.id.PnBtn_SettingsBtn_Id);
		this._FacilityInfoBtn.setTag(R.id.PnBtn_FacilityInfoBtn_Id);

		this.Hide();
	}

	//*********************************************************************************************
	//**     Event Handler
	private onCurStChng get_CurrentStateChanged() { onCurStChng o = new onCurStChng(); o.arg1 = this; return o; }
	class   onCurStChng extends RunnableWithArgs<State,Boolean> { public void run()
	{
		PanelButton _this = (PanelButton)this.arg1;
		State state = this.arg;

		if(!state.equals(State.PIN))
		{
			if(_this.getRoot().getVisibility() == View.GONE)
			{
				_this.Show();
			}
		}
		else
		{
			if(_this.getRoot().getVisibility() == View.VISIBLE)
			{
				_this.Hide();
			}
		}

		//_this._labelPoint.setText( p.Name);
	}}



	//*********************************************************************************************
	//       Control Handler

	public void onClick_sync()
	{
		this._engine.Sync();
	}

	public void onClick_list()
	{
		UIHelper.Instance().switchState(MainActivity.State.CHECKIN_LIST);
	}

	public void onClick_Settings()
	{
		UIHelper.Instance().switchState(MainActivity.State.FLAG_SETTINGS);
	}

	public void onClick_FacilityInfoBtn()
	{
		UIHelper.Instance().switchState(MainActivity.State.FACILITY_INFO);
	}

	//*********************************************************************************************
	//**     Code behind override
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = ta.lib.operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.PnBtn_SyncBtn_Id:{
					onClick_sync();
				break;}
				case R.id.PnBtn_CheckinListBtn_Id:{
					onClick_list();
				break;}
				case R.id.PnBtn_SettingsBtn_Id:{
					onClick_Settings();
				break;}
				case R.id.PnBtn_FacilityInfoBtn_Id:{
					onClick_FacilityInfoBtn();
				break;}
			}
		}
	}
}