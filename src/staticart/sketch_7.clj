(ns staticart.sketch-7
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color-matrix :as cm]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]
            [staticart.subdivision :as sub]
            [thi.ng.color.core :as c]
            [thi.ng.color.gradients :as grad]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

(defn pth [coll fact low high]
  (nth coll (int (m/map-interval-clamped fact low high 0 (- (count coll) 1)))))

(defn get-coloring-function []
  (let [blue1 (c/as-rgba (c/hsva 0.6 0.5 1))
        blue2 (c/as-rgba (c/hsva 0.7 0.5 0.4))
        blue-grad (shuffle (grad/cosine-gradient 20 (grad/cosine-coefficients blue1 blue2)))
        red1 (c/as-rgba (c/hsva 0.0 0.5 0.4))
        red2 (c/as-rgba (c/hsva 0.1 0.8 0.8))
        red-grad (shuffle (grad/cosine-gradient 20 (grad/cosine-coefficients red1 red2)))
        grey1 (c/as-rgba (c/hsva 0.0 0.0 0.4))
        grey2 (c/as-rgba (c/hsva 0.0 0.0 0.8))
        grey-grad (shuffle (grad/cosine-gradient 20 (grad/cosine-coefficients grey1 grey2)))
        green1 (c/as-rgba (c/hsva 0.3 0.5 0.4))
        green2 (c/as-rgba (c/hsva 0.4 0.8 0.8))
        green-grad (shuffle (grad/cosine-gradient 20 (grad/cosine-coefficients green1 green2)))]
    (fn [n]
      (cond
        (< n -0.6) (pth grey-grad n -1 -0.6)
        (< n -0.4) (pth green-grad n -0.6 -0.4)
        (< n -0.3) (pth red-grad n -0.4 -0.3)
        (< n -0.0) (pth blue-grad n -0.3 -0.0)
        (< n 0.1) (pth red-grad n 0.0 0.1)
        (< n 0.6) (pth green-grad n 0.1 0.6)
        (>= n 0.6) (pth grey-grad n 0.6 1)))))

(defn noise-function [coloring-function octaves scale]
  (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
    (let [n (noise/octave-noise2 x y scale octaves)
          newcolor (coloring-function n)]
      (cm/aset2c mt x y newcolor))))

(defn draw []
  (d/background 0 0 1 1)

  (cm/on-matrix (noise-function (get-coloring-function) 2 0.001))

  #_(cm/on-matrix
   (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
     (cm/aset2c mt x y (pth my-grad (/ (/ (+ x y) 2) 500) 0.2 0.8))))

  (cm/draw-matrix)

  (d/save "output.jpg"))

