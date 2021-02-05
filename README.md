![KVP Icon](assets/kvp.png "KVP Icon")
# KVP
A simple key value pair data format
## Implementations
+ [JavaScript](https://github.com/SteveBeeblebrox/KVP/tree/master/JavaScript)
  + Normal
  + Module
+ [Java 8+](https://github.com/SteveBeeblebrox/KVP/tree/master/Java)
## Format
+ Single Line Comments
  + Anything following a `!` is ignored
+ Key Value Pairs
  + Pairs are defined on their own line and follow the pattern `key: value`
  + Keys and values can contain any character except `:`, `!`, `"`, or `'`
  + Whitespace is trimmed from values so `key: value` and `key:value` both have a value of just `value`
  + To keep leading or trailing whitepace, wrap the value in single or double quotes (`key:" value "` or `key:' value '`)
+ Escaping Characters
  + The following characters may be escaped when needed: `$`, `!`, `:`, `"`, `'`, and `\n`
  + For `$`, `!`, `:`, `"`, or `'`, simply type a `$` before the character to be escaped
  + To escape a newline (`\n`), use `$endline` instead
  + Characters in keys and values can be escaped; however, not all occurrences of special characters need to be escaped
    + Any `:` in the value do not need to be escaped
    + Quotes in the key do not need to be escaped
## Miscellaneous
### Images
There are several images inside of the `assets` folder that can be used for file icons or other references to KVP parsing or support within a program.
