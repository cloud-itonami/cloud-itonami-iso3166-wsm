(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest wsm-has-spec-basis
  (let [sb (facts/spec-basis "WSM")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["WSM" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["wsm.companies-act-2001"]
         (mapv :statute/id (facts/by-topic "WSM" :corporate-governance))))
  (is (= ["wsm.labour-and-employment-relations-act-2013"]
         (mapv :statute/id (facts/by-topic "WSM" :labor))))
  (is (empty? (facts/by-topic "WSM" :data-protection))
      "no data-protection statute independently confirmed this iteration -- honestly absent, see namespace docstring")
  (is (empty? (facts/by-topic "ATL" :labor))))
