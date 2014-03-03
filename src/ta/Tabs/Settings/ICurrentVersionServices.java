package ta.Tabs.Settings;

public interface ICurrentVersionServices
{
	void LoadCurrentVersionNumber();
	
	ta.Tabs.Settings.CurrentVersionServices.CurrentVersionLoadedEventClass get_CurrentVersionLoaded();
}