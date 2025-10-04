package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        EditPersonDescriptor descriptorAmy = new EditPersonDescriptorBuilder().withName("Amy Bee")
                .withPhone("11111111").withEmail("amy@example.com").withAddress("Block 312, Amy Street 1").build();
        EditPersonDescriptor descriptorBob = new EditPersonDescriptorBuilder().withName("Bob Choo")
                .withPhone("22222222").withEmail("bob@example.com").withAddress("Block 123, Bobby Street 3").build();
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(descriptorAmy);
        assertTrue(descriptorAmy.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptorAmy.equals(descriptorAmy));

        // null -> returns false
        assertFalse(descriptorAmy.equals(null));

        // different types -> returns false
        assertFalse(descriptorAmy.equals(5));

        // different values -> returns false
        assertFalse(descriptorAmy.equals(descriptorBob));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(descriptorAmy)
                .withName(VALID_NAME_BOB).build();
        assertFalse(descriptorAmy.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(descriptorAmy).withPhone(VALID_PHONE_BOB).build();
        assertFalse(descriptorAmy.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(descriptorAmy).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(descriptorAmy.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(descriptorAmy).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(descriptorAmy.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(descriptorAmy).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(descriptorAmy.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName()
                + "{name=" + editPersonDescriptor.getName().orElse(null)
                + ", phone=" + editPersonDescriptor.getPhone().orElse(null)
                + ", email=" + editPersonDescriptor.getEmail().orElse(null)
                + ", address=" + editPersonDescriptor.getAddress().orElse(null)
                + ", remark=" + editPersonDescriptor.getRemark().orElse(null)
                + ", tags=" + editPersonDescriptor.getTags().orElse(null)
                + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
