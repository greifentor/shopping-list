package de.ollie.shoppinglist.gui.vaadin;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

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
public class ApplicationStartLayout extends VerticalLayout implements HasUrlParameter<String> {

	public static final String URL = "shopping-list";

	private static final Logger logger = LogManager.getLogger(ApplicationStartLayout.class);

	private String token;
	private Label label;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		Map<String, List<String>> parametersMap = queryParameters.getParameters();
		if ((parametersMap.get("jwt") != null) && !parametersMap.get("jwt").isEmpty()) {
			token = parametersMap.get("jwt").get(0);
		}
		label.setText("token: " + token);
	}

	@PostConstruct
	void postConstruct() {
		getStyle().set("background-image", "url(ShoppingList-Background.png)");
		setHeight("632px");
		setMargin(false);
		setWidthFull();
		label = new Label("empty");
		add(label);
	}

}