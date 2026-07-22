(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "WSM" 0)
        s (registry/register-submit "eng-1" "WSM" 0)]
    (is (= "WSM-DFT-000000" (get d "draft_number")))
    (is (= "WSM-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "WSM" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest required-procurement-authority-walks-the-ladder
  (testing "Works: own breakpoint between CEO and the delegated tier is 150,000"
    (is (= :ceo (registry/required-procurement-authority :works 150000.0)))
    (is (= :tenders-board-or-board-of-directors (registry/required-procurement-authority :works 150000.01)))
    (is (= :tenders-board-or-board-of-directors (registry/required-procurement-authority :works 200000.0)))
    (is (= :tenders-board (registry/required-procurement-authority :works 500000.0)))
    (is (= :cabinet (registry/required-procurement-authority :works 500000.01))))
  (testing "Goods and General Services: own breakpoint is 100,000, NOT 150,000 -- genuinely different from Works"
    (is (= :ceo (registry/required-procurement-authority :goods-and-general-services 100000.0)))
    (is (= :tenders-board-or-board-of-directors (registry/required-procurement-authority :goods-and-general-services 100000.01))))
  (testing "Consultancy Services: only 4 tiers, no CEO oral/written split, own breakpoints at 50,000/200,000/500,000"
    (is (= :ceo (registry/required-procurement-authority :consultancy-services 50000.0)))
    (is (= :tenders-board-or-board-of-directors (registry/required-procurement-authority :consultancy-services 200000.0)))
    (is (= :tenders-board (registry/required-procurement-authority :consultancy-services 500000.0)))
    (is (= :cabinet (registry/required-procurement-authority :consultancy-services 500000.01))))
  (testing "unknown category or missing value -> nil, not a throw"
    (is (nil? (registry/required-procurement-authority :unknown-category 1000.0)))
    (is (nil? (registry/required-procurement-authority :works nil)))))

(deftest procurement-authority-sufficient-is-ordinal-not-just-tenders-board-string-match
  (testing "the intermediate 'Tenders Board (dept/agency) or Board of Directors (public body)' tier and the uniform 'Tenders Board' tier are DIFFERENT ranks"
    (is (false? (registry/procurement-authority-sufficient? :works 300000.0 :tenders-board-or-board-of-directors))
        "300,000 in Works requires the uniform :tenders-board tier (rank 2), not the delegated rank-1 tier")
    (is (true? (registry/procurement-authority-sufficient? :works 300000.0 :tenders-board)))
    (is (true? (registry/procurement-authority-sufficient? :works 300000.0 :cabinet))
        "a HIGHER-ranked claimed authority than required is always sufficient"))
  (testing "missing claimed authority or unknown category simply fails"
    (is (false? (registry/procurement-authority-sufficient? :works 1000.0 nil)))
    (is (false? (registry/procurement-authority-sufficient? :unknown 1000.0 :cabinet)))))

(deftest procurement-authority-insufficient-claim-is-entity-scope-gated
  (testing "an engagement NOT claiming procurement clearance is never flagged, even if its authority would fail"
    (is (false? (registry/procurement-authority-insufficient-claim?
                 {:claims-procurement-cleared? false
                  :procurement-category :works
                  :declared-contract-value 999999.0
                  :claimed-approval-authority :ceo}))))
  (testing "an engagement claiming clearance whose declared value exceeds its own claimed authority tier -> insufficient claim"
    (is (true? (registry/procurement-authority-insufficient-claim?
                {:claims-procurement-cleared? true
                 :procurement-category :works
                 :declared-contract-value 300000.0
                 :claimed-approval-authority :tenders-board-or-board-of-directors}))))
  (testing "an engagement claiming clearance whose declared value DOES satisfy its own claimed authority tier -> not flagged"
    (is (false? (registry/procurement-authority-insufficient-claim?
                 {:claims-procurement-cleared? true
                  :procurement-category :works
                  :declared-contract-value 300000.0
                  :claimed-approval-authority :tenders-board})))
    (is (false? (registry/procurement-authority-insufficient-claim?
                 {:claims-procurement-cleared? true
                  :procurement-category :goods-and-general-services
                  :declared-contract-value 40000.0
                  :claimed-approval-authority :ceo})))))
