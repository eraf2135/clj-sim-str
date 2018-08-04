# clj-sim-str


Finds the 2 "most similar" strings given a list of strings.

Reads standard in, expecting a list with each line being a single string for comparison. e.g.

      Foo bar
      Foos bars
      Dogs
      Cats

Outputs the two most similar strings found, one per line.

    Foo bar
    Foos bars

## Usage

    lein run
    
Or you can build an executable jar using:

    lein uberjar
    
Then run with:

    java -jar ./target/clj-sim-str.jar
