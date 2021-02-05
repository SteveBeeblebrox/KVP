/*
MIT License

Copyright (c) 2021 SteveBeeblebrox

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

public class KVP {
	private java.util.LinkedHashMap<String, String> pairs = new java.util.LinkedHashMap<String, String>();
	private void internalAdd(String key, String value) {
		this.pairs.put(key, value);
	}
	private String internalGet(String key) {
		return this.pairs.get(key);
	}
	private <T> T internalGetHelper(java.util.function.Function<String, T> transformer, java.util.function.BiFunction<T, String, Boolean> validator, String key, T fallback) {
		if(this.hasKey(key)) {
			String value = this.internalGet(key);
			if(value != null) {
				T transformed = (T)transformer.apply(value);
				if(validator.apply(transformed, value)) 
					return transformed;
			}
		}
		return fallback;
	}
	private static String internalSmartReplaceAll(String text, String pattern, java.util.function.Function<java.util.regex.MatchResult, String> replacer) {
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(text);
		StringBuffer result = new StringBuffer();
		while(matcher.find())
			matcher.appendReplacement(result, replacer.apply(matcher.toMatchResult()));
		matcher.appendTail(result);
		return result.toString();
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
