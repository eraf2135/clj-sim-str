(ns clj-sim-str.core
  (:require [clj-fuzzy.metrics :as fuz]
            [clojure.math.combinatorics :as combo]))

(defn closest
  "This will cycle through each pair of strings => O((n2 + n) / 2) (i.e. nth triangular number)
   and calculate the levenshtein distance => O(n1 n2)
   However, it'll short circuit on equality just in case there are duplicates.

   TODO: for a \"good enough\" match, sort collection based on string length and batch them.
   In a large dataset, it's unlikely the shortest string will be the closest match for longest
   string so no point comparing them. Then take best match from each batch. This would miss matches
   on the boundaries between batches but would significantly speed up the comparison"
  [coll]
  (let [pairs (combo/combinations coll 2)
        best-combo (reduce (fn [best-combo [x y]]
                             (if (= x y)
                               (reduced [0 x y])
                               (let [dist (fuz/levenshtein x y)]
                                 (if (> (first best-combo) dist)
                                   [dist x y]
                                   best-combo))))
                           [Integer/MAX_VALUE "" ""]
                           pairs)]
    (rest best-combo)))