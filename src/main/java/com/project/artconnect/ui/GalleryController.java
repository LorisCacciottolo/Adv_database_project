package com.project.artconnect.ui;

import com.project.artconnect.model.Gallery;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.util.ServiceProvider;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class GalleryController {
    @FXML
    private ListView<Gallery> galleryList;

    private final GalleryService galleryService = ServiceProvider.getGalleryService();

    @FXML
    public void initialize() {
        galleryList.setItems(FXCollections.observableArrayList(galleryService.getAllGalleries()));

        galleryList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Gallery item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    String orgName = "Unknown";
                    if (item.getOrganizer() != null) {
                        orgName = item.getOrganizer().getName();
                    }

                    setText(item.getName() + " (Organizer : " + orgName + ") - " +
                            item.getAddress() + " (" + item.getRating() + "/5.0)");
                }
            }
        });
    }

    public void refreshData() {
        galleryList.setItems(FXCollections.observableArrayList(galleryService.getAllGalleries()));
    }
}