(ns clj-sim-str.core
  (:require [clj-fuzzy.metrics :as fuz]))

(defn- closer?
  "Expects best-match to be a vector with first value being the levenshtein-distance"
  [best-match dist]
  (> (first best-match) dist))

(defn- update-best-match!
  [best-match dist pair]
  (reset! best-match [dist pair]))

(defn- exact-match-found? [best-match]
  (= 0 (first best-match)))

(defn- closest [coll]
  (let [best-match (atom [Integer/MAX_VALUE []])];tuple of [levenshtein-distance, pair-of-compared-strings]

    ;this will cycle through each pair of strings => O((n2 + n) / 2) i.e. nth triangular number
    ;however, short circuit on equality just incase there are duplicates.
    ;todo: for a "good enough" match, sort collection based on string length and batch them. Then take best match from
    ;      each batch. This would mis matches on the boundaries of batches but would significantly speed up the comparison
    (loop [outer coll
           s (first outer)]

      (loop [inner (rest outer)
             comparison-string (first inner)]

        (if (= s comparison-string)
          (update-best-match! best-match 0 [s comparison-string])
          (let [dist (fuz/levenshtein s comparison-string)];O(n1 n2)
            (when (closer? @best-match dist)
              ;for performance, we'll only return the first pair for a given levenshtein distance
              (update-best-match! best-match dist [s comparison-string]))))

        (when (and (not (exact-match-found? @best-match))
                   (> (count inner) 1))
          (let [new-inner (pop inner)]
            (recur new-inner
                   (first new-inner)))))

      (when (and (not (exact-match-found? @best-match))
                 (> (count outer) 2))
        (let [new-outer (pop outer)]
          (recur new-outer
                 (first new-outer)))))

    (second @best-match)))

(comment
  (closest (list "ac" "abc" "bac" "bacc")))