(ns staticart.subdivision
  (:require [staticart.random-helper :as rh]
            [thi.ng.geom.core :as g]
            [thi.ng.math.core :as m]))

(defn divide-triangle [[a b c]]
  (let [[p1 p2] (max-key #(apply g/dist %) [a b] [a c] [b c])
        pm (m/mix p1 p2 (rh/gauss 0.5 0.1))
        p3 (first (clojure.set/difference #{a b c} #{p1 p2}))]
    [[p3 p1 pm] [p3 p2 pm]]))

(defn rec-divide-triangle [tri max-depth depth]
  (if (or (> 0.02 (rand)) (>= depth max-depth))
    [tri]
    (let [tris (divide-triangle tri)]
      (mapcat #(rec-divide-triangle % max-depth (inc depth)) tris))))

(defn rec-divide-tris [tris max-depth]
  (mapcat #(rec-divide-triangle % max-depth 0) tris))
