/**
 * Created by lispa on 15/10/2017.
 */

class teste {
	private static final teste ourInstance = new teste();

	static teste getInstance() {
		return ourInstance;
	}

	private teste() {
	}
}
