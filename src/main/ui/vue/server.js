// server.js

var express = require('express');
var history = require('connect-history-api-fallback')
var path = require('path');
var serveStatic = require('serve-static');

app = express();

// Use a fallback for non-root routes (required for Vue router)
// NOTE: History fallback must be "used" before the static serving middleware!
app.use(history({
    // OPTIONAL: Includes more verbose logging
    verbose: true
}))

app.use(serveStatic(__dirname + "/dist"));

var port = process.env.PORT || 8081;
app.listen(port);

console.log('server started ' + port);