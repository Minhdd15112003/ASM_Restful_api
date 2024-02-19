const userModel = require("../models/user.model");
const bcrypt = require("bcrypt");
const saltRounds = 5; // số vòng lặp mã hóa Độ phức tạp của thuật toán mã hóa
const crypto = require("crypto");
class UserController {
  //////////////////////////////// getUsers
  getUsers(req, res, next) {
    userModel
      .find({})
      .then(function (userData) {
        res.render("home/userView/getUsers", { userData: userData });
      })
      .catch(function (error) {
        res.send("co loi xay ra", error.message);
      });
  }
  //////////////////////////////// insertUsers
  getInsertUsersForm(req, res, next) {
    res.render("home/userView/getFormRegister");
  }
  insertUsers(req, res, next) {
    const { Email, Username, Password, PasswordCheck } = req.body;

    bcrypt.hash(Password, saltRounds, function (err, hash) {
      if (err) {
        res.send("Có lỗi xảy ra khi mã hóa mật khẩu: " + err.message);
        return;
      }

      const newUsers = {
        Email: Email,
        Username: Username,
        Password: hash,
      };

      userModel
        .create(newUsers)
        .then(() => {
          res.redirect("/getUsers");
        })
        .catch((error) => {
          res.send("Them that bai " + error.message);
        });
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
    const id = req.body.Id; // ID của người dùng cần cập nhật

    const updatedUser = {
      Email: req.body.Email,
      Username: req.body.Username,
     // Password: req.body.Password,
    };
    userModel
      .findByIdAndUpdate(id, updatedUser, { new: true }) // Option { new: true } để trả về người dùng đã được cập nhật
      .then((user) => {
        if (!user) {
          res.send("Không tìm thấy người dùng với ID: " + id);
        } else {
          res.redirect("/getUsers");
        }
      })
      .catch((error) => {
        res.send("Cập nhật thất bại " + error.message);
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

    userModel
      .findOne({ Email: Email })
      .then((user) => {
        if (!user) {
          res.send("không tìm thấy người dụng vơi Email " + user.Email);
        } else {
          bcrypt.compare(Password, user.Password, function (err, result) {
            if (err) {
              res.send("Có lỗi xảy ra khi so sánh mật khẩu: " + err.message);
            } else if (result) {
              res.send("Đăng nhập thành công!!! " + result);
            } else {
              res.send("Mật khẩu không chính xác");
            }
          });
        }
      })
      .catch((error) => {
        res.send("Có lỗi xảy ra khi tìm người dùng: " + error.message);
      });
  }
}



module.exports = new UserController();
