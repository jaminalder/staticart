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

(def colors [(c/as-rgba (c/css "rgba(237,106,90,1)"))
             (c/as-rgba (c/css "rgba(244,241,187,1)"))
             (c/as-rgba (c/css "rgba(155,193,188,1)"))
             (c/as-rgba (c/css "rgba(92,164,169,1)"))
             (c/as-rgba (c/css "rgba(230,235,224,1)"))])

(def grad1 (shuffle (grad/cosine-gradient
                     50 (grad/cosine-coefficients (nth colors 0) (nth colors 1)))))

(def grad2 (grad/cosine-gradient
            50 (grad/cosine-coefficients (nth colors 1) (nth colors 2))))

(def grad3 (shuffle (grad/cosine-gradient
                     50 (grad/cosine-coefficients (nth colors 2) (nth colors 0)))))

(defn pth [coll fact low high]
  (nth coll (int (m/map-interval-clamped fact low high 0 (- (count coll) 1)))))

(defn get-coloring-function []
  (let [green-grad 0]
    (fn [n]
      (cond
        (< n -0.15) (pth grad1 n -1 -0.15)
        (< n 0.15) (pth grad2 n -0.15 0.15)
        (>= n 0.15) (pth grad3 n 0.15 1)))))

(defn noise-function [coloring-function octaves scale turb]
  (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
    (let [[x' y'] (turb x y)
          n (noise/octave-noise2 x' y' scale octaves)
          newcolor (coloring-function n)]
      (cm/aset2c mt x y newcolor))))

(defn draw []
  (d/background 0 0 1 1)

  (cm/on-matrix (noise-function (get-coloring-function) 2 0.006
                                (fn [x y] [x y])))

  #_(cm/on-matrix
     (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
       (cm/aset2c mt x y (pth my-grad (/ (/ (+ x y) 2) 500) 0.2 0.8))))

  (cm/draw-matrix)

  (d/save "output.jpg"))

