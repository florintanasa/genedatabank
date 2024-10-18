package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedsLines/:id", layout = MainView.class)
@ViewController("ViabNewSeedsLine.detail")
@ViewDescriptor("viab-new-seeds-line-detail-view.xml")
@EditedEntityContainer("viabNewSeedsLineDc")
public class ViabNewSeedsLineDetailView extends StandardDetailView<ViabNewSeedsLine> {
    @ViewComponent
    private TypedTextField<Integer> seedsNumField;
    @ViewComponent
    private TypedTextField<Integer> viableSeedsField;
    @ViewComponent
    private TypedTextField<Integer> germFacultyField;
    @Autowired
    private NumberFormatter numberFormatter;
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabNewSeedsLine> event) {
        //set the date with current date
        event.getEntity().setGermStartDate(timeSource.now().toLocalDate());
        event.getEntity().setSeedsNum(0);
        event.getEntity().setViableSeeds(0);
    }

    @Subscribe
    public void onValidation(final ValidationEvent event) {
        if (getEditedEntity().getGermFaculty() > 100 || getEditedEntity().getGermFaculty() < 0) {
            String canNotSaveGermFaculty = messageBundle.getMessage("can_not_save_germ_faculty");
            event.getErrors().add(messageBundle.getMessage(canNotSaveGermFaculty));
            notifications.create("HOPA", canNotSaveGermFaculty).withDuration(5000).show();
        }
    }
    
    

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        //check if value in seeds number field was changed and then calculate viability percentage
        seedsNumField.addValueChangeListener(valueChangeEvent -> {
            Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
            Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
            double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);

            germFacultyField.setValue(Objects.requireNonNull(numberFormatter.apply((int) valueGermFaculty)));
        });

        viableSeedsField.addValueChangeListener(valueChangeEvent -> {
            Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
            Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
            double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);

            germFacultyField.setValue(Objects.requireNonNull(numberFormatter.apply((int) valueGermFaculty)));
        });
    }

}