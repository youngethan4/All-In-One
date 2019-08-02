var  express = require('express');
var  app = express();
var  port = 3000;
var  mysql = require('mysql');
var  bodyParser = require('body-parser');
var  url = require('url');
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

var conJSON = require('./app/pass.json');

var con = mysql.createConnection(conJSON); 

//Try this later once I get a feel of node js.
//require('./app/routes/user.routes.js')(app, con);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))

app.post("/api/create/user", (req, res) => {
  console.log("\nNew user registering...");

  var jsonData = req.body;
  
  var username = jsonData.username;
  var password = jsonData.password;
  var email = jsonData.email;
  var fname = jsonData.firstname;
  var lname = jsonData.lastname;

  var  sql = "INSERT IGNORE INTO users (username, password, email, firstname, lastname) VALUES ('";
  sql += username + "', '" + password + "', '" + email + "', '" + fname + "', '" + lname + "')";

      try{
        con.query(sql, function(err, result){
          if(err) throw err;
          console.log("registered!");
          console.log("id = "+result.insertId);
          var jsonReturn = {"userid": result.insertId};
          res.json(jsonReturn);
        });
      }
      catch (err){
        console.log("Registering new user error.");
        var retJsonObj = {"userid":0};
        res.json(retJsonObj);
      }
});

app.post("/api/login/user", (req, res) => {
  console.log("\nUser logging in...");

  var jsonData = req.body;
  
  var email = jsonData.email;
  var password = jsonData.password;

  var sql = "SELECT * FROM users WHERE email = '";
  sql += email + "'";
  
  try{
    con.query(sql, function(err, result){
	    if(err) throw err;

	    if (result[0] == null) {
		    console.log("No email exists.");
		    var jsonReturn = {"userid" : 0};
		    res.json(jsonReturn);
	    }
	    else {
	            var checkpass = result[0].password;
	    
		    if(password === checkpass){
		      console.log("logged in " + result[0].userid);
		      var jsonReturn = {"userid" : result[0].userid};
		      res.json(jsonReturn);
	            } else {
		      console.log("Password incorrect");
	              var jsonReturn = {"userid" : 0};
	              res.json(jsonReturn);
	            }
	    }
    });
  }
  catch(err){
	  console.log("Login error.");
	  var jsonReturn = {"userid" : 0};
          res.json(jsonReturn);
  }
});
