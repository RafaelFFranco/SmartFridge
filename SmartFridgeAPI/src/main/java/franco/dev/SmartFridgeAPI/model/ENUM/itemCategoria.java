package franco.dev.SmartFridgeAPI.model.ENUM;

public enum itemCategoria {
    CARNE("Carne"),
    LEGUME("Legume"),
    FRUTA("Fruta"),
    LATICINIO("Laticínio"),
    GRAO("Grão"),
    VERDURA("Verdura"),
    BEBIDA("Bebida"),
    DOCE("Doce"),
    CEREAL("Cereal"),
    OLEAGINOSA("Oleaginosa");

    private final String tipo;

    itemCategoria(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return tipo;
    }
}

