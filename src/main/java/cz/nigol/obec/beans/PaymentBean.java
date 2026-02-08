package cz.nigol.obec.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;
import cz.nigol.obec.qualifiers.*;

@Named
@RequestScoped
public class PaymentBean {
	@Inject
	private PaymentService paymentService;
	@Inject
	@CurrentSettings
	private Settings settings;
	private Payment payment;
	private long paymentId;
	private int hash;
	private String qr;

	public void onLoad() {
		payment = paymentService.getPaymentById(paymentId);
		if (payment != null) {
			qr = paymentService.getQrPayment(payment, settings);
		}
	}

	public String getQr() {
		return qr;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public Payment getPayment() {
		return payment;
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
}