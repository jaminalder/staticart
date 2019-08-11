(ns staticart.sketch-8
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color :as col]
            [staticart.color-matrix :as cm]
            [staticart.draw :as d]
            [staticart.generic-helper :refer :all]
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

(defn get-coloring-function []
  (let [grad1 (col/shuffled-random-grad col/palette_1 40)
        grad2 (col/shuffled-random-grad col/palette_1 40)
        grad3 (col/shuffled-random-grad col/palette_1 40)]
    (fn [n]
      (cond
        (< n -0.15) (pth grad1 n -1 -0.15)
        (< n 0.15) (pth grad2 n -0.15 0.15)
        (>= n 0.15) (pth grad3 n 0.15 1)))))

(defn noise-function [coloring-function octaves scale turb]
  (let [r (rand 1000)]
    (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
      (let [[x' y'] (map #(+ r %) (turb x y))
            n (noise/octave-noise2 x' y' scale octaves)
            newcolor (coloring-function n)]
        (cm/aset2c mt x y newcolor)))))

(defn draw []

  (d/background 0 0 1 1)

  (let [oct (+ 1 (rand-int 4))
        scale (+ 0.0005 (rand 0.001))
        offset (+ 2 (rand-int 2))]

    (cm/on-matrix (noise-function (get-coloring-function) oct scale
                                  (fn [x y] [(* x 1) (* y (rand-int offset))]))))

  (cm/draw-matrix)

  (d/save (str "tmpout/output" (System/currentTimeMillis) ".tiff")))

