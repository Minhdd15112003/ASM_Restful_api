const mongoose = require("mongoose");
const cateModel = require("./cate.model");

const ProductSchema = new mongoose.Schema(
  {
    name:  { type: String, required: true },
    description:  { type: String, required: true },
    price:  { type: Number, required: true },
    CateID: { 
        type: mongoose.Schema.Types.ObjectId, 
        required: true,
        ref: "CateModel"
      },
  },
  { collection: "Products" }
);
const ProductModel = mongoose.model('Products', ProductSchema);
module.exports = ProductModel;
