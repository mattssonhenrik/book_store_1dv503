package book_store_1dv503.controller;

import book_store_1dv503.view.InvoiceView;

public class CartController {
  InvoiceView invoiceView;

  public CartController(int userId) {
    invoiceView = new InvoiceView(userId);
  }

  // FAt method for handling showing cart, printing invoice, emptying cart.
  public void handleCart() {
    invoiceView.showCartAndAskIfCheckOut();
  }

}
