# Three Card Poker Judger

This program judges a game of three-card poker. The program aims to find the winning player and return their id.


## How to run and test

### Prerequisite:
- Java 8
- Python 3

### Step 1: Change directory to source

```
cd src
```

### Step 2: Compile the code

```
javac PokerHandEvaluator.java
```

### Step 3: Run the tests
```
python3 run_tests "java PokerHandEvaluator"
```


## Program Design
I used Object Oriented Design and Programming to implement the Three Card Poker Judger.
I degined three classes 

- Card.java
- Hand.java
- PokerHandEvaluator.java

The input gets converted into a list of cards with each card having a rank and a suit associated with it.
A hand object is create from the list of cards which will contain the information such as player's ID, hand type (Straight Flush, Three of A Kind, ...) etc.
All the hands are then stores in a list of hands which will be evaluated.
The ThreeCardPokerHandEvaluator class will evaluate the hand type (Straight Flush, Three of A Kind etc) and run a comparison to find the winner.

### Program Limitations

The current program is limited to a Three Card Poker game. However the program is designed in order to make room for scalability such as for a Five Card Poker Game.

