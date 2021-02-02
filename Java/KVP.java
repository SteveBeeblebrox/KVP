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
