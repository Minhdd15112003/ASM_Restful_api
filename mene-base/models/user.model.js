var mongoose = require("mongoose");

var userSchema = new mongoose.Schema(
  {
    Email: String,
    Username: String,
    Password: String
  },
  { collection: "Accounts" }
);

var userModel = mongoose.model("Accounts", userSchema);
module.exports = userModel;
