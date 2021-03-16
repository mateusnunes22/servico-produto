package br.com.dynamic.infra.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.dynamic.util.LogMessagesUtil;

public class SistemaPhaseListener implements PhaseListener {

	private final Logger log = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		log.log(Level.INFO, LogMessagesUtil.getPageView());
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}