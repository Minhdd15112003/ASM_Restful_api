const mongoose = require("mongoose");

const BillSchema = new mongoose.Schema({
    Date: {
      type: Date,
      required: true,
    },
    AccountID: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'userModel',
      required: true,
    },
    BillDetails: [{
      ProductID: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'ProductModel',
        required: true,
      },
      Quantity: {
        type: Number,
        required: true,
      },
    }],
  });
const BillModel = mongoose.model("Bills", BillSchema);
module.exports = BillModel;
