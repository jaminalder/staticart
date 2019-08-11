(ns staticart.color
  (:require [thi.ng.color.core :as c]
            [thi.ng.color.gradients :as grad]))

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

(def palette_1 [(c/as-rgba (c/css "rgba(51,92,103,1)"))
                (c/as-rgba (c/css "rgba(255,243,176,1)"))
                (c/as-rgba (c/css "rgba(224,159,62,1)"))
                (c/as-rgba (c/css "rgba(158,42,43,1)"))
                (c/as-rgba (c/css "rgba(84,11,14,1)"))])

(def palette_2 [(c/as-rgba (c/css "rgba(91,192,235,1)"))
                (c/as-rgba (c/css "rgba(253,231,76,1)"))
                (c/as-rgba (c/css "rgba(155,197,61,1)"))
                (c/as-rgba (c/css "rgba(229,89,52,1)"))
                (c/as-rgba (c/css "rgba(250,121,33,1)"))])

(def palette_3 [(c/as-rgba (c/css "rgba(60,21,24,1)"))
                (c/as-rgba (c/css "rgba(105,20,14,1)"))
                (c/as-rgba (c/css "rgba(164,66,0,1)"))
                (c/as-rgba (c/css "rgba(213,137,54,1)"))
                (c/as-rgba (c/css "rgba(242,243,174,1)"))])

(def palette_4 [(c/as-rgba (c/css "rgba(27,153,139,1)"))
                (c/as-rgba (c/css "rgba(45,48,71,1)"))
                (c/as-rgba (c/css "rgba(255,253,130,1)"))
                (c/as-rgba (c/css "rgba(255,155,113,1)"))
                (c/as-rgba (c/css "rgba(232,72,85,1)"))])

(def palette_5 [(c/as-rgba (c/css "rgba(51,92,103,1)"))
                (c/as-rgba (c/css "rgba(255,243,176,1)"))
                (c/as-rgba (c/css "rgba(224,159,62,1)"))
                (c/as-rgba (c/css "rgba(158,42,43,1)"))
                (c/as-rgba (c/css "rgba(84,11,14,1)"))])

(def palette_6 [(c/as-rgba (c/css "rgba(237,106,90,1)"))
                (c/as-rgba (c/css "rgba(244,241,187,1)"))
                (c/as-rgba (c/css "rgba(155,193,188,1)"))
                (c/as-rgba (c/css "rgba(92,164,169,1)"))
                (c/as-rgba (c/css "rgba(230,235,224,1)"))])

(defn pick2 [palette]
  (let [c1 (rand-nth palette)
        c2 (rand-nth palette)]
    (if (not= c1 c2) [c1 c2] (recur palette))))

(defn random-grad [palette steps]
  (let [[c1 c2] (pick2 palette)]
   (grad/cosine-gradient
    steps
    (grad/cosine-coefficients c1 c2))))

(defn shuffled-random-grad [palette steps] (shuffle (random-grad palette steps)))



