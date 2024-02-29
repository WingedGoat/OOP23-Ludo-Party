package ludoparty.model.api;

/**
 * Enum item.
 * 
 * @see DADUPLO
 * @see ABBONDANZA
 * @see BASTIONE
 * @see REGOLA_DEI_4
 * @see TAGLIATELO
 * @see ARIETE
 */
public enum Item {

    /**
     * Daduplo.
     */
    DADUPLO(
            ItemType.BONUS,
            "Daduplo",
            "Per il prossimo tiro di dado in questo turno il risultato varrà doppio",
            150,
            1),
    /**
     * Abbondanza.
     */
    ABBONDANZA(
            ItemType.BONUS,
            "Abbondanza",
            "I ludollari raccolti in questo turno raddoppiano",
            250,
            2),
    /**
     * Bastione.
     */
    BASTIONE(ItemType.BONUS,
            "Bastione",
            "Fino al tuo prossimo turno non sei targhettabile dai malus degli avversari "
                    + "e le tue pedine non possono venir mangiate",
            500,
            3),
    /**
     * La regola dei 4.
     */
    REGOLA_DEI_4(
            ItemType.MALUS,
            "La regola dei 4",
            "Riporta una pedina avversaria indietro di 4 caselle",
            400,
            4),
    /**
     * Tagliatelo.
     */
    TAGLIATELO(
            ItemType.MALUS,
            "Tagliatelo",
            "Il prossimo tiro di dado dell'avversario sara' dimezzato",
            300,
            5);

    private ItemType type;
    private String name;
    private String description;
    private int price;
    private int id;

    Item(final ItemType type, final String name, final String description, final int price, final int id) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    /**
     * Return the item type.
     * 
     * @return the item type
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Return the name of the item.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the description of an item and its interaction.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the price of the item.
     * 
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Return the id of the item.
     * 
     * @return the item id
     */
    public int getId() {
        return id;
    }

    /**
     * Enum item type.
     * @see BONUS
     * @see MALUS
     */
    public enum ItemType {
        /**
         * Bonus item.
         */
        BONUS,
        /**
         * Malus item.
         */
        MALUS;
    }
}
