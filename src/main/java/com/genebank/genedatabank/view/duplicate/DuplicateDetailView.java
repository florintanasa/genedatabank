package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.entity.DuplicateStatus;
import com.genebank.genedatabank.entity.Institute;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "duplicates/:id", layout = MainView.class)
@ViewController("Duplicate.detail")
@ViewDescriptor("duplicate-detail-view.xml")
@EditedEntityContainer("duplicateDc")
public class DuplicateDetailView extends StandardDetailView<Duplicate> {
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private EntityPicker<Institute> id_duplicate_instituteField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Duplicate> event) {
        //set default the Status to Prepared
        event.getEntity().setStatus(DuplicateStatus.PREPARED);
        //set default the date to now
        event.getEntity().setTheDate(timeSource.now().toLocalDate());

        //focus to Duplicate Institute field for choose
        id_duplicate_instituteField.focus();
    }
    
}