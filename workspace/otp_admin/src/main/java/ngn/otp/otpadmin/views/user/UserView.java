package ngn.otp.otpadmin.views.user;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import ngn.otp.otpadmin.forms.UserSettingForm;
import ngn.otp.otpadmin.services.SamplePersonService;
import ngn.otp.otpadmin.views.MainLayout;

@PageTitle("User")
@Route(value = "user", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class UserView extends Composite<VerticalLayout> {
	private SamplePersonService samplePersonService;
    public UserView(SamplePersonService samplePersonService) {
    	this.samplePersonService = samplePersonService;
        HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h1 = new H1();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        TabSheet tabSheet = new TabSheet();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        H3 h3 = new H3();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h1.setText("Heading");
        h1.setWidth("max-content");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        tabSheet.setWidth("100%");
        setTabSheetSampleData(tabSheet);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        h3.setText("Heading");
        h3.setWidth("max-content");
        getContent().add(layoutRow);
        layoutRow.add(h1);
        getContent().add(layoutColumn2);
        layoutColumn2.add(tabSheet);
        getContent().add(layoutRow2);
        layoutRow2.add(h3);
    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        tabSheet.add("Dashboard", new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Payment", new Div(new Text("This is the Payment tab content")));
        tabSheet.add("Shipping", new Div(new Text("This is the Shipping tab content")));
        tabSheet.add("user setting", new UserSettingForm(samplePersonService));
    }
}
