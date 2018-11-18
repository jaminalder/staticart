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

(def c1 (c/as-rgba (c/hsva 0.6 0.5 1)))
(def c2 (c/as-rgba (c/hsva 0.7 0.8 0.5)))

(def my-grad (grad/cosine-gradient 200 (grad/cosine-coefficients c1 c2)))

(def shuffled-grad (shuffle my-grad))

(defn pth [coll fact low high]
  (nth coll (int (m/map-interval-clamped fact low high 0 (- (count coll) 1)))))

(defn noise-function [gradient octaves scale]
  (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
    (let [n (noise/octave-noise2 x y scale octaves)
          newcolor (pth gradient n -1 1)]
      (cm/aset2c mt x y newcolor))))

(defn draw []
  (d/background 0 0 1 1)

  (cm/on-matrix (noise-function shuffled-grad 3 0.007))

  #_(cm/on-matrix
   (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
     (cm/aset2c mt x y (pth my-grad (/ (/ (+ x y) 2) 500) 0.2 0.8))))

  (cm/draw-matrix)

  (d/save "output.jpg"))

