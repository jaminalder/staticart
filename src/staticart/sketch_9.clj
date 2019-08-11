(ns staticart.sketch-9
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color :as col]
            [staticart.color-matrix :as cm]
            [staticart.draw :as d]
            [staticart.generic-helper :refer :all]
            [staticart.noise :as noise]
            [staticart.random-helper :as rh]
            [staticart.screen-locations :refer :all]
            [staticart.settings :refer [settings]]
            [staticart.subdivision :as sub]
            [thi.ng.color.core :as c]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

(defn function-points [f ps]
  (map #(vec2 % (f %)) ps))

(defn random-points [n]
  (repeatedly n #(vec2 (+ 0.4 (* 0.5 (rand))) (* 2 (Math/PI) (rand)))))

(defn points-on-circle [n scale]
  (map #(vec2 (+ scale (* (rand) 0.05)) %) (range 0.0 m/TWO_PI (/ m/TWO_PI n))))

(defn to-cartesian [ps]
  (map g/as-cartesian ps))

(defn to-mid-screen [ps]
  (map #(m/+ screen-mid-point (scale-to-mid-screen %)) ps))

(defn scale-and-move [s m ps]
  (map #(m/+ m (m/* s %)) ps))

(defn curve-function [x]
  #_(Math/sin (* m/TWO_PI x (* (Math/sin (* x x 0.01)) m/TWO_PI)))(Math/sin (* x 8))
  #_(Math/sin (* m/TWO_PI x (* (Math/sin (* x x 0.01)) m/TWO_PI)))
  (Math/sin (* m/TWO_PI (* (Math/sin (* (* (Math/sin x) 1.4) x x 0.2)) m/TWO_PI))))

(defn draw []

  (d/background 1 1 1 1)

  (d/no-fill)
  ;;(apply d/fill (col/pick-color col/palette-soft1 0.2))

  (d/stroke-weight (h 0.01))

  (doseq [y (range -50 (+ 50 (h)) (h 0.005))]
    (apply d/stroke (deref (rand-nth col/palette_1)))
    (d/curve-through-points (scale-and-move (vec2 (h 0.2) (h 0.02)) (vec2 -50 y)
                                           (function-points curve-function (range 0.0 m/TWO_PI 0.05)))))
  ;; (d/curve-through-points (to-mid-screen (to-cartesian (points-on-circle 25 0.5))) true)
  ;; (doseq [p (to-mid-screen (to-cartesian (random-points 100)))] (d/debug-point) p)
  (d/save (str "tmpout/sketch_9_" (System/currentTimeMillis) ".tiff"))

  )

