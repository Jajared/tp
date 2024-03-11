package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TutorialClassTest {

    @Test
    public void equals() {
        TutorialClass tutorialClass = new TutorialClass("T09");

        // same object -> returns true
        assertTrue(tutorialClass.equals(tutorialClass));

        // same values -> returns true
        TutorialClass remarkCopy = new TutorialClass(tutorialClass.value);
        assertTrue(tutorialClass.equals(remarkCopy));

        // different types -> returns false
        assertFalse(tutorialClass.equals(1));

        // null -> returns false
        assertFalse(tutorialClass.equals(null));

        // different remark -> returns false
        TutorialClass differentModule = new TutorialClass("T01");
        assertFalse(tutorialClass.equals(differentModule));
    }
}
