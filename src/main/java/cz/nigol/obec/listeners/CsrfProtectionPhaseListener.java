package cz.nigol.obec.listeners;

import java.io.IOException;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsrfProtectionPhaseListener implements PhaseListener {

	@Override
	public void beforePhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		HttpServletRequest request = 
			(HttpServletRequest) facesContext
				.getExternalContext()
				.getRequest();

		HttpServletResponse response = 
			(HttpServletResponse) facesContext
				.getExternalContext()
				.getResponse();

		if ("POST".equalsIgnoreCase(request.getMethod())) {

			String secFetchSite = request.getHeader("sec-fetch-site");

			if ("cross-site".equalsIgnoreCase(secFetchSite)) {

				try {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					facesContext.responseComplete();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}