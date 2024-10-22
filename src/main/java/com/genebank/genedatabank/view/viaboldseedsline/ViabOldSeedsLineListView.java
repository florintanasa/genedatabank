package com.genebank.genedatabank.view.viaboldseedsline;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabOldSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : florin
 * @since : 22.10.2024
 **/
@Route(value = "viabOldSeedsLines", layout = MainView.class)
@ViewController("ViabOldSeedsLine.list")
@ViewDescriptor("viab-old-seeds-line-list-view.xml")
@LookupComponent("viabOldSeedsLinesDataGrid")
@DialogMode(width = "64em")
public class ViabOldSeedsLineListView extends StandardListView<ViabOldSeedsLine> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private JmixButton createBtn;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent
    private JmixButton removeBtn;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(user.getUsername(), "admin")) {
            createBtn.setVisible(false);
            editBtn.setVisible(false);
            removeBtn.setVisible(false);
        }
    }
    
}