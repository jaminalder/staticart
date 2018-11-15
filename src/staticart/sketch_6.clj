(ns staticart.sketch-6
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
      (let [[x' y'] (turbulence x y)
            x' (+ x x')
            y' (+ y y')
            n (noise/octave-noise2 x' y' scale octaves)
            newb (+ (nth color 2) (* n 1))
            newh (+ 200 (- (nth color 0) (* n 50)))]
        (mt/aset2i m x y ^int (q/color newh (nth color 1) newb 1))))))

(defn attractor [x y a1 a2 a3 a4 a5 a6]
  (+ a1 (* a2 x) (* a3 x x) (* a4 x y) (* a5 y) (* a6 y y)))

(defn cosattractor [x y a b c d]
  [(+ (Math/cos (* y b)) (* c (Math/sin (* x b))))
   (+ (Math/cos (* x a)) (* d (Math/sin (* y a))))])

(defn draw []
  (d/background 0 0 0 0)

  (mt/on-matrix (noise-function
                 (conj (nth c/palette-dark-red 3) 1) 6 0.01
                 #(map (partial * 260) (cosattractor % %2 0.008 0.07 2 1))))

  #_(* 80 (noise/octave-noise2 % %2 0.01 6))
  #_(* 80 (* (Math/sin (* % (/ Math/PI 100)))
                           (Math/sin (* %2 (/ Math/PI 200)))))
  
  (mt/draw-matrix)

  (d/save "output.jpg"))

