package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;

    private ArrayList<Entry> list = new ArrayList<>();

    public CustomTree() {
        this.root = new Entry<>("0");
        list.add(0, root);
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        void checkChildren() {
            if (leftChild != null) availableToAddLeftChildren = false;
            if (rightChild != null) availableToAddRightChildren = false;
        }
    }

    @Override
    public String get(int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        Entry newEntry = new Entry(s);
        for (Entry current : list) {
            if (current.isAvailableToAddChildren()) {
                if (current.leftChild == null) {
                    current.leftChild = newEntry;
                } else
                    current.rightChild = newEntry;
                newEntry.parent = current;
                current.checkChildren();
                list.add(newEntry);

                return true;
            }
        }
        return false;
    }

    public String getParent(String s) {
        for (Entry item : list) {
            if (item.elementName.equals(s))
                return item.parent.elementName;

        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) throw new UnsupportedOperationException();
        Entry<String> delObj = getElementByName(o.toString());
        Entry<String> parent = delObj.parent;

        if (!delObj.availableToAddLeftChildren) {
            remove(delObj.leftChild.elementName);
        }
        if (!delObj.availableToAddRightChildren) {
            remove(delObj.rightChild.elementName);
        }
        if (delObj.leftChild == null && delObj.rightChild == null) {
            if (parent.leftChild == delObj) {
                parent.leftChild = null;
                parent.availableToAddLeftChildren = true;
            } else {
                parent.rightChild = null;
                parent.availableToAddRightChildren = true;
            }
            list.remove(delObj);
        }
        return true;
    }

    public Entry getElementByName(String s) {
        Entry tmp = null;
        for (Entry e : list) {
            if (e.elementName.equals(s))
                tmp = e;
        }
        return tmp;
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return list.size() - 1;
    }
}
