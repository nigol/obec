package cz.nigol.obec.converters;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Named
@ApplicationScoped
public class PaymentTypeConverter implements Converter {
	@Inject
	private PaymentService paymentService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		PaymentType result = null;
		if (arg2 != null && !"".equals(arg2)) {
			result = paymentService.getPaymentTypeById(Long.parseLong(arg2));
		}	
		return result;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String result = "";
		if (arg2 != null) {
			PaymentType paymentType = (PaymentType) arg2;
			result = result + paymentType.getId();
		}
		return result;
	}
}