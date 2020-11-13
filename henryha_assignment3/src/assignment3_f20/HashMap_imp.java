package assignment3_f20;

public class HashMap_imp implements HashMap { 
  HMCell[] tab;
  int nelts;
  int count = 0;
  String array [];
  HMCell linkedList = null;
  //used for linkedList in put and creating arrays in extend
  //-------------------------------------------------------------

  HashMap_imp (int num) { 
    this.tab = new HMCell[num];
    // for (int i=0; i<num; i++) { tab[i] = null; }
    // we can rely on the Java compiler to fill the table array with nulls
    // another way would be Array.fill()
    this.nelts = 0; 
  }

  //-------------------------------------------------------------
  
  public int hash (String key, int tabSize) {
    int hval = 7;
    for (int i=0; i<key.length(); i++) {
      hval = (hval*31) + key.charAt(i);
    }
    hval = hval % tabSize;
    if (hval<0) { hval += tabSize; }
    return hval;
  }
  
  //-------------------------------------------------------------

  // dont change 
  
@Override
public HMCell[] getTable() {
	return this.tab; 
}

//check helper methods
@Override
public Value put(String k, Value v) {
	//Check first for values in the table already
	if(checkPrev(k,v) != null) {
		return checkPrev(k,v);
	} else {
		return notIncl(k,v);
	}
}

/*
 if key is NOT in the structure
         returns null (this includes when the structure is empty, size 0)
 if key IS in the structure
         returns the Value object from the cell containing the key
 */
@Override
public Value get(String k) {
	int i = 0;
	
	while(i < tab.length) {
		if(tab[i] != null) {
			return tab[i].getValue();
		} else {
			HMCell newCell = tab[i].getNext();
			while(newCell != null) {
				if(newCell.getKey().equals(k)) {
					return newCell.getValue();
				} else {
					newCell = newCell.getNext();
				}
			}
		}
		i++;
	}
	return null;
}

/*
returns: nothing in all cases (return void)
effect: 
  if the key IS in the structure
        take the whole cell out of the structure... the (key,value) pair will not be in the
        structure after
  if the key is NOT in the structure
        make no changes to the state of the structure 
 */
@Override
public void remove(String k) {
	if(valueCheck(k) != tab.length) {
	int index = hash(k, tab.length);
				
	if(index == 0) {
		if(k.equals(tab[0].getKey())) {
			HMCell blank = tab[0];
			tab[0] = blank.getNext();
			blank = null;
			nelts--;
			return;
		} else {
			HMCell blank = tab[0];
			HMCell newCell = tab[0];
			newCell = newCell.getNext();
			while(newCell != null & blank != null) {
				if(newCell.getKey().equals(k)) {
					blank.setNext(newCell.getNext());
					newCell.setKey(null);
					newCell.setNext(null);
					newCell = null;
					nelts--;
					return;
				} else {
					blank = blank.getNext();
					newCell = newCell.getNext();
				}

			}
		}
	} else if(index == tab.length - 1) {
		HMCell blank = tab[index];
		tab[tab.length - 1] = blank.getNext();
		blank = null;
		nelts--;
		return;
	} else if(index > 0){
		HMCell blank = tab[index];
		tab[index] = blank.getNext();
		blank = null;
		nelts--;
		return;
	} else if(index > 0) {
		HMCell blank = tab[index];
		HMCell newCell = tab[index];
		newCell = newCell.getNext();
		while(newCell != null & blank != null) {
			if(newCell.getKey().equals(k)) {
				blank.setNext(newCell.getNext());
				newCell.setKey(null);
				newCell.setNext(null);
				newCell = null;
				nelts--;
				return;
			} else {
				blank = blank.getNext();
				newCell = newCell.getNext();
			}

		}
	} else {
		return;
	}
	}
}

/*
 if key IS in the structure (meaning there is a (key,value) pair for the key), 
        returns true
 if key is NOT in the structure, returns false
 in both cases, no change to the structure
 */
@Override
public boolean hasKey(String k) {
	
	if(valueCheck(k)!= tab.length) {
		return true;
	} else {
		return false;
	}
}

/*
 returns: int, the number of (key,value) pairs stored in the map structure
 */
@Override
public int size() {
	return nelts;
}

/*
  returns: string, the key from the hash table that is largest
  if hash table size is 0, returns null
 */
@Override
public String maxKey() {
	if(size() == 0) {
		return null;
	}
	String[] newArray = getKeys();
	String maximum = newArray[0];
	for(int i=0; i<newArray.length; i++) {
		if(newArray[i].compareTo(maximum) > 0) {
			maximum = newArray[i];
		}
	}
	return maximum;
}

/*
 returns: string, the key from the hash table that is smallest
 if hash table size is 0, returns null
 */
@Override
public String minKey() {
	if(size() == 0) {
		return null;
	}
	String[] newArray = getKeys();
	String minimum = newArray[0];
	for(int i=0; i<newArray.length; i++) {
		if(newArray[i].compareTo(minimum) < 0) {
			minimum = newArray[i];
		}
	}
	return minimum;
}

/*
 returns an array of strings, containing just the keys from the hash table cells
 */
@Override
public String[] getKeys() {
	String[] array = new String[size()];
	int i = 0;
	int count = 0;
	//grabbbing strings
	while(count < size()) {
		if(tab[i] != null) {
			array[count] = tab[i].getKey(); 
			linkedList = tab[i];
			count++;
			if(count == size()) {
				break;
			}
//keys get placed into array from above
			while(linkedList.getNext() != null &&  count < size()) {
				array[count] = linkedList.getNext().getKey();
				linkedList = linkedList.getNext();
				count++;
				if(count == size()) {
					break;
				}

			}
		}
		i++; //counter goes up
	}
	//array of strings is return
	return array;
}

//returns lambda value
@Override
public double lambda() {
	int len = tab.length;
	return (double)size()/(len);
	//lambda = elts stored / slots in table
}

/*
returns: a new lambda value, after the table array has been extended
effect: the array that is the hash table is doubled in size, and the elements 
      in the old table are rehashed (using the new array size) and stored 
      in the new table array
      number of elements stored is unchanged, array is doubled in size 
      (and so lambda gets cut in half)
 */
@Override
public double extend() {
	HMCell newArray[] = createArrays();
	HMCell newArray2[] = new HMCell[tab.length*2];
	for(int i=0; i<newArray.length; i++) {
		int j = hash(newArray[i].getKey(), tab.length*2);
		if(newArray2[j] ==null) {
			newArray2[j] = newArray[i];
		}
	}
	tab = newArray2.clone();
	return lambda();
}

/*
-method to return index of the value if in table.
-returns null if not in table.
-returns -(index) if contained in the linked list
*/
public int valueCheck(String k) {
	int i = hash(k, tab.length);
	
	if(tab[i] == null) {
		return tab.length;
	} else if(tab[i] != null && k.equals(tab[i].getKey())) {
		return i;
	} else if(!(tab[i] != null && k.equals(tab[i].getKey()))) {
		HMCell linkedList = tab[i];
		while(linkedList != null) {
			if(linkedList.getKey().equals(k)) {
				return -(i);
			} else {
				linkedList = linkedList.getNext();
			}
		}
	}
	return tab.length;
}

/*Checking for values already in table/Linked List
if value is in table or in both table and linked list
replaces the Value object in the existing cell
with the new Value object
returns the old Value object 
*/
public Value checkPrev(String k, Value v) {
	int len = tab.length;
	
	if (valueCheck(k) < len) {
		if(valueCheck(k) > 0) {
			Value prev = tab[valueCheck(k)].getValue();
			tab[valueCheck(k)].setValue(v);
			if(lambda() >= 1.0) {
				extend();
			}
			return prev;
		} else if(valueCheck(k) == 0) {
			if(tab[0].getKey().equals(k)) {
				Value prev = tab[valueCheck(k)].getValue();
				tab[valueCheck(k)].setValue(v);
				if(lambda() >= 1.0) {
					extend();
				}
				return prev;
			} else { //Check for linked list
				HMCell linkedList = tab[0];
				while(linkedList.getNext() != null) {
					linkedList = linkedList.getNext();
					if(linkedList.getKey().equals(k)) {
						Value prev = linkedList.getValue();
						linkedList.setValue(v);
						if(lambda() >= 1.0) {
							extend();
						}
						return prev;
					} else {
						linkedList = linkedList.getNext();
					}
				}
			}
		} else{
		HMCell linkedList = tab[valueCheck(k) * -1];
		while(linkedList.getNext() != null) {
			linkedList = linkedList.getNext();
			if(linkedList.getKey().equals(k)) {
				Value prev = linkedList.getValue();
				linkedList.setValue(v);
				if(lambda() >= 1.0) {
					extend();
				}
				return prev;
			} else {
				linkedList = linkedList.getNext();
			}
		}
	}
	}
	return null;
}

/*
 Method for values not already in the table
 if key is NOT in the structure
adds a new cell to the structure and put the key into it, the value into it
and returns null
 */
public Value notIncl(String k, Value v) {
	int len = tab.length;
	if(valueCheck(k) == len) {
		int i = hash(k, len);
		if(i >= len || lambda() >= 1.0) {
			extend();
		}
		if(tab[i] == null) {
			tab[i] = new HMCell_imp(k,v);
			nelts++;
			if(i >= len || lambda() >= 1.0) {
				extend();
			}
		} else if(tab[i] != null) {
			HMCell blank = tab[i];
			while(blank.getNext() != null) {
				blank = blank.getNext();
			}
			nelts++;
			blank.setNext(new HMCell_imp(k,v));
			if(i >= len || lambda() >= 1.0) {
				extend();
			}
		} return null;
	}
	return null;
}

//helper method to help create array of HMCells
//Will be used to complete the extend method
public HMCell[] createArrays() {
	HMCell[] newArray = new HMCell[size()];
	int i = 0;
	int count = 0;
	while(count < size()) {
		if(tab[i] != null) {
			newArray[count] = tab[i]; 
			linkedList = tab[i];
			count++;

			while(linkedList.getNext() != null) {
				newArray[count] = linkedList.getNext();
				linkedList = linkedList.getNext();
				count++;

			}
		}
		i++;
	}
	return newArray;
}

  
}