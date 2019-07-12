var  express = require('express');
var  app = express();
var  port = 3000;
var  mysql = require('mysql');
var  bodyParser = require('body-parser');
var  url = require('url');
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

var con = mysql.createConnection({
  host: "localhost",
  user: "trippyacidcats",
  password: "danny15GAY!",
  database: "allone"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

//Try this later once I get a feel of node js.
//require('./app/routes/user.routes.js')(app, con);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))

app.post("/api/register", (req, res) => {
  console.log("\nNew user registering...");
  var rq = url.parse(req.url, true);
  var jsonData = JSON.parse(rq.querry.o);

  var username = "" + jsonData.username;
  var password = "" + jsonData.password;
  var email = "" + jsonData.email;
  var fname = "" + jsonData.fname;
  var lname = "" + jsonData.lname;

  var  sql = "INSERT INTO users (username, password, email, firstname, lastname) VALUES ('";
  sql += username + "', '" + password + "', '" + email + "', '" + firstname + "', '" + lastname + "')";

  try{
    con.query(sql, function(err, result){
      if(err) throw err;
      console.log("registered!");
      var jsonReturn = {message:"User registered."};
      res.json(jsonReturn);
    });
  }
  catch (err){
    console.log("Registering new user error.");
  }
}
