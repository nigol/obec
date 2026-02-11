package cz.nigol.obec.services.impl;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.PaymentService;

@Stateless
public class PaymentServiceImpl implements PaymentService {
	@PersistenceContext(unitName="obecPU")
	private EntityManager em;

	@Override
	public List<PaymentType> getAllPaymentTypes() {
		TypedQuery<PaymentType> typedQuery = em.createNamedQuery(PaymentType.GET_ALL, PaymentType.class);
		return new ArrayList<>(typedQuery.getResultList());
	}

	@Override
	public PaymentType savePaymentType(PaymentType paymentType) {
		return em.merge(paymentType);
	}

	@Override
	public PaymentType getPaymentTypeById(long id) {
		return em.find(PaymentType.class, id);
	}

	@Override
	public List<Payment> getAllPayments() {
		TypedQuery<Payment> typedQuery = em.createNamedQuery(Payment.GET_ALL, Payment.class);
		return new ArrayList<>(typedQuery.getResultList());
	}

	@Override
	public Payment savePayment(Payment payment) {
		return em.merge(payment);
	}

	@Override
	public void deletePayment(Payment payment) {
		Payment e = em.merge(payment);
		em.remove(e);
	}

	@Override
	public List<Payment> getPaymentsByYear(int year) {
		TypedQuery<Payment> typedQuery = em.createNamedQuery(Payment.GET_BY_YEAR, Payment.class);
		typedQuery.setParameter(Payment.YEAR_PARAM, year);
		return new ArrayList<>(typedQuery.getResultList());
	}

	@Override
	public List<Integer> getYears() {
		TypedQuery<Integer> typedQuery = em.createNamedQuery(Payment.GET_YEARS, Integer.class);
		return new ArrayList<>(typedQuery.getResultList());
	}

	@Override
	public Payment getPaymentById(long id) {
		return em.find(Payment.class, id);
	}

	@Override
	public String getSpecSymbol(Payment payment) {
		String year = "" + payment.getYear();
		year = year.substring(2);
		return year + payment.getForUser().getPaymentSymbol();
	}

	/*
	* SPD*1.0*ACC:CZ5608000000000002171532*AM:999*CC:CZK*DT:20150518*MSG:Zpráva*X-KS:1414*X-SS:1313*X-VS:1212
	* ACC – číslo účtu v IBAN formátu 
	* AM – částka k platbě
	* CC – měna
	* DT – datum splatnosti
	* MSG – zpráva pro příjemce
	* X-KS – konstantní, X-SS – specifický a X-VS – variabilní symbol
	*/
	@Override
	public String getQrPayment(Payment payment, Settings settings) {
		String result = "SPD*1.0*ACC:";
		result = result + settings.getIban();
		result = result + "*AM:" + payment.getAmount();
		result = result + "*CC:CZK";
		result = result + "*MSG:" + payment.getPaymentType().getLabel();
		result = result + "*X-SS:" + getSpecSymbol(payment);
		result = result + "*X-VS:" + payment.getPaymentType().getSymbol();
		return result;
	}
}