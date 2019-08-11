(ns staticart.generic-helper
  (:require [thi.ng.math.core :as m]))

(defn pth [coll fact low high]
  (nth coll (int (m/map-interval-clamped fact low high 0 (- (count coll) 1)))))
