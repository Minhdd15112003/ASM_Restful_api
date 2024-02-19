var express = require("express");
var app = express();
const cateModel = require("../models/cate.model");
const productModel = require("../models/product.model");

class ProductController {
  //////////////////////////////// insertCate
  getFormCate(req, res, next) {
    cateModel
      .find({})
      .then(function (cateData) {
        res.render("home/productView/formInsertCate", { cateData: cateData });
      })
      .catch(function (error) {
        res.send("co loi xay ra", error.message);
      });
  }
  insertCate(req, res, next) {
    const newCate = req.body;
    cateModel
      .create(newCate)
      .then(() => {
        res.redirect("/getFormCate");
      })
      .catch((error) => {
        res.send("Them that bai " + error.message);
      });
  }

  //////////////////////////////// deleteCate

  //////////////////////////////////////////////////////////////// deleteUser
  deleteCate(req, res) {
    const id = req.params.id;
    cateModel
      .findByIdAndDelete(id)
      .then((user) => {
        if (!user) {
          res.send("Không tìm thấy người dùng với ID: " + id);
        } else {
          res.redirect("/getFormCate");
        }
      })
      .catch((error) => {
        res.send("Xóa thất bại " + error.message);
      });
  }

  //////////////////////////////////////////////////////////////// getCategoryById

  getCategoryById(req, res, next) {
    const CateID = req.params.id;
    productModel
      .find({ CateID: CateID })
      .then((productData) => {
        if (productData) {
          res.render("home/productView/getProduct", {
            productData: productData,
          });
        } else {
          res.send("Không tìm thấy sản phẩm trong danh mục: " + CateID);
        }
      })
      .catch((error) => {
        res.send("lỗi chức năng : " + error.message);
      });
  }

  //////////////////////////////get Product
  getProduct(req, res, next) {
    productModel
      .find({})
      .then(function (productData) {
        res.render("home/productView/getProduct", { productData: productData });
      })
      .catch(function (error) {
        res.send("co loi xay ra", error.message);
      });
  }
  //////////////////////////////insert Product
  getFormProduct(req, res, next) {
    cateModel
      .find({})
      .then(function (cateData) {
        res.render("home/productView/formInsertProduct", {
          cateData: cateData,
        });
      })
      .catch(function (error) {
        res.send("co loi xay ra", error.message);
      });
  }

  // Thêm sản phẩm mới
  insertProduct = async (req, res, next) => {
    const { name, description, price, CateID } = req.body;
    //  const Cate = await cateModel.findById(CateID);
    if (!CateID) {
      return res.status(400).send("Category not found");
    }
    const newProduct = {
      name,
      description,
      price,
      CateID,
    };
    productModel
      .create(newProduct)
      .then(() => {
        res.redirect("/");
      })
      .catch((error) => {
        res.send("Them that bai " + error.message);
      });
  };

  getDetailsProduct(req, res) {
    const id = req.params.id;
    productModel
      .findById(id)
      .populate("CateID") // Điền "CateID" để tham chiếu cate
      .then(function (productData) {
        if (productData) {
          res.render("home/productView/getDetailsProduct", {
            productData: productData,
            id: id,
          });
        } else {
          res.send("Không tìm thấy người dùng với ID: " + id);
        }
      })
      .catch((error) => {
        res.status(500).send("Có lỗi xảy ra: " + error.message);
      });
  }

  async getFormUpdateProduct(req, res) {
    try {
      const id = req.params.id;
      const productData = await productModel.findById(id);
      if (!productData) {
        return res.send("Không tìm thấy Product với ID: " + id);
      }
      const cateData = await cateModel.find({});
      res.render("home/productView/formUpdateProduct", {
        cateData,
        productData,
        id,
      });
    } catch (error) {
      res.send("");
    }
  }

  updateProduct(req, res) {
    const id = req.body.Id;
    const { name, description, price, CateID } = req.body;

    const updateProduct = {
      name: name,
      description: description,
      price: price,
      CateID: CateID,
    };
    productModel
      .findByIdAndUpdate(id, updateProduct, { new: true })
      .then((productData) => {
        if (!productData) {
          res.send("Không tìm thấy product với ID: " + id);
        } else {
          res.redirect("/");
        }
      })
      .catch((error) => {
        res.send("Cập nhật thất bại " + error.message);
      });
  }

  deleteProduct(req, res) {
    const id = req.params.id;
    productModel
      .findByIdAndDelete(id)
      .then((product) => {
        if (!product) {
          res.send("Không tìm sản phẩm với ID: " + id);
        } else {
          res.redirect("/");
        }
      })
      .catch((error) => {
        res.send("Xóa thất bại " + error.message);
      });
  }
}

module.exports = new ProductController();
