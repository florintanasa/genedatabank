/**
 * @author : Florin Tanasă
 * @since : 29.09.2024
 **/

package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.entity.DuplicateStatus;
import com.genebank.genedatabank.entity.Institute;
import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "duplicates/:id", layout = MainView.class)
@ViewController("Duplicate.detail")
@ViewDescriptor("duplicate-detail-view.xml")
@EditedEntityContainer("duplicateDc")
public class DuplicateDetailView extends StandardDetailView<Duplicate> {
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private EntityComboBox<Institute> id_duplicate_instituteComboBox;
    @ViewComponent
    private EntityComboBox<Institute> id_send_instituteComboBox;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Duplicate> event) {
        //set default the Status to Prepared
        event.getEntity().setStatus(DuplicateStatus.PREPARED);
        //set default the date to now
        event.getEntity().setTheDate(timeSource.now().toLocalDate());
        //focus to Duplicate Institute field for choose
        id_duplicate_instituteComboBox.focus();
    }

    // check what user try to save, if is not from the same institute him can't save
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(getEditedEntity().getId_send_institute(), user.getId_institute())) {
            String can_not_insert_for_over_institute = messageBundle.getMessage("can_not_insert_for_over_institute");
            event.getErrors().add(messageBundle.getMessage("can_not_insert_for_over_institute"));
            notifications.create("HOPA", can_not_insert_for_over_institute).withDuration(5000).show();
        }
    }

    
    
}