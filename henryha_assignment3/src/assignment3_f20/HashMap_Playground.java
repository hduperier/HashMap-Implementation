package assignment3_f20;

public class HashMap_Playground {
/*
 * you will test your own HashMap implementation in here
 *
 * we will replace this with our own when grading, and will
 * do what you should do in here... create TreeMap objects,
 * put data into them, take data out, look for values stored
 * in it, checking size, and looking at the HMCells to see if they 
 * are all linked up correctly chains in the hash table
 * 
*/
  
  public static void main(String[] args) {
    // sample manual tests are shown
    // it is up to you to test it thoroughly and make sure
    // the methods behave as requested above in the interface
  
    // do not simple depend on the oracle test we will give
    // use the oracle tests as a way of checking AFTER you have done
    // your own testing

    HashMap hm = new HashMap_imp(40);
    System.out.println(hm.getTable().length); // expect 40
    System.out.println(hm.size()); // expect 0

    Value v1 = new Value_imp(12345, 87.3, 21);
    Value v2 = new Value_imp(23456, 75.54, 25);
    Value v3 = new Value_imp(34567, 99.013, 19);
    Value v4 = new Value_imp(45678, 55.70, 35);
    Value v5 = new Value_imp(56789, 77.91, 32);
    Value v6 = new Value_imp(67890, 83.03, 24);
    
    hm.put("Jenny", v1);  hm.put("Bill",v2); 
    hm.put("Steve",v3);   hm.put("Alan",v4);
    hm.put("Gail",v5);    hm.put("Zed",v6);
    hm.put("Wilma",v6);   hm.put("Lauren",v6);
    hm.put("Xray",v6);

    System.out.println(hm.size()); // expect 9
    System.out.println(hm.hasKey("Bill")); // expect true
    System.out.println(hm.hasKey("Zorro")); // expect false
    hm.extend();
    System.out.println(hm.size()); // expect 9
    System.out.println(hm.hasKey("Bill")); // expect true
    System.out.println(hm.hasKey("Zorro")); // expect false

    // etc...

  }

}