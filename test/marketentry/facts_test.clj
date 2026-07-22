(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest wsm-has-spec-basis
  (let [sb (facts/spec-basis "WSM")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "WSM")))
    (is (some? (facts/procurement-threshold-spec-basis "WSM")))))

(deftest wsm-rep-spec-basis-is-honestly-absent
  (testing "MCIL's own site names a distinct Overseas Companies registration category, but the Companies Act 2001's own local-representative provision could not be confirmed at a specific section number -- deliberately not claimed"
    (is (nil? (facts/rep-spec-basis "WSM")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "WSM")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "WSM" all)))
    (is (not (facts/required-evidence-satisfied? "WSM" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["WSM" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest procurement-threshold-spec-basis-criteria
  (let [pt (facts/procurement-threshold-spec-basis "WSM")]
    (is (= "SAT" (get-in pt [:procurement-threshold-criteria :currency])))
    (is (= :cabinet (:authority (last (get-in pt [:procurement-threshold-criteria :categories :works])))))
    (is (= 4 (count (get-in pt [:procurement-threshold-criteria :categories :consultancy-services]))))))
