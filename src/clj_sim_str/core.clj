(ns clj-sim-str.core
  (:require [clj-fuzzy.metrics :as fuz]
            [clojure.math.combinatorics :as combo]
            [clj-sim-str.utils :as u]))

(defn closest
  "This will cycle through each pair of strings => O((n2 + n) / 2) (i.e. nth triangular number)
   and calculate the levenshtein distance => O(nm)
   However, it'll short circuit on equality just in case there are duplicates.

  It'll return a list of [distance, string n, string m]"
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
    best-combo))

(defn closest-by-batches
  [partition-size coll]
  (let [batches (u/create-batches partition-size (sort-by count coll))
        batch-results (pmap closest batches)]
    (u/best-of-batches batch-results)))