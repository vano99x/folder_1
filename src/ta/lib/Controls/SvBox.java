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
	//private String     _pointName;
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
		//this._pointName = null;
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
		this.__svModel.SvChanged_EventAdd(get_onAuthSV());
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
		this.__pointModel.set_CurrentPointApplied(get_onSetCurPt());
	}


	//*********************************************************************************************
	//*      public func

	//*********************************************************************************************
	//**     Event Handler
	private onSetCurPt get_onSetCurPt() { onSetCurPt o = new onSetCurPt(); o.arg1 = this; return o; }
	class   onSetCurPt extends RunnableWithArgs<Point,Boolean> { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		//Point p = this.arg;
		//_this._pointName = p.Name;
		UpdatePointTextView();
	}}

	private onAuthSV get_onAuthSV() { onAuthSV a = new onAuthSV(); a.arg1 = this; return a; }
	class   onAuthSV extends RunnableWithArgs<Personel,Object> { public void run()
	{
		if( this.arg != null)
		{
			SvBox _this = (SvBox)this.arg1;
			Personel p = this.arg;

			Tab.UpdateTextView( _this._labelLastName,  p.LastName);
			Tab.UpdateTextView( _this._labelName,      p.FirstName);
			Tab.UpdateTextView( _this._labelThirdName, p.ThirdName);
			
			_this.UpdatePointTextView();
		}
	}}


	private void UpdatePointTextView()
	{
		String ctrlStr = this._labelPoint.getText().toString();
		String str = null;

		Point point = this.__pointModel.get_CurrentPoint();
		if( point == null || point.Name == null ){
			str = "Выберите точку в настройках!";
		}else{
			str = point.Name;
		}

		if(ctrlStr == null || ! ctrlStr.equals(str)){
			Tab.UpdateTextView( this._labelPoint, str);
		}
	}

	//private onClearing get_onClearing() { onClearing o = new onClearing(); o.arg1 = this; return o; }
	//class onClearing extends RunnableWithArgs { public void run()
	//{
	//SvBox _this = (SvBox)this.arg1;
	//_this.HideNameBlock();
	//}}

	//private onClosing get_onClosing() { onClosing a = new onClosing(); a.arg1 = this; return a; }
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
	//**     Code behind override
	//@Override
	//public void Show()
	//{
	//super.Show();
	//UpdateCtrlData();
	//}
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
