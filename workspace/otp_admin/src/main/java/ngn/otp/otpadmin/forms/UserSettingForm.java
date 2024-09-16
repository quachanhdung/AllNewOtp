package ngn.otp.otpadmin.forms;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import ngn.otp.otpadmin.data.SamplePerson;
import ngn.otp.otpadmin.services.SamplePersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;



public class UserSettingForm extends Composite<VerticalLayout>{


	private SamplePersonService samplePersonService;
	

	public UserSettingForm(SamplePersonService samplePersonService) {
		this.samplePersonService = samplePersonService;

		HorizontalLayout layoutRow = new HorizontalLayout();
		MessageInput messageInput = new MessageInput();
		HorizontalLayout layoutRow2 = new HorizontalLayout();
		Button buttonSecondary = new Button();
		Button buttonSecondary2 = new Button();
		Button buttonSecondary3 = new Button();
		Grid basicGrid = new Grid(SamplePerson.class);
		getContent().setWidth("100%");
		getContent().getStyle().set("flex-grow", "1");
		layoutRow.setWidthFull();
		getContent().setFlexGrow(1.0, layoutRow);
		layoutRow.addClassName(Gap.MEDIUM);
		layoutRow.setWidth("100%");
		layoutRow.setHeight("min-content");
		layoutRow.setAlignSelf(FlexComponent.Alignment.START, messageInput);
		messageInput.getStyle().set("flex-grow", "1");
		layoutRow2.setWidthFull();
		getContent().setFlexGrow(1.0, layoutRow2);
		layoutRow2.addClassName(Gap.MEDIUM);
		layoutRow2.setWidth("100%");
		layoutRow2.setHeight("min-content");
		buttonSecondary.setText("Button");
		buttonSecondary.setWidth("min-content");
		buttonSecondary2.setText("Button");
		buttonSecondary2.setWidth("min-content");
		buttonSecondary3.setText("Button");
		buttonSecondary3.setWidth("min-content");
		basicGrid.setWidth("100%");
		basicGrid.setHeight("100%");
		setGridSampleData(basicGrid);
		getContent().add(layoutRow);
		layoutRow.add(messageInput);
		getContent().add(layoutRow2);
		layoutRow2.add(buttonSecondary);
		layoutRow2.add(buttonSecondary2);
		layoutRow2.add(buttonSecondary3);
		getContent().add(basicGrid);
	}

	private void setGridSampleData(Grid grid) {
		grid.setItems(query -> samplePersonService.list(
				PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
				.stream());
	}





}
