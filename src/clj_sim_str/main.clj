(ns clj-sim-str.main
  (:gen-class)
  (:require [clj-sim-str.core :as core])
  (:import (java.io BufferedReader)))

(defn -main
  "Reads standard in, expects a list with each line being a single string for comparison.
  Outputs the two most similar strings found, one per line."
  [& args]
  (let [lines (line-seq (BufferedReader. *in*))
        partition-size-str (first args)
        [_ n m] (if partition-size-str
                  (core/closest-by-batches (Integer/parseInt partition-size-str) lines)
                  (core/closest lines))]
    (println n)
    (println m)
    (shutdown-agents))) ;need to call this because of the agents created by pmap