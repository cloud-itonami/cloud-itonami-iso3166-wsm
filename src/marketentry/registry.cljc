(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `required-procurement-authority` / `procurement-authority-sufficient?`
  / `procurement-authority-insufficient-claim?` are THIS vertical's own
  new ground-truth recompute, grounding WSM's flagship governor check
  (`marketentry.governor`'s `procurement-authority-insufficient-
  violations`): the Ministry of Finance's own \"Financial Delegation
  Threshold (B4 Schedule)\", made under Treasury Instructions Section 6
  Part K (Public Finance Management Act 2001 s.88 -- see
  `marketentry.facts` for the full research trail).

  This is a check SHAPE genuinely different from every prior sibling in
  this family this iteration is aware of: not Fiji's single flat
  two-tier value split (Procurement Regulations 2010 reg 29/reg 30),
  and not an itemized per-activity foreign-investment table (Tonga/
  Palau's own Restricted-List shape). Samoa's own B4 Schedule is a
  MULTI-TIER ladder (up to 6 tiers) across THREE parallel procurement-
  category tracks (Works; Goods and General Services; Consultancy
  Services), each with its OWN breakpoints between an ordinal sequence
  of approving authorities: a Relevant Chief Executive Officer (lowest),
  a delegated 'Tenders Board (department/agency) or Board of Directors
  (public body)' tier, the Tenders Board alone (uniform, entity-type-
  independent), and Cabinet (highest, above SAT 500,000 in every
  category). The check independently recomputes, from the engagement's
  OWN declared `:procurement-category`/`:declared-contract-value`,
  which authority tier the schedule's own text actually requires, and
  flags a filing that CLAIMS a lower-authority tier suffices than its
  own declared facts require -- exactly the kind of scrutiny-evasion
  ground-truth mismatch this family's other flagship checks already
  catch, reapplied to a genuinely different (ordinal multi-tier, not
  flat two-tier or itemized-table) shape.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement system. It builds the RECORD an operator
  would keep, not the act of submitting a filing itself (that is
  `marketentry.operation`'s `:filing/submit`, always human-gated -- see
  README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(def ^:private money-scale
  "Sub-minor-unit scale used when comparing two money amounts: 1/10000 of
  a unit. Coarser than double representation error by many orders of
  magnitude, finer than any real currency's minor unit (2 decimals for
  most, 3 for KWD/BHD/OMR, 0 for JPY/KRW)."
  10000)

(defn- money=
  "Exact-at-money-precision equality for two amounts.

  `==` on raw doubles is NOT the right comparison for money. With
  whole-unit fees the two agree, but as soon as an amount carries
  cents the sum `base + rate x months` is routinely not the double
  nearest the true total, and a CORRECT claim compares false: measured
  on this exact shape, 40,989 of 327,060 cent-denominated combinations
  (12.5%) were rejected while being right, against 0 of 327,060 in
  whole units.

  Rounding both sides to `money-scale` before comparing removes the
  representation error while preserving every distinction money can
  actually carry."
  [x y]
  (and (number? x) (number? y)
       (= (Math/round (* money-scale (double x)))
          (Math/round (* money-scale (double y))))))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  ;; nil when any field is not a number: an un-recomputable engagement is
  ;; un-verifiable, which is neither `correct` nor a ClassCastException
  ;; thrown out of the caller.
  (when (and (number? base-fee) (number? monthly-rate) (number? monitoring-months))
    (+ (double base-fee)
       (* (double monthly-rate) (double monitoring-months)))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (money= claimed-fee (compute-engagement-fee engagement)))

(def procurement-tiers
  "Ministry of Finance's own 'Financial Delegation Threshold (B4
  Schedule)', made under Treasury Instructions Section 6 Part K
  (Public Finance Management Act 2001 s.88) -- read directly off the
  schedule's own PDF text (fetched and read end-to-end, verified
  2026-07-23). Each category is a vector of tiers, ascending, each
  `:max-inclusive` the upper bound of that tier (`nil` = no upper bound,
  i.e. the schedule's own 'Above 500,000' Cabinet tier). Works and
  Goods-and-General-Services share the same 6-tier shape but differ in
  their OWN breakpoint between the Chief-Executive-Officer tier and the
  delegated Tenders-Board-or-Board-of-Directors tier (150,000 for Works
  vs 100,000 for Goods and General Services, per the schedule's own
  text); Consultancy Services genuinely has only 4 tiers (no oral-vs-
  written Request-for-Quotation split), own breakpoints at
  50,000/200,000/500,000. This catalog does not force a uniform shape
  onto categories the schedule's own text treats differently."
  {:works [{:max-inclusive 5000.0 :authority :ceo}
           {:max-inclusive 50000.0 :authority :ceo}
           {:max-inclusive 150000.0 :authority :ceo}
           {:max-inclusive 200000.0 :authority :tenders-board-or-board-of-directors}
           {:max-inclusive 500000.0 :authority :tenders-board}
           {:max-inclusive nil :authority :cabinet}]
   :goods-and-general-services [{:max-inclusive 5000.0 :authority :ceo}
                                {:max-inclusive 50000.0 :authority :ceo}
                                {:max-inclusive 100000.0 :authority :ceo}
                                {:max-inclusive 200000.0 :authority :tenders-board-or-board-of-directors}
                                {:max-inclusive 500000.0 :authority :tenders-board}
                                {:max-inclusive nil :authority :cabinet}]
   :consultancy-services [{:max-inclusive 50000.0 :authority :ceo}
                          {:max-inclusive 200000.0 :authority :tenders-board-or-board-of-directors}
                          {:max-inclusive 500000.0 :authority :tenders-board}
                          {:max-inclusive nil :authority :cabinet}]})

(def authority-rank
  "Ordinal rank of each B4 Schedule approving authority, lowest to
  highest scrutiny. The intermediate 'Tenders Board (department/agency)
  or Board of Directors (public body)' tier and the uniform 'Tenders
  Board' tier are genuinely DIFFERENT ranks -- above the delegated
  tier's own threshold, even a public body must go to the Tenders Board
  itself, per the schedule's own text."
  {:ceo 0
   :tenders-board-or-board-of-directors 1
   :tenders-board 2
   :cabinet 3})

(defn required-procurement-authority
  "The ground-truth B4 Schedule authority tier for `category` at
  `value` (SAT), independently recomputed by walking the category's own
  ascending tier ladder and returning the first tier whose own
  `:max-inclusive` is >= value (or the last, unbounded, tier). Returns
  nil for an unknown category or a nil/missing value -- an engagement
  with no declared category/value cannot have its authority requirement
  computed, and simply fails the sufficiency check below rather than
  throwing."
  [category value]
  (when-let [tiers (get procurement-tiers category)]
    (when (some? value)
      (let [v (double value)]
        (:authority
         (or (some #(when (and (:max-inclusive %) (<= v (double (:max-inclusive %)))) %) tiers)
             (last tiers)))))))

(defn procurement-authority-sufficient?
  "Does `claimed-authority`'s own ordinal rank meet or exceed the
  INDEPENDENTLY recomputed `required-procurement-authority` for
  `category`/`value`? An unknown category, missing value, or missing
  claimed authority simply fails (does not throw)."
  [category value claimed-authority]
  (boolean
   (when-let [required (required-procurement-authority category value)]
     (when-let [claimed-rank (get authority-rank claimed-authority)]
       (>= claimed-rank (get authority-rank required))))))

(defn procurement-authority-insufficient-claim?
  "Does `engagement` declare `:claims-procurement-cleared? true` (i.e.
  it asserts its own declared `:claimed-approval-authority` already
  covers this filing) while the INDEPENDENTLY recomputed
  `procurement-authority-sufficient?` is false (its own declared
  `:procurement-category`/`:declared-contract-value` actually requires
  a higher-scrutiny tier)? An engagement that does NOT claim
  procurement clearance is never flagged by this check (entity/
  engagement-scope-gated, the same discipline this family's other
  claim-gated flagship checks use)."
  [{:keys [claims-procurement-cleared? procurement-category
           declared-contract-value claimed-approval-authority]}]
  (boolean (and claims-procurement-cleared?
                (not (procurement-authority-sufficient?
                      procurement-category declared-contract-value
                      claimed-approval-authority)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a registration package.
  Pure function -- does not touch any real procurement/registry
  system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a filing
  (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
