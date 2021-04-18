package org.fpt.DTO;

import java.util.UUID;

public class ImageDTO {
    private int id;
    private UUID imageId;
    private String fullPath;
    private byte[] data;
    private String type;
    private String confirm_date;

    public ImageDTO() {
    }

    public ImageDTO(int id, UUID imageId, String fullPath, byte[] data, String type, String confirm_date) {
        this.id = id;
        this.imageId = imageId;
        this.fullPath = fullPath;
        this.data = data;
        this.type = type;
        this.confirm_date = confirm_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(String confirm_date) {
        this.confirm_date = confirm_date;
    }
}
