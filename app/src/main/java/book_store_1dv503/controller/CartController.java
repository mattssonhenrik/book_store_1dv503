package book_store_1dv503.controller;

import book_store_1dv503.view.InvoiceView;

public class CartController {
  InvoiceView invoiceView;
  int userId;

  public CartController(int userId) {
    invoiceView = new InvoiceView(userId);
    this.userId = userId;
  }

  // FAt method for handling showing cart, printing invoice, emptying cart.
  public void handleCart() {
    boolean invoiceCartOrNot = invoiceView.showCartAndAskIfCheckOut();
    if (invoiceCartOrNot) {
      OrderController orderController = new OrderController(userId);
    }
  }

}
