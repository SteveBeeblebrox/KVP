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
