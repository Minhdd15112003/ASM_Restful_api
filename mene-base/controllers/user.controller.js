const userModel = require("../models/user.model");

class UserController {
  //////////////////////////////// getUsers
  getUsers(req, res, next) {
    userModel
      .find({})
      .then(function (userData) {
      //  res.render("home/userView/getUsers", { userData: userData });
        res.json({ userData: userData});
      })
      .catch(function (error) {
        res.send("co loi xay ra", error.message);
      });
  }

  getUser(req, res, next) {
    const id = req.params.id;
    userModel.findById(id)
   .then(function (userData) {
    if (userData) {
      res.json(userData);
    } else {
      res.send("Không tìm thấy người dùng với ID: " + id);
    }
  
   })
  }
  //////////////////////////////// insertUsers
  getInsertUsersForm(req, res, next) {
    res.render("home/userView/getFormRegister");
  }
  insertUsers(req, res, next) {
    const { Email, Username, Password, PasswordCheck } = req.body;
    const newUsers = {
      Email: Email,
      Username: Username,
      Password: Password,
    };
    userModel
      .create(newUsers)
      .then((user) => {
        res.json({ user: user });
      })
      .catch((error) => {
        res.send("Them that bai " + error.message);
      });
  }
  //////////////////////////////////////////////////////////////// updateUser
  getUpdateUsersForm(req, res, next) {
    const id = req.params.id;
    userModel
      .findById(id)
      .then(function (user) {
        if (user) {
          res.render("home/userView/updateUser", { user: user, id: id });
        } else {
          res.send("Không tìm thấy người dùng với ID: " + id);
        }
      })
      .catch(function (error) {
        res.send("Có lỗi xảy ra", error.message);
      });
  }

  updateUser(req, res, next) {
   // const id = req.body.Id; // ID của người dùng cần cập nhật
    const id = req.params.id
    const updatedUser = {
      Email: req.body.Email,
      Username: req.body.Username,
      // Password: req.body.Password,
    };
 
   
    userModel
      .findByIdAndUpdate(id, updatedUser, { new: true }) // Option { new: true } để trả về người dùng đã được cập nhật
      .then((user) => {
        
        if (!user) {
          res.status(500).send("Không tìm thấy người dùng với ID: " + id);
          // console.log('====================================');
          // console.log(req.body);
          // console.log('====================================');
        } else {
          //res.redirect("/getUsers");
          res.json(user);
        }
      })
      .catch((error) => {
        res.status(501).send("Cập nhật thất bại " + error.message);
      });
  }

  //////////////////////////////////////////////////////////////// deleteUser
  deleteUser(req, res) {
    const id = req.params.id;
    userModel
      .findByIdAndDelete(id)
      .then((user) => {
        if (!user) {
          res.send("Không tìm thấy người dùng với ID: " + id);
        } else {
          res.redirect("/getUsers");
        }
      })
      .catch((error) => {
        res.send("Xóa thất bại " + error.message);
      });
  }
  //////////////////////////////////////////////////////////////// getLoginUsersForm
  getLoginUsersForm(req, res) {
    res.render("home/userView/getFormLogin");
  }
  loginUser(req, res) {
    const { Email, Password } = req.body;
    // console.log('====================================');
    // console.log(req.body);
    // console.log('====================================');
    userModel
      .findOne({ Email: Email })
      .then((user) => {
        if (!user) {
          res.status(501).send("không tìm thấy người dụng vơi Email " + user.Email);
        } else {
          if (Password == user.Password) {
           // res.send("Đăng nhập thành công!!!");
           res.json(user);
      
          } else {
            res.status(500).send("Mật khẩu không chính xác");
          }
        }
      })
      .catch((error) => {
        res.send("Có lỗi xảy ra khi tìm người dùng: " + error.message);
      });
  }
}

module.exports = new UserController();
