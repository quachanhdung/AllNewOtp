package ngn.otp.otpadmin.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import ngn.otp.otpadmin.data.User;
import ngn.otp.otpadmin.models.UserModel;
//import ngn.otp.otpadmin.security.AuthenticatedUser;
import ngn.otp.otpadmin.security.SecurityService;
import ngn.otp.otpadmin.views.helloworld.HelloWorldView;
import ngn.otp.otpadmin.views.ldap.LdapView;
import ngn.otp.otpadmin.views.organization.OrganizationView;
import ngn.otp.otpadmin.views.user.UserView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

//    private AuthenticatedUser authenticatedUser;
    private final SecurityService securityService;
    private AccessAnnotationChecker accessChecker;

//    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
//        this.authenticatedUser = authenticatedUser;
//        this.accessChecker = accessChecker;
//
//        setPrimarySection(Section.DRAWER);
//        addDrawerContent();
//        addHeaderContent();
//    }
    public MainLayout(SecurityService securityService, AccessAnnotationChecker accessChecker) {
        this.securityService = securityService;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("otp_admin");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(HelloWorldView.class)) {
            nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

        }
        if (accessChecker.hasAccess(UserView.class)) {
            nav.addItem(new SideNavItem("User", UserView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(LdapView.class)) {
            nav.addItem(new SideNavItem("Ldap", LdapView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(OrganizationView.class)) {
            nav.addItem(new SideNavItem("Organization", OrganizationView.class,
                    LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
//
//        Optional<User> maybeUser = authenticatedUser.get();
//        if (maybeUser.isPresent()) {
//            User user = maybeUser.get();
//
//            Avatar avatar = new Avatar(user.getName());
//            StreamResource resource = new StreamResource("profile-pic",
//                    () -> new ByteArrayInputStream(user.getProfilePicture()));
//            avatar.setImageResource(resource);
//            avatar.setThemeName("xsmall");
//            avatar.getElement().setAttribute("tabindex", "-1");
//
//            MenuBar userMenu = new MenuBar();
//            userMenu.setThemeName("tertiary-inline contrast");
//
//            MenuItem userName = userMenu.addItem("");
//            Div div = new Div();
//            div.add(avatar);
//            div.add(user.getName());
//            div.add(new Icon("lumo", "dropdown"));
//            div.getElement().getStyle().set("display", "flex");
//            div.getElement().getStyle().set("align-items", "center");
//            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
//            userName.add(div);
//            userName.getSubMenu().addItem("Sign out", e -> {
////                authenticatedUser.logout();
//            	securityService.logout();
//            });
//
//            layout.add(userMenu);
//        } else {
//            Anchor loginLink = new Anchor("login", "Sign in");
//            layout.add(loginLink);
//        }
        
        Optional<UserModel> maybeUser = securityService.getAuthenticatedUser();
        if (maybeUser.isPresent()) {
        	UserModel user = maybeUser.get();

        	Avatar avatar = new Avatar(user.getFullName());
//        	StreamResource resource = new StreamResource("profile-pic",
//        			() -> new ByteArrayInputStream(user.getProfilePicture()));
//        	avatar.setImageResource(resource);
        	avatar.setThemeName("xsmall");
        	avatar.getElement().setAttribute("tabindex", "-1");

        	MenuBar userMenu = new MenuBar();
        	userMenu.setThemeName("tertiary-inline contrast");

        	MenuItem userName = userMenu.addItem("");
        	Div div = new Div();
        	div.add(avatar);
        	div.add(user.getFullName());
        	div.add(new Icon("lumo", "dropdown"));
        	div.getElement().getStyle().set("display", "flex");
        	div.getElement().getStyle().set("align-items", "center");
        	div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
        	userName.add(div);
        	userName.getSubMenu().addItem("Sign out", e -> {
        		//              authenticatedUser.logout();
        		securityService.logout();
        	});

        	layout.add(userMenu);
        } else {
        	Anchor loginLink = new Anchor("login", "Sign in");
        	layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
