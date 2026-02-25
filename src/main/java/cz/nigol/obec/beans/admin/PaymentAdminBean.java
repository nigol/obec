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
	private List<Integer> years;
	private int year;

	@PostConstruct
	public void init() {
		years = paymentService.getYears();
		if (!years.isEmpty()) {
			year = years.get(0);
		}
		loadPaymentTypes();
		loadPayments();
		users = userService.getAllUsers();
	}

	private void loadPaymentTypes() {
		paymentTypes = paymentService.getAllPaymentTypes();
	}

	private void loadPayments() {
		years = paymentService.getYears();
		payments = paymentService.getPaymentsByYear(year);
	}

	public void onYearSelect() {
		loadPayments();
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

	public void sendQrMail(Payment payment) {
		paymentService.sendQrMail(payment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Email byl odeslán."));
	}

	public void undeletePayment() {
		paymentService.savePayment(deletedPayment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla obnovena."));
		deletedPayment = null;
		loadPayments();
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
		loadPayments();
	}

	public void savePayment(Payment payment) {
		paymentService.savePayment(payment);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		year = payment.getYear();
		loadPayments();
	}
	
	public void savePaymentType(PaymentType paymentType) {
		paymentService.savePaymentType(paymentType);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		loadPaymentTypes();
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Integer> getYears() {
		return years;
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