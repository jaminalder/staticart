(ns staticart.sketch-1
  (:require [staticart.distortion :as dist]
            [staticart.draw :as d]
            [staticart.geometry-helper :refer :all]
            [staticart.random-helper :as rh]
            [staticart.screen-locations :refer :all]
            [staticart.subdivision :as sub]
            [thi.ng.geom.bezier :as bez :refer [bezier2]]
            [thi.ng.geom.circle :as c]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.polygon :as poly]
            [thi.ng.geom.rect :as r]
            [thi.ng.geom.triangle :as tri :refer [triangle2]]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]))

(defn draw []
  (d/background 360)
  (d/no-stroke)
  (d/fill (rand-int 360) 50 100 0.2)
  (let [line  [(point-on-screen 0.1 0.1)
               (point-on-screen 0.9 0.9)]
        variation (dist/distort-line (first line) (last line) 1 50)
        curves (for [n (range 10)] (dist/distort-line (first line) (last line) 1 50))]
    (doseq [c curves] (d/curve-through-points c))
    #_(doseq [p variation] (d/debug-point p)))
  (d/save "output.jpg")
  )

