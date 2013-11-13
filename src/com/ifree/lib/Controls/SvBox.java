package com.ifree.lib.Controls;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.ifree.lib.*;
import com.ifree.lib.tabui.*;
import com.ifree.timeattendance.*;
import com.ifree.Database.Personel;

import com.example.test6.R;

public class SvBox extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	private LinearLayout _NameBlock;
	private TextView     _labelLastName;
	private TextView     _labelName;
	private TextView     _labelThirdName;

	public SvBox(Context mainActivity, ViewGroup rootView)
	{
		super( mainActivity, rootView, R.layout.sv_box, R.id.SvBox_RootId);
		this._engine = MainEngine.getInstance();

		this._NameBlock       = (LinearLayout)this.root.findViewById(R.id.SvBox_NameBlockId);
		this._labelLastName   = (TextView)    this.root.findViewById(R.id.SvBox_LastName);
		this._labelName       = (TextView)    this.root.findViewById(R.id.SvBox_Name);
		this._labelThirdName  = (TextView)    this.root.findViewById(R.id.SvBox_ThirdName);

		CheckBox checkBoxInternet = ((CheckBox)this.root.findViewById(R.id.checkBoxInternet));
		checkBoxInternet.setOnClickListener(this);
		checkBoxInternet.setTag(R.id.checkBoxInternet);
		
		// subscribe on event
		this._engine.AuthenticateSVCompleteEvent.Add(get_onAuthenticateSVHandler());
		this._engine.Clearing.Add(get_onClearing());
		//this._engine.Closing.Add(get_onClosing());
	}


	//*********************************************************************************************
	//*      public func
	public void ShowNameBlock()
	{
		Tab.Show(this._NameBlock);
	}
	public void HideNameBlock()
	{
		Tab.Hide(this._NameBlock);
	}

	//*********************************************************************************************
	//       Event Handler
	private onAuthenticateSVHandler get_onAuthenticateSVHandler()
	{
		onAuthenticateSVHandler a = new onAuthenticateSVHandler();
		a.arg1 = this;
		return a;
	}
	class onAuthenticateSVHandler extends RunnableWithArgs { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		
		Object[] resultArr = (Object[])this.result;
		boolean result = ((Boolean)resultArr[0]).booleanValue();

		if(result)
		{
			_this.ShowNameBlock();
			Personel p = (Personel)resultArr[1];
			_this._labelLastName.setText(  p.LastName);
			_this._labelName.setText(      p.FirstName);
			_this._labelThirdName.setText( p.ThirdName);
		}
	}}

	private onClearing get_onClearing()
	{
		onClearing o = new onClearing();
		o.arg1 = this;
		return o;
	}
	class onClearing extends RunnableWithArgs { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		_this.HideNameBlock();
	}}

	//private onClosing get_onClosing()
	//{
	//    onClosing a = new onClosing();
	//    a.arg1 = this;
	//    return a;
	//}
	//class onClosing extends RunnableWithArgs { public void run()
	//{
	//    SvBox _this = (SvBox)this.arg1;
	//    _this.Hide();
	//}}



	//*********************************************************************************************
	//       Control Handler

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
		        case R.id.checkBoxInternet:{
		            CheckBoxInternet_CheckedOrUnchecked(ctrl);
		        break;}
		    }
		}
	}
}
