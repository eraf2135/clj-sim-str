(ns clj-sim-str.core
  (:require [clj-fuzzy.metrics :as fuz]))

(defn- closer?
  "Expects best-match to be a vector with first value being the levenshtein-distance"
  [best-match dist]
  (> (first best-match) dist))

(defn- update-best-match!
  "Updates atom tracking the current best-match found"
  [best-match dist pair]
  (reset! best-match [dist pair]))

(defn- exact-match-found?
  [best-match]
  (zero? (first best-match)))

(defn closest
  "This will cycle through each pair of strings => O((n2 + n) / 2) (i.e. nth triangular number)
   and calculate the levenshtein distance => O(n1 n2)
   However, it'll short circuit on equality just in case there are duplicates.

   TODO: for a \"good enough\" match, sort collection based on string length and batch them.
   In a large dataset, it's unlikely the shortest string will be the closest match for longest
   string so no point comparing them. Then take best match from each batch. This would miss matches
   on the boundaries between batches but would significantly speed up the comparison"
  [coll]
  (let [best-match (atom [Integer/MAX_VALUE []])] ;tuple of [levenshtein-distance, pair-of-compared-strings]
    (loop [outer-coll coll
           s (first outer-coll)]
      (loop [inner-coll (rest outer-coll)
             comparison-string (first inner-coll)]

        (if (= s comparison-string)
          (update-best-match! best-match 0 [s comparison-string])
          (let [dist (fuz/levenshtein s comparison-string)]
            (when (closer? @best-match dist) ;for performance, we'll only return the first pair for a given levenshtein distance
              (update-best-match! best-match dist [s comparison-string]))))

        (when (and (not (exact-match-found? @best-match))
                   (> (count inner-coll) 1))
          (let [new-inner-coll (rest inner-coll)]
            (recur new-inner-coll
                   (first new-inner-coll)))))

      (when (and (not (exact-match-found? @best-match))
                 (> (count outer-coll) 2))
        (let [new-outer (rest outer-coll)]
          (recur new-outer
                 (first new-outer)))))

    (second @best-match)))

(comment
  (closest (list "abc" "ac" "bac" "bacc")))