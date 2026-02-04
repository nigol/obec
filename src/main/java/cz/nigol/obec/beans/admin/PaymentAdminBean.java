package cz.nigol.obec.beans.admin;

import java.io.Serializable;
import java.util.*;
import java.time.Year;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.LoggedUser;
import cz.nigol.obec.services.*;

@Named
@ViewScoped
public class PaymentAdminBean implements Serializable {
	private static final long serialVersionUID = -7906523978360505956L;
	@Inject
	private PaymentService paymentService;
	@Inject
	private UserService userService;
	@Inject
	private FacesContext facesContext;
	@Inject
	@LoggedUser
	private User user;
	private List<PaymentType> paymentTypes;
	private PaymentType paymentType;
	private List<Payment> payments;
	private List<User> users;
	private Payment deletedPayment;

	@PostConstruct
	public void init() {
		paymentTypes = paymentService.getAllPaymentTypes();
		payments = paymentService.getAllPayments();
		users = userService.getAllUsers();
	}

	public void newPayment() {
		Payment p = new Payment();
		p.setChangedAt(new Date());
		p.setChangedBy(userService.getUserById(user.getId()));
		p.setYear(Year.now().getValue());
		payments.add(p);
	}

	public void newPaymentType() {
		paymentType = new PaymentType();
		paymentTypes.add(paymentType);
	}

	public void undeletePayment() {
		paymentService.savePayment(deletedPayment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla obnovena."));
		deletedPayment = null;
		init();
	}

	public void deletePayment(Payment payment) {
		paymentService.deletePayment(payment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla smazána."));
		deletedPayment = new Payment();
		deletedPayment.setLabel(payment.getLabel());
		deletedPayment.setChangedAt(new Date());
		deletedPayment.setChangedBy(userService.getUserById(user.getId()));
		deletedPayment.setForUser(payment.getForUser());
		deletedPayment.setAmount(payment.getAmount());
		deletedPayment.setYear(payment.getYear());
		deletedPayment.setPaymentType(payment.getPaymentType());
		init();
	}

	public void savePayment(Payment payment) {
		paymentService.savePayment(payment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		init();
	}
	
	public void savePaymentType(PaymentType paymentType) {
		paymentService.savePaymentType(paymentType);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		init();
	}

	public Payment getDeletedPayment() {
		return deletedPayment;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
}