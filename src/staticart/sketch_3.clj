(ns staticart.sketch-3
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color :as c]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.subdivision :as sub]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

(defn draw-matrix [matrix]
  (doseq [x (range (count (aget matrix 0))) y (range (count matrix))]
    (q/set-pixel x y (aget matrix x y))))



(defn spiral
  [center start end r1 r2 steps]
  (map
   (fn [r theta] (m/+ (g/as-cartesian (vec2 r theta)) center))
   (range r1 r2 (mm/subdiv r2 r1 steps))
   (range start end (mm/subdiv end start steps))))

(defn points-in-triangle [triangle]
  (let [tri (tri/triangle2 triangle)
        b (g/bounds tri)
        x-start (:x (:p b))
        y-start (:y (:p b))
        x-end (+ x-start (:x (:size b)))
        y-end (+ y-start (:y (:size b)))
        xs (range x-start x-end)
        ys (range y-start y-end)
        ps (for [x xs y ys] [x y])]
    (filter #(g/contains-point? tri %) ps)))

(defn draw []
  (d/background 360)
  (d/stroke 0)
  (d/no-fill)
  (let [full [(vec2 0 0) (vec2 500 0) (vec2 500 500) (vec2 0 500)]
        tris (sub/rec-divide-tris (gu/tessellate-with-point full) 3)
        trips (map  points-in-triangle tris)]
    (time (doseq [ps trips]
            (noise/noise-image ps (c/pick-color c/palette-dark-red 1)  6 0.01)))
    (doseq [t tris] (d/vertex t true))
    #_(time (doseq [p ps] (q/set-pixel (first p) (last p) (q/color (rand-int 360))))))
  (d/save "output.jpg"))

