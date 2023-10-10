package com.genebank.genedatabank.view.sysfile;

import com.genebank.genedatabank.entity.SysFile;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;


/**
 * @author : Florin Tanasă
 * @since : 04.10.2023
**/
@Route(value = "sysFiles/:id", layout = MainView.class)
@ViewController("SysFile.detail")
@ViewDescriptor("sys-file-detail-view.xml")
@EditedEntityContainer("sysFileDc")
public class SysFileDetailView extends StandardDetailView<SysFile> {
    @ViewComponent
    private FileStorageUploadField fileField;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @Autowired
    private TemporaryStorage temporaryStorage;

    @Subscribe("fileField")
    public void onFileFieldFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {
        String message_1 = messageBundle.getMessage("upload_temporary");
        String message_2 = messageBundle.getMessage("upload");
        Receiver receiver = event.getReceiver();
        if (receiver instanceof FileTemporaryStorageBuffer) {
            UUID fileID = ((FileTemporaryStorageBuffer) receiver).getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileID);

            if (file !=null) {
                notifications.create(message_1 + file.getAbsolutePath())
                        .withDuration(6000).withPosition(Notification.Position.BOTTOM_END).show();

                String nameOfFile = ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName();
                if (!nameOfFile.contains(".")) {
                    getEditedEntity().setName(nameOfFile);
                }
                else {
                    getEditedEntity().setName(nameOfFile.substring(0, nameOfFile.lastIndexOf(".")));
                    getEditedEntity().setExtension(nameOfFile.substring(nameOfFile.lastIndexOf(".")+1));
                }
                getEditedEntity().setSize(((FileTemporaryStorageBuffer) receiver).getFileData().getFileInfo().getFile().length());
            }

            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileID, event.getFileName());
            fileField.setValue(fileRef);

            notifications.create(message_2 + ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName())
                    .withDuration(4000).withPosition(Notification.Position.BOTTOM_END).show();
        }
    }
}