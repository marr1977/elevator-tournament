package hallerdal.elevator;

public class Log {

	public static boolean logEnabled = true;
	
	public static void info(String message, Object... args) {
		logEnabled = false;
		if (!logEnabled) {
			return;
		}
		System.out.println(String.format(message, args));
	}
	
	
}
