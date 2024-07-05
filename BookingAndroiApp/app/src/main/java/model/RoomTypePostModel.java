package model;

import okhttp3.MultipartBody;

public class RoomTypePostModel {
    private Integer capacity;
    private Double price;
    private MultipartBody.Part imageFile;

    public RoomTypePostModel(Integer capacity, Double price, MultipartBody.Part imageFile) {
        this.capacity = capacity;
        this.price = price;
        this.imageFile = imageFile;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MultipartBody.Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartBody.Part imageFile) {
        this.imageFile = imageFile;
    }
}
