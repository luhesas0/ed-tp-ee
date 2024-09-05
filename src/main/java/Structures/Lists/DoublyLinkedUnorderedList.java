package Structures.Lists;

import Structures.Exceptions.EmptyCollectionException;
import Structures.Exceptions.NoSuchElementException;

public class DoublyLinkedUnorderedList<T> extends DoublyLinkedList<T> implements UnorderedListADT<T> {

    public DoublyLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        if (isEmpty()) {
            head = tail = new DoublyNode<>(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (isEmpty()) {
            head = tail = new DoublyNode<>(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        DoublyNode<T> current = head;
        while (current != null && !current.getElement().equals(target)) {
            current = current.getNext();
        }
        if (current == null) throw new NoSuchElementException();
        if (current == tail) {
            addToRear(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            newNode.setNext(current.getNext());
            newNode.setPrevious(current);
            current.getNext().setPrevious(newNode);
            current.setNext(newNode);
            count++;
            modCount++;
        }
    }
}
