
(ns staticart.sketch-1
  (:require [staticart.color :as col]
            [staticart.distortion :as dist]
            [staticart.draw :as d]
            [staticart.geometry-helper :refer :all]
            [staticart.random-helper :as rh]
            [staticart.screen-locations :refer :all]
            [staticart.subdivision :as sub]))

(defn draw []
  (d/background 360)
  (d/no-stroke)
  (let [tris  [[(pos 0 0) (pos 1 0) (pos 1 1)]
               [(pos 0 0) (pos 0 1) (pos 1 1)]]
        subtris (sub/rec-divide-tris tris 5)
        subsubtris (for [t subtris]
                     (let [divided (sub/rec-divide-tris [t] 5)]
                       (for [n (range (* 2 (count divided)))] (rand-nth divided))))]
    (doseq [randtris subsubtris]
      (apply d/fill (col/pick-color col/palette-soft1 0.2))
      (doseq [t randtris]
       (d/vertex t true))))
  (d/save "output.jpg"))

