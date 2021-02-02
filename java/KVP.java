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
	public String getString(String key) { return this.getString(key, ""); }
	public String getString(String key, String fallback) {
		return this.<String>internalGetHelper(v -> v, (t, v) -> true, key, fallback);
	}
	public Boolean getBoolean(String key) { return this.getBoolean(key, Boolean.FALSE); }
	public Boolean getBoolean(String key, Boolean fallback) {
		return this.<Boolean>internalGetHelper(v -> v.trim().equalsIgnoreCase("true"), (t, v) -> v.trim().equalsIgnoreCase("true") || v.trim().equalsIgnoreCase("false"), key, fallback);
	}
	public Integer getInteger(String key) { return this.getInteger(key, new Integer(0)); }
	public Integer getInteger(String key, Integer fallback) {
		return this.<Integer>internalGetHelper(v -> {
			try {
				return Integer.valueOf(v.trim());
			} catch(NumberFormatException exception) {
				return null;
			}
		}, (t, v) -> t != null, key, fallback);
	}
	public Float getDecimal(String key) { return this.getDecimal(key, new Float(0)); }
	public Float getDecimal(String key, Float fallback) {
		return this.<Float>internalGetHelper(v -> {
			try {
				return Float.valueOf(v.trim());
			} catch(NumberFormatException exception) {
				return null;
			}
		}, (t, v) -> t != null, key, fallback);
	}
	public java.util.Date getDate(String key) { return this.getDate(key, new java.util.Date("January 1, 1970")); }
	public java.util.Date getDate(String key, java.util.Date fallback) {
		return this.<java.util.Date>internalGetHelper(v -> {
			try {
				return new java.util.Date(v.trim());
			} catch(IllegalArgumentException exception) {
				return null;
			}
		}, (t, v) -> t != null, key, fallback);
	}
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
