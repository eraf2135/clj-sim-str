(ns clj-sim-str.core-test
  (:require [clojure.test :refer :all]
            [clj-sim-str.core :refer :all]
            [clj-sim-str.utils :as u]))

(deftest closest-test
  (testing "basic happy case"
    (is (= [1 "foo bar" "foo bars"]
           (closest ["cat" "foo bar" "dog" "foo bars"])))))

(defn rand-str [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

;
; Performance analysis
; no asserts here...just to get an idea of running time
;
(deftest performance-10
  (testing "10, 1000 char strings => Elapsed time: 16189.48845 msecs"
    (time (closest (map rand-str (take 10 (repeat 1000)))))))

(deftest performance-20
  (testing "20, 1000 char strings => Elapsed time: 66705.600372 msecs"
    (time (closest (map rand-str (take 20 (repeat 1000)))))))

(deftest performance-20-in-batches-of-10
  (testing "2 batches of 10, 1000 char strings run in sequence => Elapsed time: 31869.059321 msecs"
    (time (let [batches (u/create-batches 10 (map rand-str (take 20 (repeat 1000))))
                batch-results (map closest batches)]
            (u/best-of-batches batch-results)))))

(deftest performance-20-in-batches-of-10-parallel
  (testing "2 batches of 10, 1000 char strings run in parallel => Elapsed time: 16933.694204 msecs"
    (time (closest-by-batches 10 (map rand-str (take 20 (repeat 1000)))))))
