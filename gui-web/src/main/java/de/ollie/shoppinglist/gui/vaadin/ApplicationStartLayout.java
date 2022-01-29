package de.ollie.shoppinglist.gui.vaadin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.shoppinglist.core.model.localization.LocalizationSO;
import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.JWTService;
import de.ollie.shoppinglist.core.service.JWTService.AuthorizationData;
import de.ollie.shoppinglist.core.service.ListPositionService;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.UserService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.AccessChecker;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.WebAppConfiguration;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;
import de.ollie.shoppinglist.gui.vaadin.component.HeaderLayout;
import de.ollie.shoppinglist.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import lombok.RequiredArgsConstructor;

/**
 * The GUI entry point for the Cube application.
 *
 * @author ollie (30.12.2021)
 */
@Route(ApplicationStartLayout.URL)
@PreserveOnRefresh
@VaadinSessionScope
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@RequiredArgsConstructor
public class ApplicationStartLayout extends VerticalLayout implements AccessChecker, HasUrlParameter<String> {

	public static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	public static final String URL = "shopping-list";

	private static final Logger logger = LogManager.getLogger(ApplicationStartLayout.class);

	private final ItemService itemService;
	private final JWTService jwtService;
	private final ListPositionService listPositionService;
	private final ResourceManager resourceManager;
	private final SessionData sessionData;
	private final ShopService shopService;
	private final UserService userService;
	private final WebAppConfiguration webAppConfiguration;

	private AuthorizationData authorizationData;
	private Button buttonBackToCube;
	private HeaderLayout headerLayout;
	private String token;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		Map<String, List<String>> parametersMap = queryParameters.getParameters();
		if ((parametersMap.get("jwt") != null) && !parametersMap.get("jwt").isEmpty()) {
			token = parametersMap.get("jwt").get(0);
		}
		try {
			authorizationData = jwtService.getAuthorizationData(token);
			sessionData.setAccessChecker(this);
			sessionData.setAuthorizationData(authorizationData);
			sessionData.setLocalization(LocalizationSO.DE);
			logger.info("session started by user: " + authorizationData.getUser().getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("tried to login with invalid token! (" + e + ")");
		}
	}

	@PostConstruct
	void postConstruct() {
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		logger.info("starting shopping-list application ...");
		removeAll();
		getStyle().set("background-image", "url(ShoppingList-Background.png)");
		setMargin(false);
		setWidthFull();
		buttonBackToCube =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ApplicationStartLayout.buttonBackToCube.text",
												LOCALIZATION));
		buttonBackToCube.addClickListener(event -> switchToCube());
		headerLayout =
				new HeaderLayout(
						buttonBackToCube,
						resourceManager
								.getLocalizedString("ApplicationStartLayout.headerLayout.label.text", LOCALIZATION)
								+ " (" + webAppConfiguration.getAppVersion() + ")",
						HeaderLayoutMode.PLAIN);
		if (authorizationData == null) {
			denyAccess();
		} else {
			add(
					headerLayout,
					new ShoppingListMainLayout(
							shopService,
							itemService,
							listPositionService,
							resourceManager,
							sessionData));
		}
	}

	@Override
	public boolean checkToken() {
		if (jwtService
				.getLoginDate(token)
				.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes())
				.isBefore(LocalDateTime.now())) {
			logger
					.info(
							"session invalid: "
									+ jwtService
											.getLoginDate(token)
											.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes())
									+ " < " + LocalDateTime.now());
			denyAccess();
			return false;
		}
		logger
				.info(
						"valid until: " + jwtService
								.getLoginDate(token)
								.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes()));
		return true;
	}

	public void denyAccess() {
		removeAll();
		Label label = new Label(resourceManager.getLocalizedString("Error.notAuthorized.label.text", LOCALIZATION));
		label.getStyle().set("color", "red");
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.add(label);
		add(layout);
		logger.info("access denied!");
	}

	private void switchToCube() {
		String url = webAppConfiguration.getCubeURL();
		logger.info("returning to: " + url);
		getUI().ifPresent(ui -> ui.getPage().setLocation(url));
	}

}