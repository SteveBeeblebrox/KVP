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

export default class KVP {
    #keys = [];
    #values = [];
    #pairs = {};
    #internalAdd(key, value) {
        if(!this.hasKey(key)) this.#keys.push(key);
        this.#values[this.#keys.indexOf(key)] = value;
        this.#pairs[Symbol.for(key)] = value;
    }
    #internalGet(key) {
        return this.#pairs[Symbol.for(key)];
    }
    #internalGetHelper(transformer, validator, key, fallback) {
        let validate = (transformed, value) => validator(transformed, value) ? transformed : fallback;
        let transform = (value) => [transformer(value), value];
        return this.hasKey(key) ? validate(...transform(this.#internalGet(key))) : fallback;
    }
    constructor(source) {
        let unexpectedTokens = [];
        let add = this.#internalAdd.bind(this);
        source.split(/\n/g).forEach((line, index) => 
            line
                .replace(/^$\n/g, '')
                .replace(/(?<!\$)!.*$/g, '')
                .replace(/^\s*?(.*?)(?<!\$):\s*?(?<!\$)(['"])(.*?)(?<!\$)\2\s*$/g, (str, match1, match2, match3) => (add(KVP.unescape(match1), KVP.unescape(match3)), ''))
                .replace(/^\s*?(.*?)(?<!\$):(.*?)$/g, (str, match1, match2) => (add(KVP.unescape(match1), KVP.unescape(match2.trim())), ''))
                .replace(/^$\n/g, '')
                .replace(/(.+)/g, (str, match1) =>
                    unexpectedTokens.push([match1, index])
                )
        );
        for(let [token, line] of unexpectedTokens)
            console.warn(`Unexpected token "${token}" in Key Value Pair on line ${line}`);
    }
    static parse(source) {
        return new KVP(source);
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
    getString(key, fallback = '') {
        return this.#internalGetHelper(v => v, t => true, key, fallback);
    }
    getBoolean(key, fallback = false) {
        return this.#internalGetHelper(v => v.trim().toLowerCase() === 'true', (t, v) => v.trim().toLowerCase() === 'true' || v.trim().toLowerCase() === 'false', key, fallback);
    }
    getInteger(key, fallback = 0) {
        return this.#internalGetHelper(v => parseInt(v.trim(), 10), t => !isNaN(t), key, fallback);
    }
    getDecimal(key, fallback = 0.0) {
        return this.#internalGetHelper(v => parseFloat(v.trim()), t => !isNaN(t), key, fallback);
    }
    getDate(key, fallback = new Date('January 1, 1970')) {
        return this.#internalGetHelper(v => new Date(v.trim()), t => !isNaN(t), key, fallback);
    }
    hasKey(key) {
        return this.#keys.includes(key);
    }
    keys() {
        return [...this.#keys];
    }
    values() {
        return [...this.#values];
    }
}
