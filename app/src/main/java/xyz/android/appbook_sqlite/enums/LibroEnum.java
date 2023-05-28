package xyz.android.appbook_sqlite.enums;

public enum LibroEnum {

    TABLE_NAME ("mi_libros"),
    COL_ID ("id"),
    COL_TITULO ( "titulo"),
    COL_AUTOR ("autor"),
    COL_PAGINA ("paginas"),
    KEY_NAME("libro");

    private String value;

    LibroEnum(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
