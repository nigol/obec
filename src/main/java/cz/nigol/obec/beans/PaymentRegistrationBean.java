package cz.nigol.obec.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.services.*;

@Named
@RequestScoped
public class PaymentRegistrationBean {
	@Inject
	@CurrentSettings
	private Settings settings;
	@Inject
	private UserService userService;
	private String email;
	private boolean sent;

	public void register() {
		userService.subscribeAnnouncements(email);
		User user = userService.getUserByEmail(email);
		userService.sendNewUserInfoByEmail(settings.getNotificationEmail(), user);
		sent = true;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}