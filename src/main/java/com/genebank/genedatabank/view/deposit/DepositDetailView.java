package com.genebank.genedatabank.view.deposit;

import com.genebank.genedatabank.entity.Deposit;

import com.genebank.genedatabank.view.main.MainView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
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


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        displayImage();
        updateImageButtons(getEditedEntity().getQrcode() != null);
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

        //the date necessary to input in QR code
        String data = getEditedEntity().getId_accenumb().getAccenumb() + "-"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() +"-"
                + getEditedEntity().getYearstorage() + "-"
                + getEditedEntity().getDeposit_code() + "-"
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
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() +"-"
                + getEditedEntity().getDeposit_code() + "-"
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