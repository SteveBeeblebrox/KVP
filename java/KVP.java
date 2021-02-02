public class KVP {
	@FunctionalInterface
	private interface Transformer<T> {
		public <T> T accept(String value);
	}
	@FunctionalInterface
	private interface Validator<T> {
		public <T> boolean accept(T transformed, String value);
	}
	private java.util.LinkedHashMap<String, String> pairs = new java.util.LinkedHashMap<String, String>();
	private void internalAdd(String key, String value) {
		this.pairs.put(key, value);
	}
	private String internalGet(String key) {
		return this.pairs.get(key);
	}
	private <T> T internalGetHelper(Transformer transformer, Validator validator, String key, T fallback) {
		//TODO ...
		return null;
	}
	public KVP(String source) {
		//TODO ...
	}
	public static KVP parse(String source) {
		return new KVP(source);
	}
	public static String unescape(String string) {
		return string
			.replaceAll("\\$endline", "\n")
			.replaceAll("\\$([$!:'\"])", "$1");
	}
	public static String escape(String string) {
		return string
			.replaceAll("([$!:'\"])", "\\$$1")
			.replaceAll("\n", "$endline");
	}
	//TODO ...
	public boolean hasKey(String key) {
		return this.pairs.containsKey(key);
	}
	public String[] keys() {
		return (String[])this.pairs.keySet().toArray();
	}
	public String[] values() {
		return (String[])this.pairs.values().toArray();
	}
}
