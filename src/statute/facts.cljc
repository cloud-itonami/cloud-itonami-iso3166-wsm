(ns statute.facts
  "General-law compliance catalog for the Independent State of Samoa
  (WSM) -- extends this repo's existing `marketentry.facts` (public-
  procurement market-entry only, narrow scope) with a second, orthogonal
  catalog of statutes a company operating in this jurisdiction must
  generally track for compliance. Mirrors cloud-itonami-iso3166-jpn/
  -deu/-bgr/-aze/-alb/-arm/-atg/-ben/-btn/-bwa/-caf/-est/-fji/-png/-plw/
  -ton's `statute.facts` (ADR-2607141700, cloud-itonami-compliance-
  fact-federation).

  Every entry cites an OFFICIAL government-hosted URL (or, where the
  live official page/domain could not be reached this session, a
  `web.archive.org` snapshot of that SAME official document, disclosed
  as such) that this iteration actually fetched and read this session --
  never fabricated.

  - Companies/commercial-entity law: the **Companies Act 2001**,
    year confirmed directly from PacLII's own \"Consolidated Acts of
    Samoa 2014\" database (`http://www.paclii.org/ws/legis/
    consol_act_2014/toc-C.html`, own list entry quoted directly:
    \"Companies Act 2001\"). Live `paclii.org` returned a Cloudflare
    bot-detection challenge this session (`cf-mitigated: challenge`
    response header, `challenges.cloudflare.com` script present in the
    Content-Security-Policy) -- NOT bypassed; this entry is instead read
    from a `web.archive.org` snapshot of the SAME PacLII page, dated
    20241217150821. Cross-confirmed (title only, no year) by the
    Ministry of Commerce, Industry and Labour's own online registry,
    `businessregistries.gov.ws/companies/how-to-register-a-company/
    overseas-companies/` (fetched directly this session, live, HTTP
    200): \"Overseas Companies carrying on business registered under
    the Companies Act.\" MCIL's own `/companies/legislation/` page
    (fetched directly this session, live, HTTP 200) separately names an
    amending instrument by title, quoted directly: \"The Companies
    Amendment Act 2006 can be found in English here and in Samoan
    here\" (linking `parliament.gov.ws`, which failed DNS resolution
    this session -- `curl` exit code 6 -- and so was not independently
    fetched). This iteration did NOT independently fetch the Companies
    Act 2001's own full primary statutory text, only PacLII's own index
    entry (title + year) and MCIL's own summary/citation -- an honest
    limit on how deep this iteration went, not a claim the Act's
    substantive provisions were read.
  - Employment law: the **Labour and Employment Relations Act 2013**,
    confirmed directly from the SAME PacLII \"Consolidated Acts of
    Samoa 2014\" database (`http://www.paclii.org/ws/legis/
    consol_act_2014/toc-L.html`, read via a `web.archive.org` snapshot
    dated 20241217150824, live `paclii.org` unreachable this session for
    the same Cloudflare-challenge reason as above), own list entry
    quoted directly: \"Labour and Employment Relations Act 2013\". The
    Ministry of Commerce, Industry and Labour's own site (`mcil.gov.ws`,
    fetched directly this session, live, HTTP 200) independently
    corroborates this Ministry's OWN labour/industrial-relations remit
    -- its own \"Work & Labour\" section lists \"Minimum wage rates\",
    \"Industrial relations\" and \"Foreign Employment Permits (FEEP)\" as
    services it operates, though that specific page does not itself
    name the Act by year (an honest limit -- the year citation here
    rests on PacLII's own index, not MCIL's own page). This iteration
    did NOT independently fetch the Labour and Employment Relations Act
    2013's own full primary statutory text, only PacLII's own index
    entry -- an honest limit, not a claim the Act's substantive
    provisions were read end-to-end.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit. WSM's catalog has 2 entries
  -- company law and employment law, both independently confirmed this
  iteration from PacLII's own official (Wayback-archived, live paclii.org
  Cloudflare-blocked this session) consolidated-Acts database, cross-
  referenced against MCIL's own official site where possible."
  {"WSM"
   [{:statute/id "wsm.companies-act-2001"
     :statute/title "Companies Act 2001"
     :statute/jurisdiction "WSM"
     :statute/kind :law
     :statute/law-number "Companies Act 2001, per PacLII's own \"Consolidated Acts of Samoa 2014\" index (http://www.paclii.org/ws/legis/consol_act_2014/toc-C.html, read via a web.archive.org snapshot this session -- live paclii.org returned a Cloudflare bot-detection challenge, not bypassed), own entry: \"Companies Act 2001\"; amended by the Companies Amendment Act 2006 per the Ministry of Commerce, Industry and Labour's own /companies/legislation/ page (mcil.gov.ws, fetched directly this session, live). This iteration did not independently fetch the Act's own primary statutory text, only PacLII's own index entry and MCIL's own citation of its title/year."
     :statute/url "https://web.archive.org/web/20241217150821/http://www.paclii.org/ws/legis/consol_act_2014/toc-C.html"
     :statute/url-provenance :archived-paclii-org
     :statute/enacted-date "2001-01-01"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "wsm.labour-and-employment-relations-act-2013"
     :statute/title "Labour and Employment Relations Act 2013"
     :statute/jurisdiction "WSM"
     :statute/kind :law
     :statute/law-number "Labour and Employment Relations Act 2013, per PacLII's own \"Consolidated Acts of Samoa 2014\" index (http://www.paclii.org/ws/legis/consol_act_2014/toc-L.html, read via a web.archive.org snapshot this session -- live paclii.org returned a Cloudflare bot-detection challenge, not bypassed), own entry: \"Labour and Employment Relations Act 2013\". The Ministry of Commerce, Industry and Labour's own site (mcil.gov.ws, fetched directly this session, live) independently corroborates this Ministry's own labour/industrial-relations remit (Minimum wage rates, Industrial relations, Foreign Employment Permits) without itself naming the Act's year."
     :statute/url "https://web.archive.org/web/20241217150824/http://www.paclii.org/ws/legis/consol_act_2014/toc-L.html"
     :statute/url-provenance :archived-paclii-org
     :statute/enacted-date "2013-01-01"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-wsm statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "WSM")) " WSM statute(s) seeded with an "
                 "official citation. Extend `statute.facts/catalog`, never "
                 "fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
