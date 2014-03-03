package ta.lib.Controls;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ta.lib.*;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.Database.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.R;

public class SvBox extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	private TextView     _labelPoint;
	//private LinearLayout _NameBlock;
	private TextView     _labelLastName;
	private TextView     _labelName;
	private TextView     _labelThirdName;

	private ISupervisorModel __svModel;
	private IPointModel      __pointModel;

	public SvBox(Context mainActivity, ViewGroup rootView)
	{
		super( mainActivity, rootView, R.layout.sv_box, R.id.SvBox_RootId);
		this._engine = MainEngine.getInstance();

		this._labelPoint      = (TextView)    this.root.findViewById(R.id.SvBox_Point);
		//this._NameBlock       = (LinearLayout)this.root.findViewById(R.id.SvBox_NameBlockId);
		this._labelLastName   = (TextView)    this.root.findViewById(R.id.SvBox_LastName);
		this._labelName       = (TextView)    this.root.findViewById(R.id.SvBox_Name);
		this._labelThirdName  = (TextView)    this.root.findViewById(R.id.SvBox_ThirdName);

		//CheckBox checkBoxInternet = ((CheckBox)this.root.findViewById(R.id.checkBoxInternet));
		//checkBoxInternet.setOnClickListener(this);
		//checkBoxInternet.setTag(R.id.checkBoxInternet);

		Tab.Hide(this._labelPoint);
		//this.HideNameBlock();
		Tab.Hide(this._labelLastName);
		Tab.Hide(this._labelName);
		Tab.Hide(this._labelThirdName);

		// subscribe on event
		//this._engine.Clearing.Add(get_onClearing());
		//this._engine.Closing.Add(get_onClosing());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__svModel.OnSvAppliedEvt(get_onAuthSV());
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
		this.__pointModel.set_CurrentPointApplied(get_onSetCurPt());
	}


	//*********************************************************************************************
	//*      public func
	//public void ShowNameBlock()
	//{
	//Tab.Show(this._NameBlock);
	//}
	//public void HideNameBlock()
	//{
	//Tab.Hide(this._NameBlock);
	//}

	//*********************************************************************************************
	//**     Event Handler
	private onSetCurPt get_onSetCurPt() { onSetCurPt o = new onSetCurPt(); o.arg1 = this; return o; }
	class   onSetCurPt extends RunnableWithArgs<Point,Boolean> { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		Point p = this.arg;
		SvBox.UpdateTextView( _this._labelPoint,  p.Name);
	}}

	private onAuthSV get_onAuthSV() { onAuthSV a = new onAuthSV(); a.arg1 = this; return a; }
	class   onAuthSV extends RunnableWithArgs<Personel,Object> { public void run()
	{
		if( this.arg != null)
		{
			SvBox _this = (SvBox)this.arg1;
			Personel p = this.arg;

			//_this.ShowNameBlock();
			SvBox.UpdateTextView( _this._labelLastName,  p.LastName);
			SvBox.UpdateTextView( _this._labelName,      p.FirstName);
			SvBox.UpdateTextView( _this._labelThirdName, p.ThirdName);
			
			SvBox.UpdateTextView( _this._labelPoint, "Выберите точку в настройках!");
		}
	}}



	//*********************************************************************************************
	//**     private func
	private static void UpdateTextView(TextView ctrl, String text)
	{
		boolean aaa1 = text != null;
		boolean aaa2 = !("".equals(text));
		//int     aaa3 = text.length();

		if(aaa1 && aaa2)
		{
			if( ! Tab.IsShow(ctrl)) {
				Tab.Show(ctrl);
			}
			ctrl.setText(  text);
		}
		else
		{
			if(Tab.IsShow(ctrl)) {
				Tab.Hide(ctrl);
			}
		}
	}

	//private onClearing get_onClearing()
	//{
	//onClearing o = new onClearing();
	//o.arg1 = this;
	//return o;
	//}
	//class onClearing extends RunnableWithArgs { public void run()
	//{
	//SvBox _this = (SvBox)this.arg1;
	//_this.HideNameBlock();
	//}}

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

	//private void CheckBoxInternet_CheckedOrUnchecked(View ctrl)
	//{
	//boolean isChecked = ((CheckBox)ctrl).isChecked();
	//this._engine.set_IsIntenetDisable(isChecked);
	//}

	//*********************************************************************************************
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = ta.lib.operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				//case R.id.checkBoxInternet:{
				//CheckBoxInternet_CheckedOrUnchecked(ctrl);
				//break;}
			}
		}
	}
}
