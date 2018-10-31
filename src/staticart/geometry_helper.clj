(ns staticart.geometry-helper
  (:require [thi.ng.math.core :as m]))

(defn points-on-line [p1 p2 n] (map #(m/mix p1 p2 %) (range 0 1.00001 (/ 1 (- n 1)))))
