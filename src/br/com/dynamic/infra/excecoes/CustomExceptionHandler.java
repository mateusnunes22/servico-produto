package br.com.dynamic.infra.excecoes;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.services.IEmailService;
import br.com.dynamic.util.LogMessagesUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private IEmailService emailService;

	private ExceptionHandler wrapped;

	final FacesContext facesContext = FacesContext.getCurrentInstance();
	final Map requestMap = facesContext.getExternalContext().getRequestMap();
	final NavigationHandler navigationHandler = facesContext.getApplication()
			.getNavigationHandler();

	CustomExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		final Iterator iterator = getUnhandledExceptionQueuedEvents()
				.iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable exception = context.getException();

			try {
				String title;

				try {
					Logger.getLogger(getClass().getName()).log(Level.ERROR, exception.getMessage(), exception);
					title = "Erro em "
							+ FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath();
					emailService = FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(facesContext,
									"#{emailService}", IEmailService.class);
					emailService.sendMail(title, LogMessagesUtil.getMailMessage(exception));
				} catch (Exception emailException) {
					emailException.printStackTrace();
				}

				requestMap.put("exceptionMessage", exception.getMessage());

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O sistema se recuperou de um erro inesperado.", ""));

				FacesContext
						.getCurrentInstance()
						.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você pode continuar usando o sistema normalmente!", ""));

				navigationHandler.handleNavigation(facesContext, null,
						"/erro.jsf");
				facesContext.renderResponse();
			} finally {
				iterator.remove();
			}
		}

		getWrapped().handle();
	}

}
