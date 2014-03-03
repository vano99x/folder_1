package ta.timeattendance.Models;

import ta.lib.*;
import ta.Database.*;

public interface ISupervisorModel
{
	Personel get_CurrentSuperviser();
	void set_CurrentSuperviser(Personel p);

	void OnSvAppliedEvt(RunnableWithArgs runnable);
}