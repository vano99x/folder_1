package ta.timeattendance.Models;

import ta.lib.*;
import ta.timeattendance.Models.*;
import ta.Database.*;
import ta.timeattendance.Services.*;

public class SettingSvModel implements ISettingSvModel
{
	private ISupervisorModel __svModel;
	private IPointModel      __pointModel;
	private IAppService __appService;

	public SettingSvModel()
	{
		this.PointSettingChanged = new PointSettingChangedEventClass();

		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__appService.get_Closing().Add(get_onClosing());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__svModel.SvChanged_EventAdd(get_onSvChanged());
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
	}



	//*********************************************************************************************
	//**     Event
	class PointSettingChangedEventClass extends Event<Point,Object> {}
	private PointSettingChangedEventClass PointSettingChanged;
	public void PointSettingChanged_Add(RunnableWithArgs runnable)
	{
		PointSettingChanged.Add(runnable);
	}



	//*********************************************************************************************
	//**     Event Handler
	private       onCls get_onClosing() { onCls o = new onCls(); o.arg1 = this; return o; }
	private class onCls extends RunnableWithArgs<Object,Object> { public void run()
	{
		SettingSvModel _this = (SettingSvModel)this.arg1;

		Point p = _this.__pointModel.get_CurrentPoint();
		if(p != null)
		{
			Personel sv = _this.__svModel.get_CurrentSuperviser();
			if(sv != null)
			{
				int id = _this.__svModel.get_CurrentSuperviser().Id;
				SettingSv.UpdatePoint(id, p.Id);
			}
		}
	}}

	private       onSvChanged get_onSvChanged() { onSvChanged o = new onSvChanged(); o.arg1 = this; return o; }
	private class onSvChanged extends RunnableWithArgs<Personel,Object> { public void run()
	{
		//Point p = _this.__pointModel.get_CurrentPoint();
		//_this.PointSettingChanged.RunEvent( point );

		SettingSvModel _this = (SettingSvModel)this.arg1;
		Personel sv = this.arg;
		Point p = _this.__pointModel.get_CurrentPoint();
		if(p == null)
		{
			Point point = SettingSv.SelectPoint(sv.Id);
			_this.__pointModel.set_CurrentPoint( point );
		}
	}}
}