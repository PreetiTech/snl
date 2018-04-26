package snlTest;

public final class Logger {
	private Logger() {
	}

	public static void log(Object obj) {
		System.out.println(obj);
	}

	public static void error(Object obj) {
		System.err.println(obj);
	}
}
