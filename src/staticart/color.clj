(ns staticart.color)

(def palette-ice
  [[178 96 75]
   [178 70 80]
   [179 52 85]
   [184 35 94]
   [174 23 100]])

(def palette-ice2
  [[197 96 55]
   [187 99 56]
   [174 100 66]
   [167 99 76]
   [63 42 85]])

(def palette-soft1
  [[90 2 32]
   [1 62 95]
   [48 60 100]
   [198 78 63]
   [170 42 76]
   ])

(def palette-dark-red
  [[15 97 13]
   [10 92 38]
   [7 92 58]
   [16 96 74]
   [39 89 96]
   ])

(defn pick-color [palette alpha] (conj (rand-nth palette) alpha))

(defn color-stream [palette alpha] (cycle (map #(conj % alpha) palette)))
(defn random-color-stream [palette alpha] (repeatedly #(conj (rand-nth palette) alpha)))
