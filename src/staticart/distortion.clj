(ns staticart.distortion
  (:require [staticart.geometry-helper :refer :all]
            [staticart.random-helper :as rh]))

(defn distort-line [p1 p2 points variation]
  (let [inner-points (rest (butlast (points-on-line p1 p2 (+ 2 points))))
        dist-points (rh/gauss-variate-points inner-points variation)
        with-head-tail (concat [p1] dist-points [p2])]
    (println inner-points)
    (println dist-points)
    with-head-tail))
