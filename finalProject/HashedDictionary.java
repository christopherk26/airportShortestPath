//
// Name:       Kurdoghlian, Christopher
// Project:    4
// Due:        April 30, 2024
// Course:     cs-2400-03-sp24
//
// Description:
//             In this project we create an airport app where we can find the optimal path between
//             two airports, given their names and locations. We can also query Airports using their code. 
//

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that implements the ADT dictionary by using a resizable array.
 * The dictionary is unsorted and has distinct search keys.
 * Search keys and associated values are not null.
 */
public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {
    private Entry<K, V>[] dictionary; // Array of unsorted entries
    private int numberOfEntries;
    private int collisionCount;

    public HashedDictionary() {
        this(31);
    }

    public HashedDictionary(int initialCapacity) {
        // The cast is safe because the new array contains null entries
        // @SuppressWarnings("unchecked")
        @SuppressWarnings("unchecked")
        Entry<K, V>[] tempDictionary = (Entry<K, V>[]) new Entry[initialCapacity];
        dictionary = tempDictionary;
        numberOfEntries = 0;
    } // end constructor

    // hashing, then modding to get the index
    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % dictionary.length;
        if (hashIndex < 0) {
            hashIndex += dictionary.length;
        }
        return hashIndex;
    }

    // some getters.
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    @Override
    public V add(K key, V value) {
        // check for null values
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cannot have null key or value");
        }

        int hashIndex = getHashIndex(key);
        boolean collisionOccured = false;
        int pos = 0;
        Entry<K, V> curr = null;
        // do the search starting at the hashIndex
        // ensure that we havent checked every position
        while (pos < dictionary.length) {
            curr = dictionary[(hashIndex + pos) % dictionary.length];
            if (curr == null) {
                // this would mean it is a new unique entry

                dictionary[(hashIndex + pos) % dictionary.length] = new Entry<K, V>(key, value);
                // add to number of entries, since it is unique
                numberOfEntries++;
                return null;
                // now, if the key we have is equal to the key of the spot we are looking in...
            } else if (curr.getKey().equals(key)) {

                // save the existing value to return it.
                V existingVal = curr.getValue();
                // set the exisitng value to the new value using setter.
                dictionary[(hashIndex + pos) % dictionary.length].setValue(value);
                ;
                return existingVal;
            }
            // linear probe.
            pos++;
            // if we are incrementing i at this point without yet returning
            // anything, then there must have been a collision, since the first
            // spot was already taken.

            // note that we dont increment it every time - only once
            // if that first spot was already taken
            if (!collisionOccured) {
                collisionCount++;
                collisionOccured = true;
            }
        }
        return null;
    }

    @Override
    public V getValue(K key) {
        // check for null value
        if (key == null) {
            throw new IllegalArgumentException("Cannot have null key");
        }
        int hashIndex = getHashIndex(key);
        int pos = 0;
        Entry<K, V> curr = null;

        // do the search starting at the hashIndex
        // ensure that we havent checked every position with pos
        while (pos < dictionary.length) {
            curr = dictionary[(hashIndex + pos) % dictionary.length];
            if (curr == null) {
                // if the first one is null, then it does not exist, so return null
                return null;
            } else if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            pos++;
        }
        return null;
    }

    @Override
    public Iterator<K> getKeyIterator() {
        return new DictionaryKeyIterator();
    }

    // unsupported

    @Override
    public boolean contains(K key) {
        // check for null value
        if (key == null) {
            throw new IllegalArgumentException("Cannot have null key");
        }
        int hashIndex = getHashIndex(key);
        int pos = 0;
        Entry<K, V> curr = null;

        // do the search starting at the hashIndex
        // ensure that we havent checked every position with pos
        while (pos < dictionary.length) {
            curr = dictionary[(hashIndex + pos) % dictionary.length];
            if (curr == null) {
                return false;
            } else if (curr.getKey().equals(key)) {
                // if the first one is the key, return true
                return true;
            } 
            // linear probing check by doing pos++;
            pos++;
        }
        return false;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Iterator<V> getValueIterator() {
        return new DictionaryValueIterator();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    private class Entry<K, V> {
        private K key;
        private V value;

        private Entry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
        } // end constructor

        private K getKey() {
            return key;
        } // end getKey

        private V getValue() {
            return value;
        } // end getValue

        private void setValue(V dataValue) {
            value = dataValue;
        } // end setValue
    } // end Entry

    class DictionaryKeyIterator implements Iterator<K> {
        int counter = 0;
        int dictIndex = 0;

        @Override
        public boolean hasNext() {
            return (counter < numberOfEntries);
        }

        @Override
        public K next() {
            if (hasNext()) {
                while (dictionary[dictIndex] == null) {
                    dictIndex++;
                }
                counter++;
                return dictionary[dictIndex++].getKey();
                // will return set[counter] and then do counter++.
                // thus it is a one liner.
            } else {
                // if there is no next then throw the no such element exception
                throw new NoSuchElementException("no such element");
            }
        }

    }

    class DictionaryValueIterator implements Iterator<V> {
        int counter = 0;
        int dictIndex = 0;

        @Override
        public boolean hasNext() {
            return (counter < numberOfEntries);
        }

        @Override
        public V next() {
            if (hasNext()) {
                while (dictionary[dictIndex] == null) {
                    dictIndex++;
                }
                counter++;
                return dictionary[dictIndex++].getValue();
                // will return set[counter] and then do counter++.
                // thus it is a one liner.
            } else {
                // if there is no next then throw the no such element exception
                throw new NoSuchElementException("no such element");
            }
        }

    }

} // end HashedDictionary
