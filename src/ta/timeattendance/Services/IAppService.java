package ta.timeattendance.Services;

public interface IAppService
{
	ta.timeattendance.Services.AppService.ClosingEventClass get_Closing();
	void ClosingRunEvent();
	ta.timeattendance.Services.AppService.LogoutEventClass get_Logout();
	void LogoutRunEvent();
}