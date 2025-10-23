package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A generic list that enforces uniqueness between its elements and does not allow nulls.
 * Elements are considered unique by comparing using a custom equality method provided by subclasses.
 *
 * @param <T> The type of elements in the list
 */
public abstract class UniqueList<T> implements Iterable<T> {

    protected final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(item -> isSameElement(item, toCheck));
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw createDuplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code editedElement}.
     * {@code target} must exist in the list.
     * The element identity of {@code editedElement} must not be the same as another existing element in the list.
     */
    public void setElement(T target, T editedElement) {
        requireAllNonNull(target, editedElement);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw createNotFoundException();
        }

        if (!isSameElement(target, editedElement) && contains(editedElement)) {
            throw createDuplicateException();
        }

        internalList.set(index, editedElement);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw createNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code elements}.
     * {@code elements} must not contain duplicate elements.
     */
    public void setElements(List<T> elements) {
        requireAllNonNull(elements);
        if (!elementsAreUnique(elements)) {
            throw createDuplicateException();
        }
        internalList.setAll(elements);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Sets all elements from another UniqueList of the same type.
     * This method is protected to allow subclasses to implement their own set methods.
     */
    protected void setAllFromOther(UniqueList<T> other) {
        internalList.setAll(other.internalList);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueList)) {
            return false;
        }

        UniqueList<?> otherUniqueList = (UniqueList<?>) other;
        return internalList.equals(otherUniqueList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    // Abstract methods to be implemented by subclasses
    /**
     * Returns true if the two elements are considered the same for uniqueness purposes.
     */
    protected abstract boolean isSameElement(T element1, T element2);

    /**
     * Creates the appropriate duplicate exception for this list type.
     */
    protected abstract RuntimeException createDuplicateException();

    /**
     * Creates the appropriate not found exception for this list type.
     */
    protected abstract RuntimeException createNotFoundException();

    /**
     * Returns true if {@code elements} contains only unique elements.
     */
    private boolean elementsAreUnique(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (isSameElement(elements.get(i), elements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
