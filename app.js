const express = require("express");
const app = express();
const Razorpay = require("razorpay");
const keyId = "rzp_test_GWUDtXTecUqLs5";
const keySecret = "eE5R7Vxdx566PFkswLWkL5i1";

app.use(express.json());
app.listen(3000, () => {
  console.log("Listening on port 3000...");
});

const rzpInstance = new Razorpay({
  key_id: keyId,
  key_secret: keySecret,
});

//android client request
app.post("/getOrderId", (req, res) => {
  const options = {
    amount: req.body.amount + "00",
    currency: "USD",
    payment_capture: "1",
  };

  rzpInstance.orders.create(options, (err, order) => {
    const resObj = {
      keyId: keyId,
      orderId: order.id,
    };
    res.send(JSON.stringify(resObj));
  });
});
//in real app you need to store the elements
app.post("/updateTransactionStatus", (req, res) => {
  console.log(req.body);
  res.send("success");
});
