var mongoose = require("mongoose");

var userModelShop = new mongoose.Schema({
  Email: String,
  Username: String,
  Password: String,
  Role : String,
}, { collection: 'Accounts' });

//var userModel = mongoose.model("Accounts", userModelShop);

module.exports = mongoose.model("Accounts", userModelShop);
