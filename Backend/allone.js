var  express = require('express');
var  app = express();
var  port = 3000;
var  mysql = require('mysql');
var  bodyParser = require('body-parser');
var  url = require('url');
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

var conJSON = require('./app/pass.json');//(mysql);
//var o = conJSON.getConInfo;
//console.log(JSON.stringify(conJSON));

console.log("\n#########################################");
console.log(conJSON[0]);


//console.log("conJSON : "+o);
//console.log("Type of conJSON : "+typeof(o));
var con = mysql.createConnection(conJSON); //Try this later once I get a feel of node js.
//require('./app/routes/user.routes.js')(app, con);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))

app.post("/api/create/user", (req, res) => {
  console.log("\nNew user registering...");
  console.log(req.body);
  //var rq = url.parse(req.url, true);
  //console.log(rq);
  var jsonData = req.body;

  var username = jsonData.username;
  var password = jsonData.password;
  var email = jsonData.email;
  var fname = jsonData.firstname;
  var lname = jsonData.lastname;

  console.log(username);

  var  sql = "INSERT INTO users (username, password, email, firstname, lastname) VALUES ('";
  sql += username + "', '" + password + "', '" + email + "', '" + fname + "', '" + lname + "')";
  
  console.log(sql);
  	
  try{
    con.query(sql, function(err, result){
      if(err) throw err;
      console.log("registered!");
      var jsonReturn = {"message":"User registered."};
      res.json(jsonReturn);
    });
  }
  catch (err){
    console.log("Registering new user error.");
    var retJsonObj = {"message":"Rip"};
    res.json(retJsonObj);
  }
});
