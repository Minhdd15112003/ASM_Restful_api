var mongoose = require("mongoose");

var cateSchema = new mongoose.Schema(
  {
    Cate: {
      type: String,
      required: true,
    },
    Product:{
      type: mongoose.Schema.Types.ObjectId, 
      required: true,
      ref: "ProductModel"
    }
    
    
  },
  { collection: "Categories" }
);
const CateModel = mongoose.model('CateModel', cateSchema);
module.exports = CateModel
