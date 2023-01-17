package assignment2;
/**
 * Your name here: Gordon Ng
 * Your McGill ID here: 23113030
 **/

import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /*
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {
		/**** ADD CODE HERE ****/
		if ((numOfCardsPerSuit < 1) || (numOfCardsPerSuit > 13) || (numOfSuits < 1) || (numOfSuits > 4)) {
			throw new IllegalArgumentException("Illegal number of cards or suits");
		}
		else {
			this.head = null;
			this.numOfCards = 0;

			for (int x = 0; x < numOfSuits /* ends if condition met*/; x++/*runs at the end*/) {
				for (int i = 1; i <= numOfCardsPerSuit /* ends if condition met*/; i++/*runs at the end*/) {

					Card card = new PlayingCard(suitsInOrder[x], i);
					addCard(card);

				}
			}

			addCard(new Joker("red"));
			addCard(new Joker("black"));
		}
    }

    /*
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {
		/**** ADD CODE HERE ****/

		if (d.head == null || d.numOfCards == 0) {
			return;
		}
		else {
			Card node = d.head;

			this.head = null;
			this.numOfCards = 0;

			while (node.next != d.head) {
				this.addCard(node.getCopy());
				node = node.next;
			}
			this.addCard(node.getCopy());
		}



    }


    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {
	}

    /*
     * TODO: Adds the specified card at the bottom of the deck. This
     * method runs in $O(1)$.
     */
    public void addCard(Card c) {
		/**** ADD CODE HERE ****/

		if (c == null) {
			return;
		}

		Card card = this.head;
		if (card == null) {
			c.prev = c;
			c.next = c;
			this.head = c;
		}
		else {
			while (card.next != this.head) {
				card = card.next;
			}
			c.prev = card;
			c.next = this.head;
			this.head.prev = c;
			card.next = c;
		}
		this.numOfCards++;
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf.
     * This method runs in O(n) and uses O(n) space, where n is the total
     * number of cards in the deck.
     */
    public void shuffle() {
	/**** ADD CODE HERE ****/

		if (this.numOfCards == 0 || this.head == null) {
			return;
		}
		else {
			int maxIndex = this.numOfCards;
			Card[] tempArray = new Card[this.numOfCards];

			Card card = this.head;
			int counter = 0;
			while (card.next != this.head) {
				tempArray[counter] = card;
				card = card.next;
				counter++;
			}
			tempArray[counter] = card;

			for (int i = maxIndex - 1; i > 0;i--) {

				int j = this.gen.nextInt(i + 1);

				Card tempCard = tempArray[j];

				tempArray[j] = tempArray[i];
				tempArray[i] = tempCard;

			}

			this.head = null;
			this.numOfCards = 0;

			for (int i = 0; i < maxIndex;i++) {
				this.addCard(tempArray[i]);
			}
		}



    }

    /*
     * TODO: Returns a reference to the joker with the specified color in
     * the deck. This method runs in O(n), where n is the total number of
     * cards in the deck.
     */
    public Joker locateJoker(String color) {
		/**** ADD CODE HERE ****/
		if (this.head == null) {
			return null;
		}
		else {
			Card card = this.head;
			while (card.next != this.head) {
				if (card instanceof Joker) {
					Joker tempJoker = (Joker) card;
					if (tempJoker.redOrBlack == color) {
						return tempJoker;
					}
				}
				card = card.next;
			}

			if (card instanceof Joker) {
				Joker tempJoker = (Joker) card;
				if (tempJoker.redOrBlack == color) {
					return tempJoker;
				}
			}
			return null;
		}
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */

    public void moveCard(Card c, int p) {
		/**** ADD CODE HERE ****/
		if (this.head == null || this.numOfCards == 0 || p % this.numOfCards == 0) {
			return;
		}
		else {

			Card card = this.head;

			while (true) {
				if (card.equals(c)) {

					Card tempCard = card;

					Card previousCard = card.prev;
					Card nextCard = card.next;

					previousCard.next = nextCard;
					nextCard.prev = previousCard;

					for (int i = 0; i < p; i++) {
						card = card.next;
					}

					tempCard.prev = card;
					tempCard.next = card.next;
					card.next = tempCard;
					card.next.next.prev = tempCard;

					break;
				}
				card = card.next;
			}

		}
    }


    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You
     * can assume that the input cards belong to the deck and the first one is
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
		/**** ADD CODE HERE ****/

		// Cases where nothing should be done
		if (this.head == null || this.numOfCards == 0 || head.equals(this.head.prev) || this.head.next.equals(this.head.prev) || firstCard.prev.equals(secondCard) || firstCard == null || secondCard == null) {
			return;
		}
		else {
			Card head = this.head;
			Card tail = this.head.prev;

			if (head.equals(firstCard)) {

				this.head = secondCard.next;
				this.head.prev = secondCard;


				firstCard.prev = tail;
				tail.next = firstCard;

			}
			else if (tail.equals(secondCard)) {

				this.head = firstCard;
				this.head.prev = firstCard.prev;


				secondCard.next = head;
				head.prev = secondCard;
			}
			else {

				this.head = secondCard.next;
				this.head.prev = firstCard.prev;
				this.head.prev.next = secondCard.next;


				firstCard.prev = tail;
				tail.next = firstCard;

				secondCard.next = head;
				head.prev = secondCard;


			}
		}


    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the
     * bottom card is equal to a multiple of the number of cards in the deck,
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {
		/**** ADD CODE HERE ****/


		if (this.head == null || this.numOfCards == 0 || head.equals(this.head.prev)) {
			return;
		}
		else {
			Card head = this.head;
			Card tail = this.head.prev;

			int number = tail.getValue() % this.numOfCards;

			// Finding the end of the top of the deck
			Card card = this.head;

			for (int i=0; i < number - 1; i++) {
				card = card.next;
			}


			if (card.next.equals(tail) || number == 0) {
				return;
			}

			// Changing the head
			this.head = card.next;
			this.head.prev = tail;

			head.prev = tail.prev;
			head.prev.next = head;

			tail.prev = card;
			card.next = tail;
			tail.next = this.head;
		}



    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the
     * card on the top of the deck, and counting down that many cards. If the
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
		/**** ADD CODE HERE ****/

		if (this.head == null || this.numOfCards == 0) {
			return null;
		}
		else {
			Card head = this.head;

			int number = head.getValue();
			Card card = this.head;

			for (int i=0; i < number ; i++) {
				card = card.next;
			}

			if (card instanceof Joker) {
				return null;
			}
			else {
				return card;
			}
		}


    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream
     * using this deck. This method runs in O(n).
     */
	public int generateNextKeystreamValue() {
		/**** ADD CODE HERE ****/
		while(true) {


			// How to take into account if the joker is on the bottom etc...
			Card redJoker = this.locateJoker("red");
			if (redJoker != null) {
				moveCard(redJoker, 1);
			}

			Card blackJoker = this.locateJoker("black");
			if (blackJoker != null) {
				moveCard(blackJoker, 2);
			}

			Card firstJoker = null;
			Card secondJoker = null;

			Card card = this.head;

			for (int i = 0; i < this.numOfCards; i++) {

				if (card instanceof Joker) {

					if (firstJoker == null) {
						firstJoker = card;
					}
					else {
						secondJoker = card;
					}
				}
				card = card.next;
			}


			this.tripleCut(firstJoker, secondJoker);

			this.countCut();

			Card luCard = this.lookUpCard();

			if (luCard != null) {
				return luCard.getValue();
			}
		}
	}

	public abstract class Card {
	public Card next;
	public Card prev;

	public abstract Card getCopy();
	public abstract int getValue();

    }

    public class PlayingCard extends Card {
	public String suit;
	public int rank;

	public PlayingCard(String s, int r) {
	    this.suit = s.toLowerCase();
	    this.rank = r;
	}

	public String toString() {
	    String info = "";
	    if (this.rank == 1) {
		//info += "Ace";
		info += "A";
	    } else if (this.rank > 10) {
		String[] cards = {"Jack", "Queen", "King"};
		//info += cards[this.rank - 11];
		info += cards[this.rank - 11].charAt(0);
	    } else {
		info += this.rank;
	    }
	    //info += " of " + this.suit;
	    info = (info + this.suit.charAt(0)).toUpperCase();
	    return info;
	}

	public PlayingCard getCopy() {
	    return new PlayingCard(this.suit, this.rank);
	}

	public int getValue() {
	    int i;
	    for (i = 0; i < suitsInOrder.length; i++) {
		if (this.suit.equals(suitsInOrder[i]))
		    break;
	    }

	    return this.rank + 13*i;
	}

    }

    public class Joker extends Card{
	public String redOrBlack;

	public Joker(String c) {
	    if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
		throw new IllegalArgumentException("Jokers can only be red or black");

	    this.redOrBlack = c.toLowerCase();
	}

	public String toString() {
	    //return this.redOrBlack + " Joker";
	    return (this.redOrBlack.charAt(0) + "J").toUpperCase();
	}

	public Joker getCopy() {
	    return new Joker(this.redOrBlack);
	}

	public int getValue() {
	    return numOfCards - 1;
	}

	public String getColor() {
	    return this.redOrBlack;
	}
    }

}

