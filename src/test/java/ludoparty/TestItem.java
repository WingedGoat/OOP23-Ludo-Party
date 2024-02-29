package ludoparty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ludoparty.model.api.Item;
import ludoparty.utils.Index;

class TestItem {

    @Test void testDaduplo() {
        assertEquals("Daduplo", Item.DADUPLO.getName());
        assertEquals("Per il prossimo tiro di dado in questo turno il risultato varrà doppio", Item.DADUPLO.getDescription());
        assertEquals(Index.HUNDREDFIFTY, Item.DADUPLO.getPrice());
        assertEquals(Index.ONE, Item.DADUPLO.getId());
    }

    @Test void testAbbondanza() {
        assertEquals("Abbondanza", Item.ABBONDANZA.getName());
        assertEquals("I ludollari raccolti in questo turno raddoppiano", Item.ABBONDANZA.getDescription());
        assertEquals(Index.TWOHUNDREDFIFTY, Item.ABBONDANZA.getPrice());
        assertEquals(Index.TWO, Item.ABBONDANZA.getId());
    }

    @Test void testBastione() {
        assertEquals("Bastione", Item.BASTIONE.getName());
        assertEquals("Fino al tuo prossimo turno non sei targhettabile dai malus degli avversari" 
            + " e le tue pedine non possono venir mangiate", 
            Item.BASTIONE.getDescription());
        assertEquals(Index.FIVEHUNDRED, Item.BASTIONE.getPrice());
        assertEquals(Index.THREE, Item.BASTIONE.getId());
    }

    @Test void testRegolaDei4() {
        assertEquals("La regola dei 4", Item.REGOLA_DEI_4.getName());
        assertEquals("Riporta una pedina avversaria indietro di 4 caselle", Item.REGOLA_DEI_4.getDescription());
        assertEquals(Index.FOURHUNDRED, Item.REGOLA_DEI_4.getPrice());
        assertEquals(Index.FOUR, Item.REGOLA_DEI_4.getId());
    }

    @Test void testTagliatelo() {
        assertEquals("Tagliatelo", Item.TAGLIATELO.getName());
        assertEquals("Il prossimo tiro di dado dell'avversario sara' dimezzato", Item.TAGLIATELO.getDescription());
        assertEquals(Index.THREEHUNDRED, Item.TAGLIATELO.getPrice());
        assertEquals(Index.FIVE, Item.TAGLIATELO.getId());
    }
}
