(ns staticart.sketch-5
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color :as c]
            [staticart.draw :as d]
            [staticart.matrix :as mt]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]
            [staticart.subdivision :as sub]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

(defn noise-function [color octaves scale turbulence]
  (let [r 3]
   (fn [^"[[I" m x y]
     (let [x' (+ x r (turbulence x y))
           y' (+ y r (turbulence y x))
           n (noise/octave-noise2 x' y' scale octaves)
           newb (+ (nth color 2) (* n 20))
           newh (- (nth color 0) (* n 20))]
       (mt/aset2i m x y ^int (q/color (nth color 0) (nth color 1) newb 1))))))


(defn draw []
  (d/background 0 0 0 0)

  (let [l (/ 500 10)
        starts (for [x (range 0 500 l) y (range 0 500 l)] [x y])
        blocks (map #(for [x (range (nth % 0) (+ (nth % 0) l)) y (range (nth % 1) (+ (nth % 1) l))] [x y]) starts)]
    (doseq [ps blocks]
      (mt/on-points ps (noise-function
                     (c/pick-color c/palette-dark-red 1) 6 0.006
                     #(* 420 (* (Math/sin (* % (/ Math/PI 25))) (Math/sin (* %2 (/ Math/PI 25))))))))
    (mt/draw-matrix)
    #_(doseq [t tris] (d/vertex t true)))

  (d/save "output.jpg"))

