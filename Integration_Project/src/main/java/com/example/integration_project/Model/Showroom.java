package com.example.integration_project.Model;

public class Showroom {
    private int roomNumber;
    private int roomCapacity;

    public Showroom(int roomNumber, int roomCapacity) {
        setRoomNumber(roomNumber);
        setRoomCapacity(roomCapacity);
    }

    public int getCapacity() {
        return roomCapacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be greater than 0");
        }
        this.roomNumber = roomNumber;
    }

    public void setRoomCapacity(int roomCapacity) {
        if (roomCapacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
        this.roomCapacity = roomCapacity;
    }

}