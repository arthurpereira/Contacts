package br.com.arthurpereira.contacts.enums;

public enum TiposDeImagemPermitidosEnum {

    JPG("image/jpg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif");

    private String mimeType;

    private TiposDeImagemPermitidosEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

}
