package com.genebank.genedatabank.view.deposit;

import com.genebank.genedatabank.entity.Deposit;

import com.genebank.genedatabank.entity.Storage;
import com.genebank.genedatabank.view.main.MainView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


/**
 * @author : Florin Tanasă
 * @since : 05.09.2023
**/
@Route(value = "deposits/:id", layout = MainView.class)
@ViewController("Deposit.detail")
@ViewDescriptor("deposit-detail-view.xml")
@EditedEntityContainer("depositDc")
public class DepositDetailView extends StandardDetailView<Deposit> {

    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private FileStorageUploadField imageqrcodeUpload;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @ViewComponent
    private JmixImage imageqrcode;
    @Autowired
    private Downloader downloader;
    @ViewComponent
    private JmixButton clearImageqrcodeBtn;
    @ViewComponent
    private JmixButton downloadImageqrcodeBtn;
    @ViewComponent
    private JmixButton generateqrcodeBtn;
    @Autowired
    private DataManager dataManager;


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        displayImage();
        updateImageButtons(getEditedEntity().getQrcode() != null);
    }

    @Subscribe("id_storageField")
    public void onId_storageFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Storage>, Storage> event) {
        // If Deposit code is null load the last code inserted for specific Storage
        if (getEditedEntity().getDeposit_code() == null) {
            /* Next lines work for scalar only
               String lastCode = dataManager
                       .loadValue("select d.deposit_code from Deposit d where d.id_storage=:id_storageFields order by d.createdDate desc",
                               String.class).parameter("id_storageFields",getEditedEntity().getId_storage())
                       .one();
             */
            List<KeyValueEntity> lastCode = dataManager
                    .loadValues("select d.deposit_code from Deposit d where d.id_storage=:id_storage_id order by d.createdDate desc")
                    .parameter("id_storage_id", getEditedEntity().getId_storage())
                    .store("main")
                    .properties("deposit_code")
                    .maxResults(1)
                    .list();

            if (!lastCode.isEmpty()) {
                getEditedEntity().setDeposit_code(lastCode.get(0).getValue("deposit_code"));
            }
            else getEditedEntity().setDeposit_code("NA");
        }
    }

    @Subscribe("generateqrcodeBtn")
    public void onGenerateQrCodeBtnClick(ClickEvent<Button> event) throws IOException, WriterException {
      generateQrCode();
    }
    public void generateQrCode() throws WriterException, IOException {
        //I got the current date and time
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        String currentDateToday = dateFormat.format(currentDate);
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeNow = dateTimeFormat.format(now);

        String County;
        String Locality;

        if (getEditedEntity().getId_accenumb().getId_countysiruta() == null ) {
            County = "NA";
        }
        else County = getEditedEntity().getId_accenumb().getId_countysiruta().getName();

        if (getEditedEntity().getId_accenumb().getId_localitysiruta() == null ) {
            Locality = "NA";
        }
        else Locality = getEditedEntity().getId_accenumb().getId_localitysiruta().getName();

        //the date necessary to be added in QR code
        String data = getEditedEntity().getId_accenumb().getAccenumb() + "|"
                + getEditedEntity().getDeposit_code() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getFamily() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getGenus() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpauthor() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSubtaxa() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getSubauthor() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getSyn_taxono() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getCropnume() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getCropname() + "|"
//                + getEditedEntity().getId_accenumb().getId_taxonomy().getId_culturecateg().iterator().next().getName() + "|"
                + getEditedEntity().getId_accenumb().getAccname() + "|"
                + getEditedEntity().getId_accenumb().getId_country().getName() + "|"
                + County + "|"
                + Locality + "|"
//                + getEditedEntity().getId_accenumb().getId_donorcode().getInstcode() + "|"
//                + getEditedEntity().getYearstorage() + "|"
                + getEditedEntity().getId_accenumb().getId_sampstat().getCodespe() + "|"
                + dateTimeNow;

        //I check if the folder qrCodeImage exist in current users, if not exist I created this directory
        String currentUserHomeDir = System.getProperty("user.home");
        String qrCodeImageFolder = currentUserHomeDir + File.separator + "qrCodeImage";
        File theDir = new File(qrCodeImageFolder);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        //I set the file name for qrCodeImage
        String fileName = getEditedEntity().getId_accenumb().getAccenumb() + "-"
                + getEditedEntity().getDeposit_code() + "-"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getGenus() + "-"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() + "-"
                + getEditedEntity().getYearstorage();
        String qrCodeImageFile = qrCodeImageFolder + File.separator + fileName + "-" + currentDateToday + ".jpg";

        //encode the data in format QR_CODE with width 500 and height 500
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);

        //Write the matrix in the path
        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(qrCodeImageFile));

        //Notification for user
        String okMessage = messageBundle.getMessage("okMessageQRcode");
        notifications.create("OK", okMessage).show();
    }

    @Subscribe("imageqrcodeUpload")
    public void onImageqrcodeUploadFileUploadSucceed(final FileUploadSucceededEvent<FileStorageUploadField> event){
        Receiver receiver = event.getReceiver();
        if (receiver instanceof FileTemporaryStorageBuffer) {
            UUID fileID = ((FileTemporaryStorageBuffer) receiver).getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileID);

            if (file != null) {
                //notifications.create("Fișierul este salvat temporar la " + file.getAbsolutePath()).show();
            }
            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileID, event.getFileName());
            imageqrcodeUpload.setValue(fileRef);
            displayImage();
            notifications.create("Încarcă fișierul: "
                    + ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName()).show();

        }
    }

    @Subscribe("downloadImageqrcodeBtn")
    public void onDownloadImageQrCodeBtnClick(ClickEvent<Button> event) {
        FileRef fileRef = getEditedEntity().getQrcode();
        downloader.download(fileRef);
        //downloadFromFileStorage();
    }

    private void downloadFromFileStorage(byte[] content) {
        //FileRef fileRef = getEditedEntity().getQrcode();
        //downloader.download(fileRef);
        //downloader.download(content, getEditedEntity().getQrcode().getFileName(), DownloadFormat.JPG);
    }

    @Subscribe("clearImageqrcodeBtn")
    public void onClearImageQrCodeBtnClick(ClickEvent<Button> event) {
        getEditedEntity().setQrcode(null);
        displayImage();
    }

    public void displayImage() {
        imageqrcode.setVisible(getEditedEntity().getQrcode() != null);
    }

    public void updateImageButtons(boolean enable) {
        downloadImageqrcodeBtn.setVisible(enable);
        clearImageqrcodeBtn.setVisible(enable);
        generateqrcodeBtn.setVisible(!enable);
        imageqrcodeUpload.setVisible(!enable);
    }
}