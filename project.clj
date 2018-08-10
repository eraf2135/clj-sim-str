(defproject clj-sim-str "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [clj-fuzzy "0.4.1"]]
  :main clj-sim-str.main
  :profiles {:uberjar {:omit-source    true
                       :aot            :all
                       :uberjar-name   "clj-sim-str.jar"
                       :source-paths   ["src"]}})
