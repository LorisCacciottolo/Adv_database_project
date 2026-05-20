package com.project.artconnect.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.application.Platform;

public class MainController {
    @FXML private TabPane mainTabPane;

    @FXML private ArtistController artistTabController;
    @FXML private ArtworkController artworkTabController;
    @FXML private CommunityController communityTabController;
    @FXML private DiscoverController discoverTabController;
    @FXML private ExhibitionController exhibitionTabController;
    @FXML private GalleryController galleryTabController;
    @FXML private WorkshopController workshopTabController;

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleGlobalRefresh() {
        if (artistTabController != null) artistTabController.refreshData();
        if (artworkTabController != null) artworkTabController.refreshData();
        if (communityTabController != null) communityTabController.refreshData();
        if (discoverTabController != null) discoverTabController.refreshData();
        if (exhibitionTabController != null) exhibitionTabController.refreshData();
        if (galleryTabController != null) galleryTabController.refreshData();
        if (workshopTabController != null) workshopTabController.refreshData();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}