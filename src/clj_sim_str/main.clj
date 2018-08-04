(ns clj-sim-str.main
  (:gen-class)
  (:require [clj-sim-str.core :as core])
  (:import (java.io BufferedReader)))

(defn -main
  "Reads standard in, expects a list with each line being a single string for comparison.
  Outputs the two most similar strings found, one per line."
  []
  (let [lines (line-seq (java.io.BufferedReader. *in*))
        strings (core/closest lines)]
    (println (first strings))
    (println (second strings))))

