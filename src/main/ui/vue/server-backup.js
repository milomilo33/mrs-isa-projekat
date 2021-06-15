// server.js

var express = require('express');
var history = require('connect-history-api-fallback')
var path = require('path');
var serveStatic = require('serve-static');
var cors = require('cors');

app = express();

app.use(cors());

// app.use(serveStatic(path.join(__dirname, '/dist')))

// app.get('/style.css', function(req, res){
//     res.sendfile(__dirname + '/dist/css/style.css');
// });

app.use(express.static('dist'));

// Use a fallback for non-root routes (required for Vue router)
// NOTE: History fallback must be "used" before the static serving middleware!
app.use(history({
    // OPTIONAL: Includes more verbose logging
    verbose: true,
    htmlAcceptHeaders: ['text/html', 'application/xhtml+xml']
}))

// app.use(express.static(__dirname + "/dist"));

// // app.get(/.*/, (req, res) => {
// //     res.sendFile(__dirname + '/dist/index.html');
// // });

app.use(express.static('dist'));

// // this * route is to serve project on different page routes except root `/`
// app.get(/.*/, function (req, res) {
// 	res.sendFile(path.join(__dirname, '/dist/index.html'))
// })

var port = process.env.PORT || 8081;
app.listen(port);

console.log('server started ' + port);