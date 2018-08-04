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


## Logic
This will cycle through each pair of strings => O((n<sup>2</sup> + n) / 2) (i.e. nth triangular number)
and calculate the levenshtein distance => O(n<sub>1</sub>n<sub>2</sub>)
However, it'll short circuit on equality just in case there are duplicates.

TODO: for a "good enough" match, sort collection based on string length and batch them.
In a large dataset, it's unlikely the shortest string will be the closest match for longest
string so no point comparing them. Then take best match from each batch. This would miss matches
on the boundaries between batches but would speed up the comparison.