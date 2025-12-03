package com.example.integration_project.Model;

/**
 * Represents a showroom in the theater system.
 * Encapsulates showroom information including room number and capacity.
 * 
 * @author Ian
 * @version 1.0
 */
public class Showrooms {
    private int roomNumber;
    private int roomCapacity;

    /**
     * Constructs a Showroom with the specified room number and capacity.
     * 
     * @param roomNumber   the room number (must be greater than 0)
     * @param roomCapacity the capacity of the room (must be greater than 0)
     * @throws IllegalArgumentException if roomNumber or roomCapacity is not greater
     *                                  than 0
     */
    public Showrooms(int roomNumber, int roomCapacity) {
        setRoomNumber(roomNumber);
        setRoomCapacity(roomCapacity);
    }

    /**
     * Gets the capacity of the showroom.
     * 
     * @return the capacity of the showroom
     */
    public int getCapacity() {
        return roomCapacity;
    }

    /**
     * Gets the room number of the showroom.
     * 
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number with validation.
     * 
     * @param roomNumber the room number to set (must be greater than 0)
     * @throws IllegalArgumentException if roomNumber is not greater than 0
     */
    public void setRoomNumber(int roomNumber) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be greater than 0");
        }
        this.roomNumber = roomNumber;
    }

    /**
     * Sets the room capacity with validation.
     * 
     * @param roomCapacity the capacity to set (must be greater than 0)
     * @throws IllegalArgumentException if roomCapacity is not greater than 0
     */
    public void setRoomCapacity(int roomCapacity) {
        if (roomCapacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
        this.roomCapacity = roomCapacity;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (Capacity: " + roomCapacity + ")";
    }

}