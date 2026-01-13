package cz.nigol.obec.beans.admin;

import java.io.Serializable;
import java.util.*;

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
	private FacesContext facesContext;
	private List<PaymentType> paymentTypes;
	private PaymentType paymentType;

	@PostConstruct
	public void init() {
		paymentTypes = paymentService.getAllPaymentTypes();
	}

	public void newPaymentType() {
		paymentType = new PaymentType();
		paymentTypes.add(paymentType);
	}
	
	public void savePaymentType(PaymentType paymentType) {
		paymentService.savePaymentType(paymentType);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		init();
	}

	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
}