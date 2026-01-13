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
}