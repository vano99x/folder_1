package ta.timeattendance.Models;

import ta.Database.*;
import ta.lib.*;

public interface IPointModel
{
	Point get_CurrentPoint();
	void set_CurrentPoint(Point p);

	void set_CurrentPointApplied(RunnableWithArgs runnable);
}