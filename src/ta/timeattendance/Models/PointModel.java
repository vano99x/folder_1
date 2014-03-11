package ta.timeattendance.Models;

import ta.lib.*;
import ta.lib.DatePicker.SelectedDateEventArgs;
import ta.Database.*;

public class PointModel implements IPointModel
{
	private ISupervisorModel __svModel;

	public PointModel()
	{
		this.CurrentPointApplied = new CurrentPointAppliedEventClass();
		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
	}



	//*********************************************************************************************
	//**     Event
	class   CurrentPointAppliedEventClass extends Event<Point,Boolean> {}
	private CurrentPointAppliedEventClass CurrentPointApplied;
	@Override
	public void set_CurrentPointApplied(RunnableWithArgs runnable)
	{
		this.CurrentPointApplied.Add(runnable);
	}



	//*********************************************************************************************
	//**     Event Handler

	//*********************************************************************************************
	//**     Property
	private Point __currentPoint;
	@Override
	public Point get_CurrentPoint()
	{
		return this.__currentPoint;
	}
	@Override
	public void set_CurrentPoint(Point p)
	{
		this.__currentPoint = p;

		if(this.__currentPoint == null)
		{
		}
		else
		{
			this.CurrentPointApplied.RunEvent( this.__currentPoint );
		}
	}
}