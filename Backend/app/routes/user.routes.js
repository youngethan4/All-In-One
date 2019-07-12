module.exports = function(app, con) {
  var users = require('../controllers/user.controller.js');

  //Creates a new user
  app.post('/api/users/create', users.create);
}

