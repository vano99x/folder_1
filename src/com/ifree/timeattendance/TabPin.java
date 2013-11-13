package com.ifree.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.ifree.timeattendance.MainActivity;
import com.ifree.lib.*;
import com.ifree.lib.tabui.Tab;
//import com.ifree.lib.tabui.TabActionListener;

import com.example.test6.R;



public class TabPin extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	private String mPin;
	private Button continue_button;
	private EditText mPinEditText;
	private boolean _isBisy;

	public TabPin(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2)
	{
		super( mainActivity, rootView, paramInt1, paramInt2);
		//TALog.Log("===========TabPin=================:" + this);
		this._engine = MainEngine.getInstance();
		this._isBisy = false;

		// ctrls
		this.continue_button = ((Button)this.root.findViewById(R.id.continue_button));
		this.continue_button.setOnClickListener(this);
		continue_button.setTag(R.id.continue_button);

		this.mPinEditText = ((EditText)this.root.findViewById(R.id.pin_edit));

		//CheckBox checkBoxInternet = ((CheckBox)this.root.findViewById(R.id.checkBoxInternet));
		//checkBoxInternet.setOnClickListener(this);
		//checkBoxInternet.setTag(R.id.checkBoxInternet);
		
		// subscribe on event
		this._engine.AuthenticateSVCompleteEvent.Add(get_onAuthenticateSVHandler());
		this._engine.Clearing.Add(get_onClearing());
	}



	//*********************************************************************************************
	//       Event Handler
	private onAuthenticateSVHandler get_onAuthenticateSVHandler()
	{
		onAuthenticateSVHandler a = new onAuthenticateSVHandler(); return a;
	}
	class onAuthenticateSVHandler extends RunnableWithArgs { public void run()
	{
		Object[] resultArr = (Object[])this.result;
		boolean result = ((Boolean)resultArr[0]).booleanValue();
		//boolean result = (Boolean)resultArr[0];

		if(result)
		{
			UIHelper.Instance().switchState(MainActivity.State.MAIN_MENU);
		}
	}}
	private onClearing get_onClearing()
	{
		onClearing o = new onClearing(); o.arg1 = this; return o;
	}
	class onClearing extends RunnableWithArgs { public void run()
	{
		TabPin _this = (TabPin)this.arg1;
		_this.mPinEditText.setText("");
	}}


	//*********************************************************************************************
	//       Control Handler
	private void continue_button_Click()
	{
		if(this._isBisy == false)
		{
			this._isBisy = true;

			this.mPin = this.mPinEditText.getText().toString().trim();
			//this.listener.doAction(this, CHECK_PIN_ACTION, this.mPin);

			if (this.mPin == "")//if (str.isEmpty()) //add
			{
				UIHelper.Instance().Toast("Неверный пин-код!");
			}
			else
			{
				UIHelper.Instance().hideKeyboard();
				//TALog.Log("doAction mEngine = " + this.mEngine);
				this._engine.AuthenticateSV_2( this.mPin );
			}

			this._isBisy = false;
		}
	}

	private void CheckBoxInternet_CheckedOrUnchecked(View ctrl)
	{
		boolean isChecked = ((CheckBox)ctrl).isChecked();

		this._engine.set_IsIntenetDisable(isChecked);
	}

	//*********************************************************************************************
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = com.ifree.lib.operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.continue_button:{
		            continue_button_Click();
		        break;}
		        case R.id.checkBoxInternet:{
		            CheckBoxInternet_CheckedOrUnchecked(ctrl);
		        break;}
		    }
		}
	}
}