//-------------------------------------------------------------------
// put this Interface and Implementation into your code as is
// the grader may need these methods to examine the structure
// that your code creates
//-------------------------------------------------------------------
// you may add methods and structure as you need to
// for example, you may want a toString to help with your debugging
// but to not remove what we have given here
//-------------------------------------------------------------------

package assignment3_f20;

public interface HMCell { // these will be used by the grader to 
                          // examine your data structure contents
  public void setKey(String newKey);
  public String getKey();
  public void setValue(Value newValue);
  public Value getValue();
  public void setNext(HMCell nextCell);
  public HMCell getNext();
}
