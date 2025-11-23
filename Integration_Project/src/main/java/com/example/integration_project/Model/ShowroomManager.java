package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShowroomManager {
    private final ObservableList<Showroom> showrooms = FXCollections.observableArrayList();

    public ObservableList<Showroom> getShowrooms() {
        return showrooms;
    }

    public void addShowroom(Showroom showroom) {
        showrooms.add(showroom);
    }

    public void removeShowroom(Showroom showroom) {
        showrooms.remove(showroom);
    }
}
