(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Samoan procurement law, whether a claimed engagement fee
  actually equals base + months x rate, whether the engagement's own
  declared procurement category and contract value actually clear the
  Ministry of Finance's own B4 Schedule authority tier it claims,
  whether a Ministry of Customs and Revenue Tax Identification Number
  (TIN) record has been verified for a filing that requires it, or when
  a draft stops being a draft and becomes a real-world MCIL/Government-
  Tenders-Board submission, so this MUST be a separate system able to
  *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Procurement authority
       insufficient                -- for `:filing/submit`, when the
                                       engagement declares
                                       `:claims-procurement-cleared?
                                       true` (i.e. it asserts its own
                                       declared `:claimed-approval-
                                       authority` already covers this
                                       filing), INDEPENDENTLY recompute
                                       whether the engagement's own
                                       declared `:procurement-category`/
                                       `:declared-contract-value`
                                       actually only requires that
                                       authority tier or a LOWER one,
                                       per the Ministry of Finance's own
                                       B4 Schedule (Treasury Instructions
                                       Part K, Public Finance Management
                                       Act 2001 s.88), and HARD-hold if
                                       not. FLAGSHIP check for this
                                       vertical -- a MULTI-TIER,
                                       multi-category ORDINAL-AUTHORITY
                                       recompute grounded in the
                                       schedule's own text (not a
                                       delegated, unread number),
                                       stopping a filing from evading
                                       Government-Tenders-Board/Cabinet
                                       scrutiny by misdeclaring its own
                                       procurement-authority-tier
                                       eligibility.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. TIN record unverified       -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-tin-record? true`,
                                       INDEPENDENTLY check
                                       `:tin-record-verified?`.
                                       CONDITIONAL on the engagement's
                                       own ground truth. Grounded in
                                       the Ministry of Customs and
                                       Revenue's own Inland Revenue
                                       Services page (see
                                       `marketentry.facts`): TIN
                                       issuance is bundled into Business
                                       Licence issuance (Business
                                       License Act 1998 s.5).
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(MCIL企業登記・Foreign Investment Certificate・Business Licence・TIN登録・調達権限区分確認・代理人確認等)が充足していない状態での提案"}]))))

(defn- procurement-authority-insufficient-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own declared procurement category and contract value
  actually only require the claimed authority tier or a lower one --
  the flagship check this vertical adds. HARD-hold when the engagement
  declares `:claims-procurement-cleared? true` but its own claimed
  authority is insufficient."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/procurement-authority-insufficient-claim? e)
        [{:rule :procurement-authority-insufficient
          :detail (str subject " はB4 Schedule上の調達権限区分(" (:claimed-approval-authority e)
                      ")で充足すると主張しているが、"
                      "独立再計算(申告調達区分 vs 申告契約額)による必要権限区分を満たさない"
                      "(Public Finance Management Act 2001 s.88・Treasury Instructions Part K B4 Schedule)")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- tin-record-unverified-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-tin-record? true`, INDEPENDENTLY check
  `:tin-record-verified?` -- CONDITIONAL on the engagement's own ground
  truth. Grounded in the Ministry of Customs and Revenue's own TIN
  issuance process (bundled into Business Licence issuance)."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-tin-record? e))
                 (not (true? (:tin-record-verified? e))))
        [{:rule :tin-record-unverified
          :detail (str subject " はMinistry of Customs and Revenue TIN登録記録の確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (procurement-authority-insufficient-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (tin-record-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
