package model;

import model.api.Item;

/**
 * Item Implemetation class.
 */
public final class ItemImpl implements Item {

    private final ItemName name;
    private final ItemDescription description;
    private final int price;
    private final ItemType type;
    private final int id;

    /**
     * Enumeration class for the Items name.
     */
    public enum ItemName {

        /**
         * "Daduplo" name.
         */
        DADUPLO("Daduplo"),
        /**
         * "Abbondanza" name.
         */
        ABBONDANZA("Abbondanza"),
        /**
         * "Bastione" name.
         */
        BASTIONE("Bastione"),
        /**
         * "Tagliatelo" name.
         */
        TAGLIATELO("Tagliatelo"),
        /**
         * "La regola dei 4" name.
         */
        REGOLA_DEI_4("La regola dei 4"),
        /**
         * "Ariete" name.
         */
        ARIETE("Ariete");

        private final String name;

        ItemName(final String name) {
            this.name = name;
        }

        String getNome() {
            return this.name;
        }

    }

    /**
     * Enumeration class for the Items description.
     */
    public enum ItemDescription {

        /**
         * "Daduplo" description.
         */
        DADUPLO_DESC("Al prossimo tiro di dado ne puoi tirare un altro."),
        /**
         * "Abbondanza" description.
         */
        ABBONDANZA_DESC("I coin raccolti raddoppiano per questo turno."),
        /**
         * "Bastione" description.
         */
        BASTIONE_DESC("Fino al tuo prossimo turno non sei targhettabile dai malus degli avversari" 
                      + " e le tue pedine non possono venir mangiate."),
        /**
         * "Tagliatelo" description.
         */
        TAGLIATELO_DESC("Il prossimo tiro di dado dell'avversario sarà dimezzato."),
        /**
         * "La regola dei 4" description.
         */
        REGOLA_DEI_4_DESC("Riporta una pedina avversaria indietro di 4 caselle."),
        /**
         * "Ariete" description.
         */
        ARIETE_DESC("Disattiva anticipatamente il bastione di un avversario.");

        private final String description;

        ItemDescription(final String description) {
            this.description = description;
        }

        String getDescrizione() {
            return this.description;
        }

    }

    /**
     * Constructor.
     * 
     * @param name
     *          the name of the item
     * @param description
     *          the description of the item
     * @param price
     *          the price of the item
     * @param type
     *          the type of the item
     * @param id
     *          the number id of the item
     */
    public ItemImpl(final ItemName name, final ItemDescription description, 
                    final int price, final ItemType type, final int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.id = id;
    }

    @Override
    public String getName() {
        return name.getNome();
    }

    @Override
    public String getDescription() {
        return description.getDescrizione();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getTypeString() {
        if (this.type == ItemType.MALUS) {
            return "Malus";
        } else {
            return "Bonus";
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String itemToString() {
        return  "Item: " + this.getName() + ".\n" 
                + "Descrizione: " + this.getDescription() + "\n" 
                + "Tipologia: " + this.getTypeString() + ".\n" 
                + "Prezzo: " + this.getPrice() + ".\n";
    }

    @Override
    public boolean isBonus() {
        return this.type == ItemType.BONUS;
    }

}
