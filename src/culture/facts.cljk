(ns culture.facts
  "Country-level regional-culture catalog for Samoa (WSM) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"WSM"
   [{:culture/id "wsm.dish.palusami"
     :culture/name "Palusami"
     :culture/country "WSM"
     :culture/kind :dish
     :culture/summary "Samoan dish of taro leaves filled with coconut cream (often with beef and onions), wrapped and steamed -- a quintessential part of Samoan food culture prized for its rich use of coconut cream."
     :culture/url "https://en.wikipedia.org/wiki/L%C5%AB%CA%BBau_(food)"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.dish.oka-ia"
     :culture/name "Oka i'a"
     :culture/country "WSM"
     :culture/kind :dish
     :culture/summary "Samoan raw-fish dish (a regional variant of 'ota 'ika) in which fish is briefly marinated in lemon or lime juice until the flesh surface turns opaque, then mixed with coconut milk."
     :culture/url "https://en.wikipedia.org/wiki/%27Ota_%27ika"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.dish.alaisa-faapopo"
     :culture/name "Alaisa fa'apopo"
     :culture/country "WSM"
     :culture/kind :dish
     :culture/summary "Samoan coconut rice made by cooking white rice in coconut milk; the variant with added cocoa and orange leaves is called koko alaisa and is eaten as a snack or dessert."
     :culture/url "https://en.wikipedia.org/wiki/Coconut_rice"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.beverage.otai"
     :culture/name "'Otai"
     :culture/country "WSM"
     :culture/kind :beverage
     :culture/summary "Samoan fruit-and-coconut drink documented by European colonists in the late 19th century; in Samoa the name 'otai refers specifically to the version made with vi (ambarella) fruit and coconut."
     :culture/url "https://en.wikipedia.org/wiki/%CA%BBotai"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.beverage.ava"
     :culture/name "'Ava"
     :culture/country "WSM"
     :culture/kind :beverage
     :culture/summary "Samoan name for kava, drunk at all important gatherings and ceremonies; prepared by the 'aumaga, served in a polished coconut half called an ipu tau 'ava, with serving order following chiefly rank."
     :culture/url "https://en.wikipedia.org/wiki/Kava_culture"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.craft.pea"
     :culture/name "Pe'a"
     :culture/country "WSM"
     :culture/kind :craft
     :culture/summary "Traditional male tattoo (tatau) of Samoa, extending from waist to knees, applied by master tattooists (tufuga ta tatau) with handmade tools of bone, turtle shell and wood; a mark of manhood and ancestral connection."
     :culture/url "https://en.wikipedia.org/wiki/Pe%CA%BBa"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.craft.siapo"
     :culture/name "Siapo"
     :culture/country "WSM"
     :culture/kind :craft
     :culture/summary "Samoan bark cloth (tapa), traditionally used for clothing, burial shrouds, bed covers and ceremonial garments; made via the siapo 'elei rubbing method or the freehand siapo mamanu method, with Palauli village on Savai'i noted for high-quality production."
     :culture/url "https://en.wikipedia.org/wiki/Tapa_cloth"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.festival.teuila"
     :culture/name "Teuila Festival"
     :culture/country "WSM"
     :culture/kind :festival
     :culture/summary "One of Samoa's largest cultural events; the Miss Samoa pageant has been held as its finale annually since 2000."
     :culture/url "https://en.wikipedia.org/wiki/Miss_Samoa"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "wsm.heritage.vailima"
     :culture/name "Vailima"
     :culture/country "WSM"
     :culture/kind :heritage
     :culture/summary "Village on Upolu south of Apia, site of author Robert Louis Stevenson's final residence Villa Vailima, later used by German and New Zealand administrators before becoming the Robert Louis Stevenson Museum; Stevenson is buried atop nearby Mount Vaea."
     :culture/url "https://en.wikipedia.org/wiki/Vailima,_Samoa"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-wsm culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "WSM"))
                 " WSM entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
