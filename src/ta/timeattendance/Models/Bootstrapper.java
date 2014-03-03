package ta.timeattendance.Models;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import ta.lib.*;
import ta.lib.operator.*;

public class Bootstrapper
{
	private ArrayList<TypeItem> _typeItems;

	private Bootstrapper()
	{
		_typeItems = new ArrayList<TypeItem>(9);
		_typeItems.add(new TypeItem("ISupervisorModel", SupervisorModel.class, true));
		_typeItems.add(new TypeItem("IPointModel",      PointModel.class,      true));
		_typeItems.add(new TypeItem("ICurrentVersionServices",      ta.Tabs.Settings.CurrentVersionServices.class,      true));
	}

	private static Bootstrapper _instance;
	private static Bootstrapper Instance()
	{
		if(Bootstrapper._instance == null)
		{
			Bootstrapper._instance = new Bootstrapper();
		}
		return Bootstrapper._instance;
	}

	public static <T> T Resolve(Class<T> _interface)
	{
		String temp = _interface.getName();
		int index = temp.lastIndexOf(".");
		if(index != -1 )
		{
			temp = temp.substring(index).replace(".", "");
		}
		final String className = temp;

		T instance = null;

		ArrayList<TypeItem> items = Bootstrapper.Instance()._typeItems;

		ArrayList<TypeItem> typeItems = 
			operator.Where(
				items,
				new WhereRunnable<TypeItem,TypeItem>() { public TypeItem run(TypeItem item)
				{
					int aaa = 9;
					String name = className;
					String str =  item.marker;
					TypeItem result = null;
					
					boolean res = str.equals(name);
					if(res == true) { 
						result = item;
					}
					return result;
				}}
			);

		if(typeItems.size() != 0)
		{
			TypeItem typeItem = typeItems.get(0);
			instance = (T)typeItem.GetOrCreateInstance();
		}
		return instance;
	}

	static
	{
		Bootstrapper._instance = null;
	}
}
