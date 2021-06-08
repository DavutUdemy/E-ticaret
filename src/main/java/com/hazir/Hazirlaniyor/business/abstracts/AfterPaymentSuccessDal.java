package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.business.concretes.PaymentEmailManager;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.CartDao;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ProductDao;
import com.hazir.Hazirlaniyor.entity.concretes.Cart;
import com.hazir.Hazirlaniyor.entity.concretes.Shipment;
import com.hazir.Hazirlaniyor.entity.concretes.SuccessEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AfterPaymentSuccessDal implements AfterPaymentSuccessService {
	private final ShipmentService shipmentService;
	private final PaymentEmailManager paymentEmailManager;
	private final CartDao cartDao;
	private final ProductDao productDao;

	@Autowired
	public AfterPaymentSuccessDal(ShipmentService shipmentService, PaymentEmailManager paymentEmailManager, CartDao cartDao, ProductDao productDao) {
		this.shipmentService = shipmentService;
		this.paymentEmailManager = paymentEmailManager;
		this.cartDao = cartDao;
		this.productDao = productDao;
	}


	@Override
	public void updateStock(String email) {
		List<Cart> findCartByUserEmail = this.cartDao.findCartByEmail (email);
		for(Cart cart: findCartByUserEmail){
			cart.getQuantity();
			this.productDao.unitsInStock(cart.getQuantity(),cart.getProductName());

		}

	}

	@Override
	public void sendSuccessEmail(SuccessEmail successEmail) {
		paymentEmailManager.send(
				successEmail.getUserEmail (),
				buildEmail(successEmail.getFirstName (),successEmail.getChargeId ()));

	}

	@Override
	public String buildEmail(String firstName, String chargeId) {
		return "<!DOCTYPE html>\n" +
				"<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
				"<head>\n" +
				"\t<meta charset=\"UTF-8\">\n" +
				"\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
				"\t<meta name=\"x-apple-disable-message-reformatting\">\n" +
				"\t<title></title>\n" +
				"\t<!--[if mso]>\n" +
				"\t<noscript>\n" +
				"\t\t<xml>\n" +
				"\t\t\t<o:OfficeDocumentSettings>\n" +
				"\t\t\t\t<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
				"\t\t\t</o:OfficeDocumentSettings>\n" +
				"\t\t</xml>\n" +
				"\t</noscript>\n" +
				"\t<![endif]-->\n" +
				"\t<style>\n" +
				"\t\ttable, td, div, h1, p {font-family: Arial, sans-serif;}\n" +
				"\t</style>\n" +
				"</head>\n" +
				"<body style=\"margin:0;padding:0;\">\n" +
				"\t<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
				"\t\t<tr>\n" +
				"\t\t\t<td align=\"center\" style=\"padding:0;\">\n" +
				"\t\t\t\t<table role=\"presentation\" style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\">\n" +
				"\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t<td align=\"center\" style=\"padding:40px 0 30px 0;background:#70bbd9;\">\n" +
				"\t\t\t\t\t\t\t<img src=\"file:///C:/Users/USER/Desktop/b2ap3_large_thank-you-shop-online.jpg\" alt=\"\" width=\"300\" style=\"height:auto;display:block;\" />\n" +
				"\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t<td style=\"padding:36px 30px 42px 30px;\">\n" +
				"\t\t\t\t\t\t\t<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
				"\t\t\t\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"padding:0 0 36px 0;color:#153643;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Thanks For Shopping YDA</h1>\n" +
				"\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Hi "+firstName + ",Your Payment was succesfully.Your transactionId is "+ chargeId +" please don't lose this Id</p>\n" +
				"\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><a href=\"http://www.example.com\" style=\"color:#ee4c50;text-decoration:underline;\">In tempus felis blandit</a></p>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"padding:0;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0 0 25px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><img src=\"file:///C:/Users/USER/Desktop/75fa9b17f632646e5ae7fae3cf837761.jpg\" alt=\"\" width=\"260\" style=\"height:auto;display:block;\" /></p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed. Morbi porttitor, eget accumsan dictum, est nisi libero ultricies ipsum, in posuere mauris neque at erat.</p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><a href=\"http://www.example.com\" style=\"color:#ee4c50;text-decoration:underline;\">Blandit ipsum volutpat sed</a></p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"width:20px;padding:0;font-size:0;line-height:0;\">&nbsp;</td>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0 0 25px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><img src=\"file:///C:/Users/USER/Desktop/indir.jpg\" alt=\"\" width=\"260\" style=\"height:auto;display:block;\" /></p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Morbi porttitor, eget est accumsan dictum, nisi libero ultricies ipsum, in posuere mauris neque at erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed.</p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><a href=\"http://www.example.com\" style=\"color:#ee4c50;text-decoration:underline;\">In tempus felis blandit</a></p>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t\t\t\t\t\t</table>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t\t\t</table>\n" +
				"\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t<td style=\"padding:30px;background:#ee4c50;\">\n" +
				"\t\t\t\t\t\t\t<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;\">\n" +
				"\t\t\t\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"padding:0;width:50%;\" align=\"left\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t&reg; Someone, Somewhere 2021<br/><a href=\"http://www.example.com\" style=\"color:#ffffff;text-decoration:underline;\">Unsubscribe</a>\n" +
				"\t\t\t\t\t\t\t\t\t\t</p>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"padding:0;width:50%;\" align=\"right\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<table role=\"presentation\" style=\"border-collapse:collapse;border:0;border-spacing:0;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding:0 0 0 10px;width:38px;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://www.twitter.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/tw_1.png\" alt=\"Twitter\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding:0 0 0 10px;width:38px;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://www.facebook.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t\t\t\t\t\t</table>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t</tr>\n" +
				"\t\t\t\t\t\t\t</table>\n" +
				"\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t</tr>\n" +
				"\t\t\t\t</table>\n" +
				"\t\t\t</td>\n" +
				"\t\t</tr>\n" +
				"\t</table>\n" +
				"</body>\n" +
				"</html>";
	}


	@Override
	public void postNewShipment(Shipment shipment) {
		this.shipmentService.addNewShipment (shipment);

	}

	@Override
	public void deleteCartByEmail(String email) {
		this.cartDao.deleteByemail(email);
	}
}
