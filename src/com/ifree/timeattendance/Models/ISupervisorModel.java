package com.ifree.timeattendance.Models;

import com.ifree.lib.*;
import com.ifree.Database.*;

public interface ISupervisorModel
{
	Personel get_CurrentSuperviser();
	void set_CurrentSuperviser(Personel p);

	void set_CurrentSuperviserApplied(RunnableWithArgs runnable);
}