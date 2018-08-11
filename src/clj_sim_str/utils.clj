(ns clj-sim-str.utils)

(defn create-batches [partition-size lines]
  (if partition-size
    (partition-all partition-size lines)
    [lines]))

(defn best-of-batches [batch-results]
  (reduce (fn [best-combo next-combo]
            (if (> (first best-combo) (first next-combo))
              next-combo
              best-combo))
          (first batch-results)
          (rest batch-results)))