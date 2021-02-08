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

class KVPObjectBuilder extends KVP {
    static build(source, pattern) {
        let result = {};
        if(typeof source === 'string' || source instanceof String)
            source = new KVP(source);
        for(let property in pattern)
            result[property] = pattern[property](source, property);
        return result;
    }
    build(pattern) {
        return KVPObjectBuilder.build(this, pattern);
    }
    static parse(source) {
        return new KVPObjectBuilder(source);
    }
    static unescape(string) {
        return string
            .replace(/\$endline/g, '\n')
            .replace(/\$([$!:'"])/g, '$1');
    }
    static escape(string) {
        return string
            .replace(/([$!:'"])/g, '$$$1')
            .replace(/\n/g, '$endline');
    }
    static getString(fallback = '') {
        return (kvp, key) => kvp.getString(key, fallback);
    }
    static getBoolean(fallback = false) {
        return (kvp, key) => kvp.getBoolean(key, fallback);
    }
    static getInteger(fallback = 0) {
        return (kvp, key) => kvp.getInteger(key, fallback);
    }
    static getDecimal(fallback = 0.0) {
        return (kvp, key) => kvp.getDecimal(key, fallback);
    }
    static getDate(fallback = new Date('January 1, 1970')) {
        return (kvp, key) => kvp.getDate(key, fallback);
    }
}
