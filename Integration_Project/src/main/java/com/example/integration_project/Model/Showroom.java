package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Showroom {
    private String room;
    private final ObservableList<Showroom> rooms = FXCollections.observableArrayList();

    public ObservableList<Showroom> getRooms() {
        return rooms;
    }

    public Showroom(String room) {
        setRoomNumber(room);
    }

    public String getName() {
        return room;
    }
    public void setRoomNumber(String room) {
        if (room == null || room.isBlank()) {
            throw new IllegalArgumentException("Room number must not be empty");
        }
        this.room = room.trim();
    }
    public String addRoom (Showroom showroom) {
        rooms.add(showroom);
        return room;
    }
    public String removeRoom (Showroom showroom) {
        rooms.remove(showroom);
        return room;
    }
    public String editRoom () {
        return room;
    }
}