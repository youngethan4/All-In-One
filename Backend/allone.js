var  express = require('express');
var  app = express();
var  port = 3000;
var  mysql = require('mysql');
var  bodyParser = require('body-parser');
var  url = require('url');
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

var conJSON = require('./app/pass.json');

var con;

function connectDB(){
	console.log("Database connected.");
	con = mysql.createConnection(conJSON);
	//con.on('error', connectDB());
}
connectDB();

//Try this later once I get a feel of node js.
//require('./app/routes/user.routes.js')(app, con);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))

/**
 *Request to create a new user.
 *Returns newly created userid.
 */
app.post("/api/create/user", (req, res) => {
  	console.log("\nNew user registering...");

  	var jsonData = req.body;
  	var username = jsonData.username;
  	var password = jsonData.password;
  	var email = jsonData.email;
  	var fname = jsonData.firstname;
  	var lname = jsonData.lastname;
	  var icon = jsonData.icon;

  	var  sql = "INSERT IGNORE INTO users (username, password, email, firstname, lastname, icon) VALUES ('";
  	sql += username + "', '" + password + "', '" + email + "', '" + fname + "', '" + lname + "', " + icon + ")";

    var userid = null;

      	try{
        	con.query(sql, function(err, result){
          	if(err) throw err;
          	console.log("registered!");
						userid = result.insertId;
          	console.log("id = "+userid);
          	var jsonReturn = {"userid": userid};
          	res.json(jsonReturn);
        	});
      }
      catch (err){
        	console.log("Registering new user error.");
        	var retJsonObj = {"userid":0};
        	res.json(retJsonObj);
      }

			var wait = setTimeout( function() {
			  sql += "INSERT INTO high_scores (userid, time20, time30, moves20, moves30) VALUES("
				        + userid + "," + null + "," + null + "," + null +"," + null +")";
			  console.log(sql);

        if (userid != null){
				  try {
					  con.query(sql, function(err, result){
						  if(err) throw err;
						  console.log("Setup high scores!");
					  });
				  }
				  catch(err) {
					  console.log("Setup error.");
				  }
			  }
			}, 3000);
});

/**
 *Request to log in a user.
 *Responds with userid, username, and icon.
 */
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
		      			var jsonReturn = {"userid" : result[0].userid, "icon" : result[0].icon,
						"username" : result[0].username};
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

/**
 * Request to edit a users profile.
 * If password and/or username are not being updated, they will come in as "no".
 * responds with password correct, incorrect, no.
 */
app.put("/api/edit/profile", (req, res) => {
	console.log("\nUpdating an account...");

	var jsonData = req.body;
	console.log(jsonData);
	var password = jsonData.password;
	var oldPass = jsonData.oldpass;
	var icon = jsonData.icon;
	var username = jsonData.username;
	var userid = jsonData.userid;

  //Starts to create a sql string that will get added to
	var sql = "UPDATE users SET icon ="
	sql += " " + icon;
	if (username !== "no"){
		sql += ", username = '" + username + "'";
	}
	var test;
	if (password !== "no"){
		var sqlGetPass = "SELECT * FROM users WHERE userid = ";
		sqlGetPass += userid;

		try {
			test = con.query(sqlGetPass, function(err, result){
				if(err) throw err;

				if(oldPass === result[0].password){
					sql += ", password = '" + password + "'";
					res.json({"password": "correct"});
				} else {
					res.json({"password": "incorrect"});
				}
			});
		}
		catch (err) {
			console.log("Error checking password");
			res.json({"password": "incorrect"});
		}
	} else {
		res.json({"password": "no"});
	}

	//Used to create a wait in order to see if the password is correct or not.
	//Will do this in the background without user waiting longer
	//Uses the sql string that was made and added to above
	var b = setTimeout( function() {
		sql += " WHERE userid = " + userid;
	  console.log(sql);

	  try {
		  con.query(sql, function(err, result){
		   if(err) throw err;
		  	console.log("Update successful!");
		  });
	  }
	  catch(err) {
		  console.log("Update error.");
	  }
	}, 3000);
});

app.put("/api/edit/highscore", (req, res) => {
	console.log("\nUpdating an account...");

	var jsonData = req.body;});
  var type = jsonData.type;
	var score = jsonData.score;

	var sql = "UPDATE high_scores SET " + type + " = " + score + " WHERE UserID = " + userid;
	try {
		con.query(sql, function(err, result){
			if(err) throw err;
			console.log("Sucess!");
			res.json({"update": "done"});
		});
	}
	catch(err) {
		console.log("error");
		res.json({"update": "error"});
	}
