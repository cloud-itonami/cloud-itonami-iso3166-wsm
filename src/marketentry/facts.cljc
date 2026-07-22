(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  The Independent State of Samoa's real market-entry surface (curl/
  WebFetch-verified 2026-07-22/23; where a live official page could not
  be reached this session, that is stated explicitly and the
  corresponding fact is sourced from a `web.archive.org` snapshot of the
  SAME official page/document instead -- never invented):

  - **Sourcing discipline for this iteration**: this session's WebSearch
    budget was already exhausted before this task began (the same
    constraint several sibling catalogs in this family record), so
    discovery used direct navigation from `mcil.gov.ws` (Ministry of
    Commerce, Industry and Labour, reachable and HTTP 200 on every fetch
    this session) and its own linked online registry
    `businessregistries.gov.ws` (live, HTTP 200 -- the SAME Foster Moore
    registry-platform vendor this family's Tonga catalog independently
    identified for `businessregistries.gov.to`), the Ministry of
    Finance's own `mof.gov.ws` (live, HTTP 200, Cloudflare-fronted but
    serving real content -- NOT a bot-detection challenge), and PacLII's
    own \"Consolidated Acts of Samoa 2014\" database (reached only via
    `web.archive.org`, because live `paclii.org` returned the SAME
    Cloudflare bot-detection challenge (`cf-mitigated: challenge`,
    `challenges.cloudflare.com` script) this family's Tonga catalog
    already recorded -- not bypassed). The Ministry of Customs and
    Revenue's own `revenue.gov.ws` returned a PLAIN `403 Forbidden` on
    every fetch this session (a WPEngine/nginx hosting-level error page,
    genuinely distinct from a Cloudflare/CAPTCHA challenge -- no
    `challenges.cloudflare.com` script, no `cf-mitigated` header; the
    body is a plain templated \"403 - Forbidden\" page) -- disclosed as
    unreachable and read instead via its own `web.archive.org` snapshot.
  - **Business/company registration**: the **Ministry of Commerce,
    Industry and Labour (MCIL)** (own page `<title>` and footer,
    `mcil.gov.ws`, fetched directly this session, live, HTTP 200: \"Home |
    Ministry of Commerce, Industry and Labour\"; \"Ministry of Commerce,
    Industry and Labour, Government of Samoa. All rights reserved.\")
    operates the online Companies Registry at `businessregistries.gov.ws`
    (fetched directly this session, live, HTTP 200 -- Foster Moore
    platform, confirmed by the `wp-json`/registry-app link headers).
    Its own \"Overseas Companies\" page states, quoted directly:
    \"Overseas Companies carrying on business registered under the
    Companies Act. Those companies must file an annual return each year,
    and update the Samoa Companies Register for various changes of their
    details.\" This iteration independently confirmed the exact Act
    YEAR (this specific MCIL page names only \"the Companies Act\", no
    year) from PacLII's own \"Consolidated Acts of Samoa 2014\" index
    (`http://www.paclii.org/ws/legis/consol_act_2014/toc-C.html`, read
    via a `web.archive.org` snapshot dated 20241217150821, live
    `paclii.org` unreachable this session -- see sourcing-discipline
    note above): own list entry, quoted directly, \"Companies Act 2001\".
    MCIL's own `/companies/legislation/` page (fetched directly this
    session, live, HTTP 200) separately names an amending instrument,
    quoted directly: \"The Companies Amendment Act 2006 can be found in
    English here and in Samoan here\", linking
    `parliament.gov.ws/images/Companies_Amendment_Act_2006_-_Eng.pdf` --
    this iteration did NOT independently fetch that PDF (`parliament.
    gov.ws` failed DNS resolution this session, `curl` exit code 6,
    genuinely unreachable, disclosed rather than retried against an
    archive since no working PacLII/Wayback mirror of that specific PDF
    was located this iteration). This iteration did NOT independently
    fetch the Companies Act 2001's own full primary statutory text,
    only PacLII's own index entry (title + year) and MCIL's own summary
    page -- an honest limit on how deep this iteration went, the SAME
    limit Fiji's/Tonga's sibling catalogs disclose for their own
    Companies Acts.
  - **Foreign investment**: the **Foreign Investment Act 2000**, as
    amended by the **Foreign Investment Amendment Act 2011** (\"FIAA
    2011\", the abbreviation MCIL's own page uses) -- base-Act year
    independently confirmed from the SAME PacLII archived index
    (`consol_act_2014/toc-F.html`, own entry, quoted directly:
    \"Foreign Investment Act 2000\"). The substantive Reserved/
    Restricted-Activities regime was fetched DIRECTLY and LIVE this
    session from MCIL's own registry site,
    `businessregistries.gov.ws/companies/how-to-register-a-company/
    foreign-investment-reservedrestricted-activities/` (HTTP 200), own
    text quoted VERBATIM: \"In accordance with the Foreign Investment
    Amendment Act 2011 the following activities are regarded as
    Reserved and Restricted activities:\"
    - **Reserved Activities (Section 3(1))**, own text: \"activities are
      reserved for Samoan citizens only. Foreign investors and companies
      with foreign shareholders, directors, and/or employees are not
      allowed to engage in any of these reserved activities\": (i) Bus
      transport services for the general public; (ii) Taxi transport
      services for the general public; (iii) Rental vehicles;
      (iv) Retailing of general food items; (v) Saw milling; and
      (vi) Traditional elei garment designing and printing.
    - **Restricted Activities (Section 4(1))**, own text: \"Foreign
      investors could participate in restricted activities if the
      conditions are satisfied as per the Restricted List\": (i)
      Fishing -- \"a foreign investor must have a maximum 40% equity\"
      (a CEILING on foreign ownership -- this iteration specifically
      notes this is the OPPOSITE direction from this family's Tonga
      catalog, whose own Restricted List sets MINIMUM Tongan-equity-
      percentage FLOORS; Samoa's own Restricted List genuinely is a
      ceiling for this item, not a floor, and this catalog does not
      force it into the floor shape just for family consistency); (ii)
      Manufacturing of Nonu (Morinda citrifolia) and Coconut Virgin Oil
      -- \"a foreign investor must establish a joint venture with a
      local partner\" (a boolean joint-venture requirement, no numeric
      percentage stated in MCIL's own text); and (iii) Services
      (Architectural, Professional engineering, General construction,
      Sewage, Refuse disposal, Sanitation and similar services) -- \"a
      foreign investor must be incorporated in Samoa and must establish
      a joint venture with a local partner\". MCIL's own page further
      states: \"every foreign investor is required to satisfy all
      requirements of the FIAA 2011, and to obtain a Foreign Investment
      Certificate (MCIL), before a Business License could be issued from
      the Ministry for Revenue\" (MCIL's OWN page calls the tax
      authority \"the Ministry for Revenue\"; that Ministry's own site,
      see below, self-identifies as the \"Ministry of Customs and
      Revenue\" -- this iteration discloses BOTH names as found on two
      different official sources rather than silently picking one).
      This iteration did NOT independently fetch the Foreign Investment
      Act 2000/FIAA 2011's own primary statutory text, only MCIL's own
      summary/citation of ss.3(1)/4(1) -- an honest limit, not a claim
      the full Act was read.
  - **Business licensing + TIN + VAGST**: the **Ministry of Customs and
    Revenue**'s own Inland Revenue Services page
    (`revenue.gov.ws/our-services/inland-revenue-services/`, LIVE fetch
    returned a plain `403 Forbidden` this session -- disclosed above as
    a hosting-level block, not a bot-challenge -- read instead via a
    `web.archive.org` snapshot dated 20260319154050), own text quoted
    directly: \"Before setting up a business in Samoa, section 5 of the
    Business License Act 1998 requires you to obtain a business license
    by applying to the Inland Revenue Services (of the Ministry of
    Customs and Revenue). This also applies to any foreigner (company or
    individual or others) who is contracted by a government
    ministry/agency or any local company in Samoa, to provide services
    or goods or both in Samoa.\" -- directly relevant to THIS actor's own
    public-sector market-entry vertical, since it names foreign
    government suppliers specifically. The SAME page, own text: \"Each
    new business license operator is automatically issued with a unique
    Tax-Identification-Number (TIN) which you will use for all your tax
    matters\" -- i.e. Samoa's TIN issuance is NOT a separate registration
    step (unlike Fiji's own TPOS or Tonga's own Form 1/2/3 TIN
    processes); it is bundled into Business License issuance itself, a
    genuinely different mechanism shape this catalog does not paper
    over. VAGST (**Value Added Goods and Services Tax Act 1993**,
    year independently confirmed via the SAME PacLII archived index,
    `consol_act_2014/toc-V.html`, own entry quoted directly: \"Value
    Added Goods and Services Tax Act 1993\") -- the SAME archived
    Ministry of Customs and Revenue page states, own text: \"VAGST
    (value added goods and services tax) is a tax that is added on most
    goods and services supplied in Samoa by a registered business ...
    VAGST of 15% is added to the price of goods and services,\" and
    sets an explicit registration threshold, own text: \"[if you earn]
    more than SAT$130,000 from your licensed business, or you expect
    your turnover to be more than SAT$130,000 in the next twelve
    months, you must register for VAGST,\" with an explicit foreigner
    carve-out, own text: \"For foreigners (company or individual etc)
    thinking about carrying out business in Samoa under a contract or a
    limited time, you will only be required to register for VAGST if
    you are already carrying out a continuous and regular business
    activity in Samoa; and if you have or expect your turnover [to meet
    the threshold].\"
  - **Public procurement** -- like this family's Tonga catalog, this
    iteration found NO free-standing \"Public Procurement Act\" or
    gazetted \"Procurement Regulations\" for Samoa. The mechanism is
    instead **Treasury Instructions**, an administrative instrument
    issued by the Ministry of Finance under the **Public Finance
    Management Act 2001** (base-Act year independently confirmed via
    the SAME PacLII archived index, `consol_act_2014/toc-P.html`, own
    entry quoted directly: \"Public Finance Management Act 2001\"; MOF's
    own `/legislation` page, fetched directly this session, live, HTTP
    200, independently lists it among its own \"Principal Acts\" and
    does NOT list any separate procurement Act/Regulations). The
    specific instrument, **\"Treasury Instructions Section 6:
    Procurement & Contracting\" (Part K, amended April 2020)**, was
    fetched DIRECTLY and LIVE this session as a PDF hosted on the
    Ministry of Finance's own asset host
    (`cdn.prod.website-files.com/.../Treasury-Instruction-Part_K-2020_
    Final.pdf`, linked directly from `mof.gov.ws/publications/
    landing-publication/procurement-manual-policies`, itself fetched
    directly, live, HTTP 200) and READ end-to-end (18 pages). Its own
    Definitions clause K.11 states, quoted directly: \"(a) 'Act' means
    the Public Finance Management Act 2001; ... (d) 'Board' means the
    Government Tenders Board established under section 88 of the Act.\"
    Instruction K.4 \"Procurement Thresholds and Authority\" states, own
    text: \"The thresholds and authority for each type of procurement
    are as specified in the Operating Manual\" -- that Operating
    Manual's own threshold table is a SEPARATE document, the
    **\"Financial Delegation Threshold (B4 Schedule)\"**, also fetched
    DIRECTLY and LIVE this session as a PDF from the SAME Ministry of
    Finance asset host and READ end-to-end. This vertical's FLAGSHIP
    check (see `marketentry.governor`/`marketentry.registry`) is
    grounded here, and this iteration specifically confirms the shape
    is genuinely NOT a flat two-tier split like this family's Fiji
    catalog (Procurement Regulations 2010 reg 29/reg 30) -- Samoa's own
    B4 Schedule is a MULTI-TIER ladder across THREE parallel
    procurement-category tracks (own headings: \"Works\"; \"Goods and
    General Services\"; \"Consultancy Services\"), each independently
    laddered (own currency label \"SAT\", i.e. Samoan Tālā)
    from \"Up to including 5,000\" (Relevant Chief Executive Officer) up
    through \"Above 500,000\" (Cabinet), with an intermediate tier
    whose OWN text names TWO alternate approving bodies depending on the
    procuring entity's own type -- \"Tenders Board (in case of a
    department or government agency) or Board of Directors of a public
    body (in the case of a public body)\" -- and, above that, a tier
    where the SAME Tenders Board alone approves regardless of entity
    type. Works and Goods-and-General-Services share the SAME 6-tier
    shape but differ in their OWN breakpoint between the
    Chief-Executive-Officer tier and the delegated Tenders-Board-or-
    Board-of-Directors tier (150,000 for Works vs 100,000 for Goods and
    General Services, per the schedule's own text); Consultancy
    Services has only 4 tiers (no oral-vs-written Request-for-Quotation
    split), own breakpoints at 50,000 / 200,000 / 500,000. This
    catalog does NOT collapse this into a single flat threshold just to
    match the Fiji-family precedent -- Samoa's own schedule genuinely
    is a multi-tier, multi-category ladder, and `marketentry.registry`
    models exactly that. Treasury Instructions K.9E also confirms,
    own text: \"For any contract, there are 2 types of taxes that may
    be applicable, and which the contractor should be aware of from the
    outset: Value Added Goods and Services Tax (VAGST) and withholding
    tax\" -- cross-confirming VAGST's relevance to a government-supply
    engagement independently of the Ministry of Customs and Revenue's
    own page above.
  - This iteration also specifically looked for a Samoa-specific
    representative/local-agent exclusion-extension provision (the shape
    Fiji's own catalog honestly leaves unconfirmed, and CAF's own
    now-superseded 2008 Code documents). MCIL's own \"Overseas
    Companies\" page names a distinct \"Overseas Companies\" registration
    category on `businessregistries.gov.ws`, which strongly suggests the
    Companies Act 2001 has its own foreign-company registration regime
    -- but this iteration did NOT independently fetch/read the Companies
    Act 2001's own primary text (only MCIL's own summary page, and
    `parliament.gov.ws` -- the likely host of the Act's own text -- was
    DNS-unreachable this session), so it cannot confirm a specific
    section number or the exact shape of any local-representative/agent
    requirement. `rep-spec-basis` below is left honestly nil for WSM,
    the same discipline Fiji's own catalog uses when a real mechanism
    plausibly exists but this iteration cannot confirm its current,
    citable shape.
  - `procurement-threshold-spec-basis` grounds this vertical's FLAGSHIP
    check (see `marketentry.governor`/`marketentry.registry`) -- the
    Ministry of Finance's own B4 Schedule multi-tier, multi-category
    procurement-authority ladder, read directly off the schedule's own
    PDF text (fetched and read end-to-end this session, no delegated/
    unread number).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit. WSM
  deliberately carries NO `:rep-owner-authority` -- see the namespace
  docstring's honest-scope-narrowing note (MCIL's own site names a
  distinct \"Overseas Companies\" registration category, but this
  iteration could not confirm the Companies Act 2001's own
  representative/local-agent provision at a specific section number --
  `parliament.gov.ws`, the likely host of the Act's own primary text,
  was DNS-unreachable this session). `:procurement-threshold-owner-
  authority` / `:procurement-threshold-legal-basis` /
  `:procurement-threshold-criteria` / `:procurement-threshold-
  provenance` ground this vertical's flagship governor check
  (`procurement-authority-insufficient-claim?` in
  `marketentry.registry`) -- a genuinely MULTI-TIER, multi-category
  ladder, not a flat two-tier split."
  {"WSM" {:name "Independent State of Samoa"
          :owner-authority "Ministry of Commerce, Industry and Labour (MCIL) operates the Companies Registry (businessregistries.gov.ws) and issues Foreign Investment Certificates under the Foreign Investment Act 2000 (as amended by the Foreign Investment Amendment Act 2011); the Ministry of Customs and Revenue's Inland Revenue Services issues Business Licences (Business License Act 1998 s.5), Tax Identification Numbers (TIN, bundled with Business Licence issuance) and VAGST registrations; the Government Tenders Board, established by s.88 of the Public Finance Management Act 2001, approves public procurement per the Ministry of Finance's own Treasury Instructions Section 6 Part K and its B4 Schedule (Financial Delegation Threshold)"
          :legal-basis "Companies Act 2001 (company registration, MCIL/businessregistries.gov.ws; amended by the Companies Amendment Act 2006); Foreign Investment Act 2000 as amended by the Foreign Investment Amendment Act 2011 (FIAA 2011), ss.3(1)/4(1) Reserved/Restricted Activities Lists (foreign investment, MCIL); Business License Act 1998 s.5 (business licensing -- own text applies to any foreigner contracted by a government ministry/agency to supply goods or services in Samoa; Ministry of Customs and Revenue); Value Added Goods and Services Tax Act 1993 (VAGST, 15%, SAT$130,000 registration threshold; Ministry of Customs and Revenue); Public Finance Management Act 2001 s.88 (Government Tenders Board) plus Treasury Instructions Section 6 'Procurement & Contracting' Part K (amended April 2020) and its own B4 Schedule 'Financial Delegation Threshold' (Ministry of Finance) -- own text fetched and read end-to-end this session, no free-standing Public Procurement Act/Regulations found"
          :national-spec "Government Tenders Board (PFMA 2001 s.88) approves public procurement at or above its own Tenders-Board tier per the B4 Schedule; Cabinet approves above SAT 500,000 in every category; a delegated Chief-Executive-Officer or (for the intermediate tier) Board-of-Directors/Tenders-Board tier applies below that, per category-specific breakpoints (Works/Goods-and-General-Services/Consultancy-Services each has its own ladder -- see marketentry.registry). No dedicated e-procurement transactional portal was confirmed reachable this session; mof.gov.ws itself publishes 'Tender Advertisements' and 'Tender Awards' directly"
          :provenance "https://www.businessregistries.gov.ws/companies/how-to-register-a-company/foreign-investment-reservedrestricted-activities/ ; https://web.archive.org/web/20241217150821/http://www.paclii.org/ws/legis/consol_act_2014/toc-C.html ; https://web.archive.org/web/20241217150820/http://www.paclii.org/ws/legis/consol_act_2014/toc-F.html ; https://web.archive.org/web/20260319154050/https://revenue.gov.ws/our-services/inland-revenue-services/ ; https://cdn.prod.website-files.com/67a155f272e2c5aeb2caf86f/67ff0850111834fa548bdabf_Treasury-Instruction-Part_K-2020_Final.pdf ; https://cdn.prod.website-files.com/67a155f272e2c5aeb2caf86f/67ff0a42ee662e65bf78c481_Financial-Delegation-Threshold-Revised.pdf"
          :required-evidence ["MCIL Companies Registry business/company registration record (Companies Act 2001; businessregistries.gov.ws, independently confirmed live this session)"
                              "MCIL Foreign Investment Certificate record, when the engagement is a foreign investor (Foreign Investment Act 2000 as amended by the Foreign Investment Amendment Act 2011, ss.3(1)/4(1))"
                              "Ministry of Customs and Revenue Business Licence record (Business License Act 1998 s.5 -- applies to any foreigner contracted by a government ministry/agency to supply goods or services in Samoa)"
                              "Ministry of Customs and Revenue Tax Identification Number (TIN) record (automatically issued upon Business Licence issuance)"
                              "Government Tenders Board procurement-category/authority-tier declaration confirmation record (Public Finance Management Act 2001 s.88; Treasury Instructions Part K, B4 Schedule)"
                              "Authorized-representative confirmation record"]
          :corporate-number-owner-authority "Ministry of Customs and Revenue (Inland Revenue Services)"
          :corporate-number-legal-basis "Ministry of Customs and Revenue's own Inland Revenue Services page (revenue.gov.ws, read via a web.archive.org snapshot this session -- the live site returned a plain 403 Forbidden, disclosed as a hosting-level block, not a bot-challenge): \"Each new business license operator is automatically issued with a unique Tax-Identification-Number (TIN) which you will use for all your tax matters\" -- TIN issuance is bundled into Business Licence issuance (Business License Act 1998 s.5), not a separate registration step"
          :corporate-number-provenance "https://web.archive.org/web/20260319154050/https://revenue.gov.ws/our-services/inland-revenue-services/"
          :procurement-threshold-owner-authority "Government Tenders Board (department/government agency procurements) or the relevant public body's own Board of Directors (intermediate tier only), or Cabinet (highest tier), per the Ministry of Finance's own B4 Schedule and Treasury Instructions Part K, made under s.88 of the Public Finance Management Act 2001"
          :procurement-threshold-legal-basis "Public Finance Management Act 2001 s.88 (Government Tenders Board) plus the Ministry of Finance's own Treasury Instructions Section 6 'Procurement & Contracting' Part K (amended April 2020) K.4 ('the thresholds and authority for each type of procurement are as specified in the Operating Manual') and its own 'Financial Delegation Threshold (B4 Schedule)' -- a MULTI-TIER ladder across three procurement-category tracks (Works; Goods and General Services; Consultancy Services), read directly off the schedule's own PDF text fetched and read end-to-end this session (not a delegated, unread number)"
          :procurement-threshold-criteria {:currency "SAT"
                                            :categories
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
                                                                    {:max-inclusive nil :authority :cabinet}]}}
          :procurement-threshold-provenance "https://cdn.prod.website-files.com/67a155f272e2c5aeb2caf86f/67ff0850111834fa548bdabf_Treasury-Instruction-Part_K-2020_Final.pdf ; https://cdn.prod.website-files.com/67a155f272e2c5aeb2caf86f/67ff0a42ee662e65bf78c481_Financial-Delegation-Threshold-Revised.pdf"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-wsm R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For WSM this is deliberately nil --
  see the `catalog` docstring's honest-scope-narrowing note (MCIL's own
  site names a distinct \"Overseas Companies\" registration category,
  but this iteration could not confirm the Companies Act 2001's own
  representative/local-agent provision at a specific section number --
  `parliament.gov.ws` was DNS-unreachable this session)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn procurement-threshold-spec-basis
  "The jurisdiction's procurement-method value-threshold/authority regime,
  or nil. For WSM this is real and current -- the flagship check this
  vertical adds is grounded here (Public Finance Management Act 2001
  s.88; Treasury Instructions Part K; B4 Schedule multi-tier ladder)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:procurement-threshold-owner-authority sb)
      (select-keys sb [:procurement-threshold-owner-authority
                       :procurement-threshold-legal-basis
                       :procurement-threshold-criteria
                       :procurement-threshold-provenance]))))
