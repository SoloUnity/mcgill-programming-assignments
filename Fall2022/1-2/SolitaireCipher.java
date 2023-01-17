package assignment2;

/**
* Your name here: Gordon Ng
* Your McGill ID here: 23113030
**/

public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher (Deck key) {
	this.key = new Deck(key); // deep copy of the deck
    }

    /* 
     * TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {
	    /**** ADD CODE HERE ****/

        int[] keystream = new int [size];

        for (int i = 0; i < size; i++) {
            keystream[i] = key.generateNextKeystreamValue();
        }
	    return keystream;
    }

    /* 
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
	    /**** ADD CODE HERE ****/

        if (msg == null) {
            return null;
        }
        else {
            int[] keystream;
            String encodedMsg = "";
            String tempMsg = msg.replaceAll("[^A-Za-z0-9]", "");
            tempMsg = tempMsg.toUpperCase();

            keystream = getKeystream(tempMsg.length());

            for (int i = 0; i < keystream.length; i++) {
                encodedMsg += (char) ((((tempMsg.charAt(i) - 'A') + keystream[i]) % 26) + 65);
            }

            System.out.println(encodedMsg);
            return encodedMsg;
        }

    }

    /* 
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
	    /**** ADD CODE HERE ****/

        if (msg == null) {
            return null;
        }
        else {
            int[] keystream = getKeystream(msg.length());
            String decodedMsg = "";

            for (int i = 0; i < keystream.length; i++) {
                decodedMsg += (char) (((((msg.charAt(i) - 'A') - keystream[i]) + 52 ) % 26) + 65);
            }

            System.out.println(decodedMsg);
            return decodedMsg;
        }

    }

}

