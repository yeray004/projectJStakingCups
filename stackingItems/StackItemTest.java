import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * The test class StackItemTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StackItemTest {
    private Cup bigCup;
    private Cup smallCup;
    private Lid sameSizeLid;
    private Lid smallLid;

    @BeforeEach
    public void setUp() {
        bigCup = new Cup(1, "blue", 400, 600, 5);
        smallCup = new Cup(2, "green", 400, 600, 3);
        sameSizeLid = new Lid(3, "blue", 400, 600, 5);
        smallLid = new Lid(4, "green", 400, 600, 3);
    }

    @Test
    public void shouldReturnCupAsTypeName() {
        assertEquals("cup", bigCup.getTypeName());
    }

    @Test
    public void shouldReturnLidAsTypeName() {
        assertEquals("lid", sameSizeLid.getTypeName());
    }

    @Test
    public void shouldIdentifyCupByPolymorphicTypeQuery() {
        assertTrue(bigCup.isCup());
        assertFalse(bigCup.isLid());
    }

    @Test
    public void shouldIdentifyLidByPolymorphicTypeQuery() {
        assertTrue(sameSizeLid.isLid());
        assertFalse(sameSizeLid.isCup());
    }

    @Test
    public void shouldRecognizeItemsWithSameLogicalType() {
        assertTrue(bigCup.hasSameTypeAs(smallCup));
        assertTrue(sameSizeLid.hasSameTypeAs(smallLid));
        assertFalse(bigCup.hasSameTypeAs(sameSizeLid));
    }

    @Test
    public void shouldDetectConflictOnlyWhenTypeAndSizeAreEqual() {
        Cup anotherCupWithSameSize = new Cup(5, "red", 400, 600, 5);

        assertTrue(bigCup.conflictsWith(anotherCupWithSameSize));
        assertFalse(bigCup.conflictsWith(sameSizeLid));
        assertFalse(bigCup.conflictsWith(smallCup));
    }

    @Test
    public void shouldAllowCupToContainSmallerCup() {
        assertTrue(bigCup.canContain(smallCup));
    }

    @Test
    public void shouldAllowCupToContainSmallerLid() {
        assertTrue(bigCup.canContain(smallLid));
    }

    @Test
    public void shouldNotAllowCupToContainSameSizeLid() {
        assertFalse(bigCup.canContain(sameSizeLid));
    }

    @Test
    public void shouldNotAllowLidToContainAnotherItem() {
        assertFalse(sameSizeLid.canContain(smallCup));
    }

    @Test
    public void shouldAllowLidToSealCupOfSameSize() {
        assertTrue(sameSizeLid.canSeal(bigCup));
    }

    @Test
    public void shouldNotAllowLidToSealCupOfDifferentSize() {
        assertFalse(smallLid.canSeal(bigCup));
    }

    @Test
    public void shouldNotAllowCupToSealAnotherItem() {
        assertFalse(bigCup.canSeal(sameSizeLid));
    }

    @Test
    public void shouldReturnTopUsingItsOwnHeightUnits() {
        assertEquals(450, bigCup.getTop());
        assertEquals(570, sameSizeLid.getTop());
    }

    @Test
    public void shouldReturnSealYAsTop() {
        assertEquals(bigCup.getTop(), bigCup.getSealY());
        assertEquals(sameSizeLid.getTop(), sameSizeLid.getSealY());
    }

    @Test
    public void shouldReturnInnerFloorOneUnitAboveCupBase() {
        assertEquals(570, bigCup.getInnerFloorY());
    }

    @Test
    public void shouldReturnCurrentYAsInnerFloorForLid() {
        assertEquals(600, sameSizeLid.getInnerFloorY());
    }

    @Test
    public void shouldRecognizeSealedCupOnlyWhenPartnerExists() {
        assertFalse(bigCup.isSealedCup());

        bigCup.setPartnerId(sameSizeLid.getId());

        assertTrue(bigCup.isSealedCup());
        assertFalse(sameSizeLid.isSealedCup());
    }

    @Test
    public void shouldReturnCupBeforeLidPriorityWhenSizeIsEqual() {
        assertTrue(bigCup.getOrderPriority() < sameSizeLid.getOrderPriority());
    }

    @Test
    public void shouldCalculateFloorYForFirstItem() {
        assertEquals(600, bigCup.calculateY(List.of(bigCup), 0, 600, 600));
    }

    @Test
    public void shouldCalculateInnerYWhenItemFitsInsideCup() {
        assertEquals(570, smallCup.calculateY(List.of(bigCup, smallCup), 1, 450, 600));
    }

    @Test
    public void shouldCalculateSealYWhenLidMatchesCup() {
        Lid sealingLid = new Lid(5, "blue", 400, 600, 5);

        assertEquals(450, sealingLid.calculateY(List.of(bigCup, sealingLid), 1, 450, 600));
    }

    @Test
    public void shouldFindPartnerForLidWhenItIsPlacedAtSealY() {
        Lid sealingLid = new Lid(5, "blue", 400, bigCup.getSealY(), 5);

        assertSame(bigCup, sealingLid.findPartner(List.of(bigCup, sealingLid)));
    }

    @Test
    public void shouldNotFindPartnerForCup() {
        assertNull(bigCup.findPartner(List.of(bigCup, sameSizeLid)));
    }
}