/**
 * @author : Florin Tanasă
 * @since : 29.09.2024
 **/

package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.*;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.runner.ReportRunner;
import io.jmix.reports.yarg.reporting.ReportOutputDocument;
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
    @Autowired
    private ReportRunner reportRunner;
    @ViewComponent
    private DataGrid<DuplicateLine> duplicateLinesDataGrid;
    @Autowired
    private Downloader downloader;
    @ViewComponent
    private JmixButton printBtn75x35;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Duplicate> event) {
        //set default the Status to Prepared
        event.getEntity().setStatus(DuplicateStatus.PREPARED);
        //set default the date to now
        event.getEntity().setTheDate(timeSource.now().toLocalDate());
        //focus to Duplicate Institute field for choose
        id_duplicate_instituteComboBox.focus();
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // disable Print button for label
        printBtn75x35.setEnabled(false);
        // check if one item is selected to validate print button
        duplicateLinesDataGrid.addItemClickListener(ClickEvent -> {
          if (duplicateLinesDataGrid.getSelectedItems().isEmpty()) {
              printBtn75x35.setEnabled(false);
          } else if (duplicateLinesDataGrid.getSelectedItems().size() == 1) {
              printBtn75x35.setEnabled(true);
          }
        });
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

    @Subscribe(id = "printBtn75x35", subject = "clickListener")
    public void onPrintBtn75x35Click(final ClickEvent<JmixButton> event) {
        ReportOutputDocument label = reportRunner.byReportCode("LabSval")
                .addParam("duplicateline",duplicateLinesDataGrid.getSelectedItems().iterator().next())
                .withTemplateCode("LabSval_V1")
                .withOutputType(ReportOutputType.PDF)
                .run();
        downloader.download(label.getContent(), label.getDocumentName());
    }

    
    
}