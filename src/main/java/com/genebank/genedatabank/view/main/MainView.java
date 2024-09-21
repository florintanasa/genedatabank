package com.genebank.genedatabank.view.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private Header header;

    @Subscribe
    public void onInit(final InitEvent event) {
        initManualThemeChange();
    }
    
    protected void initManualThemeChange() {
        JmixButton themeChangeButton = createThemeButton();
        themeChangeButton.addClickListener(e -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
        header.add(themeChangeButton);
    }

    protected JmixButton createThemeButton() {
        JmixButton themeButton = uiComponents.create(JmixButton.class);
        themeButton.setIcon(getThemeIcon());
        themeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_TERTIARY_INLINE);
        String toolTipThemeButton = messageBundle.getMessage("toolTipThemeButtonMessage");
        themeButton.setTooltipText(toolTipThemeButton);
        themeButton.addClassName("ms-auto");
        themeButton.addClassName("me-s");

        return themeButton;
    }

    private Icon getThemeIcon() {
        Icon icon = VaadinIcon.ADJUST.create();
        icon.getElement().getStyle().set("rotate", "180deg");
        return icon;
    }
}
