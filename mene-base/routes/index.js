const express = require("express");
const router = express.Router();
const userCtrl = require("../controllers/user.controller");
const productCtrl = require("../controllers/product.controller");


/* GET, POST User. */
router.get("/getUsers", userCtrl.getUsers);
//Login user 
// router.get("/getLoginUsersForm",userCtrl.getLoginUsersForm);
router.get("/",userCtrl.getLoginUsersForm);
router.post("/loginUser", userCtrl.loginUser);
//insert user 
router.get("/getInsertUsersForm",userCtrl.getInsertUsersForm);
router.post("/insertUser", userCtrl.insertUsers);
//update user
router.get("/getUpdateUsersForm/:id",userCtrl.getUpdateUsersForm);
router.post("/updateUser", userCtrl.updateUser);
//delete user
router.get('/deleteUser/:id',userCtrl.deleteUser);

/* GET, POST Category. */
router.get("/getCategoryById/:id", productCtrl.getCategoryById);
router.get("/getFormCate", productCtrl.getFormCate);
router.post("/insertCate", productCtrl.insertCate);
router.get('/deleteCate/:id', productCtrl.deleteCate)
/* GET, POST Products. */
router.get("/productHome", productCtrl.getProduct);
router.get("/getFormProduct", productCtrl.getFormProduct);
router.post("/insertProduct",productCtrl.insertProduct);
router.get("/getDetailsProduct/:id", productCtrl.getDetailsProduct);
router.get("/deleteProduct/:id",productCtrl.deleteProduct);
router.get("/getFormUpdateProduct/:id", productCtrl.getFormUpdateProduct);
router.post("/updateProduct",productCtrl.updateProduct);
module.exports = router;
