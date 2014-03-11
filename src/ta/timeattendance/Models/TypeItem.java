package ta.timeattendance.Models;

public class TypeItem
{
	public  String  marker;
	public  Class   type;
	private boolean isSengleton;
	private Object _obj;

	public TypeItem(String _marker, Class _type, boolean _isSengleton)
	{
		marker      = _marker;
		type        = _type;
		isSengleton = _isSengleton;
		_obj = null;
	}

	public Object GetOrCreateInstance()
	{
		Object result = null;

		if(isSengleton)
		{
			if(_obj == null)
			{
				try{
					_obj = type.newInstance();
				}catch(Exception e){
					Exception ex = e;
				}
			}
			result = _obj;
		}
		else
		{
			try{   result = type.newInstance();   }catch(Exception e){}
		}
		return result;
	}

	public void Clear()
	{
		this._obj = null;
	}
}