package es.upm.grise.profundizacion2018.tema5;

public class Environment {

	public static String APP_HOME  = "APP_HOME";

	private String env;

	public Environment(String env) {
		this.env = env;
	}

	public String getEnvPath() {
		if (env.equals("")) {
			return "./";
		} else {
			return System.getenv(env);
		}
	}

}
