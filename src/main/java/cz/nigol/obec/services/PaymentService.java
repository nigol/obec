package cz.nigol.obec.services;

import java.util.List;

import cz.nigol.obec.entities.*;

public interface PaymentService {
	List<PaymentType> getAllPaymentTypes();
	PaymentType savePaymentType(PaymentType paymentType);
	PaymentType getPaymentTypeById(long id);
	List<Payment> getAllPayments();
	Payment savePayment(Payment payment);
	void deletePayment(Payment payment);
	List<Payment> getPaymentsByYear(int year);
	List<Integer> getYears();
	Payment getPaymentById(long id);
	String getSpecSymbol(Payment payment);
	String getQrPayment(Payment payment, Settings settings);
	void sendQrMail(Payment payment);
}