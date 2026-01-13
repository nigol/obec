package cz.nigol.obec.services;

import java.util.List;

import cz.nigol.obec.entities.*;

public interface PaymentService {
	List<PaymentType> getAllPaymentTypes();
	PaymentType savePaymentType(PaymentType paymentType);
}