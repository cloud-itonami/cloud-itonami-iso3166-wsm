# cloud-itonami-iso3166-wsm

**WSM**: Independent State of Samoa.

- Government Tenders Board (Public Finance Management Act 2001 s.88)
  public-procurement compliance -- Ministry of Finance Treasury
  Instructions Section 6 Part K + B4 Schedule multi-tier authority
  ladder (Works / Goods and General Services / Consultancy Services)
- Ministry of Commerce, Industry and Labour (MCIL) business/company
  registration (Companies Act 2001) + Foreign Investment Certificate
  (Foreign Investment Act 2000, as amended by the Foreign Investment
  Amendment Act 2011) + Ministry of Customs and Revenue Business
  Licence/TIN/VAGST registration

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as every other `cloud-itonami-iso3166-*` sibling:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Government
  Tenders Board (Public Finance Management Act 2001 s.88, Ministry of
  Finance Treasury Instructions Section 6 Part K + its own B4 Schedule);
  MCIL's Companies Registry (Companies Act 2001) and Foreign Investment
  Certificate regime (Foreign Investment Act 2000 as amended by the
  Foreign Investment Amendment Act 2011, ss.3(1)/4(1) Reserved/
  Restricted Activities Lists); and the Ministry of Customs and
  Revenue's Business Licence (Business License Act 1998 s.5), TIN and
  VAGST (Value Added Goods and Services Tax Act 1993) registration.
  `governor.cljc`'s flagship check independently recomputes the B4
  Schedule's own multi-tier, multi-category procurement-authority
  ladder -- genuinely NOT a flat two-tier split like Fiji's own
  Procurement Regulations 2010 reg 29/reg 30, nor an itemized per-
  activity table like Tonga's own Foreign Investment Restricted List;
  see the namespace docstrings for the full research trail and
  honestly-narrowed scope, including facts this iteration could NOT
  verify (e.g. a local-representative/agent provision in the Companies
  Act 2001's own text -- `parliament.gov.ws` was DNS-unreachable this
  session).
- `src/statute/facts.cljc` -- general-law catalog: the Companies Act
  2001 (company law) and the Labour and Employment Relations Act 2013
  (labour law, confirmed via PacLII's own Consolidated Acts of Samoa
  2014 database).

Every citation is curl/WebFetch-verified against an official source
(`mcil.gov.ws`, `businessregistries.gov.ws`, `mof.gov.ws`). Where the
LIVE official site could not be reached this session
(`revenue.gov.ws` -- a plain 403 Forbidden hosting-level block, NOT a
bot-challenge; `paclii.org` -- a genuine Cloudflare bot-detection
challenge, NOT bypassed; `parliament.gov.ws` -- DNS resolution
failure), the SAME official page/document was instead read from a
`web.archive.org` snapshot -- see `marketentry.facts`/`statute.facts`'s
docstrings for exactly which facts are live-verified vs. archived-
snapshot-verified vs. an honestly-flagged gap.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Samoa:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
