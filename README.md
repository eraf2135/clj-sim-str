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

## Logic
This will cycle through each pair of strings => O((n<sup>2</sup> + n) / 2) (i.e. nth triangular number)
and calculate the levenshtein distance => O(n<sub>1</sub>n<sub>2</sub>).
However, it'll short circuit on equality just in case there are duplicates.

If you don't need the best match and "pretty close" is good enough, you can run it in a "sorted batch" mode. 
This will sort the collection based on string length and divide the collection in batches to be run in parallel.
The theory here is it's unlikely that a very short string will match with a very long string in a large collection
so there's no point comparing them.
However, if the 2 most similar strings are on the boundary between batches then they will not be matched.
Hence, this is only useful if "pretty close" is good enough or on very large data sets where the probability of this
occurring is reduced.
The algorithmic complexity is also reduced from O((n<sup>2</sup> + n) / 2) to O((a<sup>2</sup> + a)b / 2) where *a* is 
batch size and *a* is the number of batches.

## Usage

To run in standard mode where every item is compared:

    lein run

Or to run in "sorted batch" mode, pass the batch size as a parameter

    lein run 100
    
Or you can build an executable jar using:

    lein uberjar
    
Then run with:

    java -jar ./target/clj-sim-str.jar