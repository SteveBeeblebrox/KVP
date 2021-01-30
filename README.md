# KVP
A simple key value pair data format
## Format
+ Single Line Comments
  + Anything following a `!` is ignored
+ Key Value Pairs
  + Pairs are defined on their own line and follow the pattern `key: value`
  + Keys and values can contain any character except `:`, `!`, `"`, or `'`
  + Whitespace is trimmed from values so `key: value` and `key:value` both have a value of just `value`
  + To keep leading or trailing whitepace, wrap the value in single or double quotes (`key:" value "` or `key:' value '`)
+ Escaping Characters
