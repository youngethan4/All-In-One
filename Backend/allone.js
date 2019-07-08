const express = require('express');
const app = express();
const port = 3000;
const mysql = require('mysql'); 

var con = mysql.createConnection({
  host: "localhost",
  user: "trippyacidcats",
  password: "danny15GAY!"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

app.get('/', (req, res) => res.send('Hello World!'))

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
